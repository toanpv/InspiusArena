package vn.inspius.toanpv.arena.match.ui.match.detail

import android.app.PendingIntent
import android.app.PictureInPictureParams
import android.app.RemoteAction
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.res.Configuration
import android.graphics.Rect
import android.graphics.drawable.Icon
import android.os.Build
import android.util.Rational
import android.view.View
import androidx.annotation.DrawableRes
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import vn.inspius.toanpv.arena.match.R
import xyz.doikki.videocontroller.StandardVideoController
import xyz.doikki.videoplayer.player.BaseVideoView.SimpleOnStateChangeListener
import xyz.doikki.videoplayer.player.VideoView


abstract class VideoViewPiPActivity : AppCompatActivity() {

    /**
     * The arguments to be used for Picture-in-Picture mode.
     */
    @RequiresApi(Build.VERSION_CODES.O)
    private val mPictureInPictureParamsBuilder: PictureInPictureParams.Builder =
        PictureInPictureParams.Builder()

    /**
     * A [BroadcastReceiver] to receive action item events from Picture-in-Picture mode.
     */
    private var _receiver: BroadcastReceiver? = null

    private lateinit var _videoView: VideoView
    private lateinit var _controller: StandardVideoController
    private var _widthPixels = 0

    fun initPlayer(videoView: VideoView) {
        _videoView = videoView
        _widthPixels = resources.displayMetrics.widthPixels
        _videoView.apply {
            layoutParams = ConstraintLayout.LayoutParams(_widthPixels, _widthPixels * 9 / 16 + 1)

            _controller = StandardVideoController(this@VideoViewPiPActivity).apply {
                addDefaultControlComponent(
                    getString(vn.inspius.toanpv.arena.core.ui.R.string.app_name), false
                )
            }
            setVideoController(_controller)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                addOnStateChangeListener(object : SimpleOnStateChangeListener() {
                    @RequiresApi(Build.VERSION_CODES.O)
                    override fun onPlayStateChanged(playState: Int) {
                        when (playState) {
                            VideoView.STATE_PAUSED -> updatePictureInPictureActions(
                                R.drawable.ic_video_player_play,
                                getString(R.string.video_player_play),
                                CONTROL_TYPE_PLAY,
                                REQUEST_PLAY
                            )
                            VideoView.STATE_PLAYING -> updatePictureInPictureActions(
                                R.drawable.ic_video_player_pause,
                                getString(R.string.video_player_pause),
                                CONTROL_TYPE_PAUSE,
                                REQUEST_PAUSE
                            )
                            VideoView.STATE_PLAYBACK_COMPLETED -> updatePictureInPictureActions(
                                R.drawable.ic_video_player_replay,
                                getString(R.string.video_player_replay),
                                CONTROL_TYPE_REPLAY,
                                REQUEST_REPLAY
                            )
                        }
                    }
                })

                addOnLayoutChangeListener { _: View?, _: Int, _: Int, _: Int, _: Int, _: Int, _: Int, _: Int, _: Int ->
                    val sourceRectHint = Rect()
                    videoView.getGlobalVisibleRect(sourceRectHint)
                    val builder = PictureInPictureParams.Builder().setSourceRectHint(sourceRectHint)
                    setPictureInPictureParams(builder.build())
                }
            }
        }
    }

    /**
     * Update the state of pause/resume action item in Picture-in-Picture mode.
     *
     * @param iconId      The icon to be used.
     * @param title       The title text.
     * @param controlType The type of the action. either [.CONTROL_TYPE_PLAY] or [.CONTROL_TYPE_REPLAY] [.CONTROL_TYPE_PAUSE].
     * @param requestCode The request code for the [PendingIntent].
     */
    @RequiresApi(Build.VERSION_CODES.O)
    fun updatePictureInPictureActions(
        @DrawableRes iconId: Int, title: String, controlType: Int, requestCode: Int
    ) {
        mPictureInPictureParamsBuilder.setActions(ArrayList<RemoteAction>().apply {
            add(
                RemoteAction(
                    Icon.createWithResource(this@VideoViewPiPActivity, iconId),
                    title,
                    title,
                    PendingIntent.getBroadcast(
                        this@VideoViewPiPActivity,
                        requestCode,
                        Intent(ACTION_MEDIA_CONTROL).putExtra(EXTRA_CONTROL_TYPE, controlType),
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) PendingIntent.FLAG_MUTABLE else 0
                    )
                )
            )
        })
        setPictureInPictureParams(mPictureInPictureParamsBuilder.build())

    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun startFloatWindow() {
        val params = (if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            mPictureInPictureParamsBuilder.setAspectRatio(Rational(16, 9))
                .setAutoEnterEnabled(true)
        } else {
            mPictureInPictureParamsBuilder.setAspectRatio(Rational(16, 9))
        }).build()
        enterPictureInPictureMode(params)
    }

    override fun onStart() {
        super.onStart()
        _videoView.start()
    }

    override fun onStop() {
        super.onStop()
        _videoView.release()
    }

    override fun onBackPressed() {
        if (_videoView.onBackPressed()) return
        super.onBackPressed()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onPictureInPictureModeChanged(
        isInPictureInPictureMode: Boolean, newConfig: Configuration
    ) {
        super.onPictureInPictureModeChanged(isInPictureInPictureMode, newConfig)
        toggleContent(isInPictureInPictureMode)
        if (isInPictureInPictureMode) {
            _videoView.apply {
                setVideoController(null)
                val smallWi = (_widthPixels / 2).toInt()
                layoutParams = ConstraintLayout.LayoutParams(
                    smallWi, smallWi * 9 / 16 + 1
                )
//                layoutParams = ConstraintLayout.LayoutParams(
//                    ConstraintLayout.LayoutParams.WRAP_CONTENT,
//                    ConstraintLayout.LayoutParams.WRAP_CONTENT
//                )
            }
            // Starts receiving events from action items in PiP mode.
            _receiver = object : BroadcastReceiver() {
                override fun onReceive(context: Context?, intent: Intent?) {
                    if (intent == null || ACTION_MEDIA_CONTROL != intent.action) {
                        return
                    }

                    when (intent.getIntExtra(EXTRA_CONTROL_TYPE, 0)) {
                        CONTROL_TYPE_PLAY -> _videoView.start()
                        CONTROL_TYPE_PAUSE -> _videoView.pause()
                        CONTROL_TYPE_REPLAY -> _videoView.replay(true)
                    }
                }
            }
            registerReceiver(_receiver, IntentFilter(ACTION_MEDIA_CONTROL))
        } else {
            unregisterReceiver(_receiver)
            _receiver = null
            _videoView.apply {
                layoutParams = ConstraintLayout.LayoutParams(
                    _widthPixels, _widthPixels * 9 / 16 + 1
                )
                setVideoController(_controller)
                requestLayout()
            }
        }
    }

    abstract fun toggleContent(isPip: Boolean)

    companion object {

        /**
         * Intent action for media controls from Picture-in-Picture mode.
         */
        private const val ACTION_MEDIA_CONTROL = "media_control"

        /**
         * Intent extra for media controls from Picture-in-Picture mode.
         */
        private const val EXTRA_CONTROL_TYPE = "control_type"

        /**
         * The request code for play action PendingIntent.
         */
        private const val REQUEST_PLAY = 1

        /**
         * The request code for pause action PendingIntent.
         */
        private const val REQUEST_PAUSE = 2

        /**
         * The request code for replay action PendingIntent.
         */
        private const val REQUEST_REPLAY = 3

        /**
         * The intent extra value for play action.
         */
        private const val CONTROL_TYPE_PLAY = 1

        /**
         * The intent extra value for pause action.
         */
        private const val CONTROL_TYPE_PAUSE = 2

        /**
         * The intent extra value for replay action.
         */
        private const val CONTROL_TYPE_REPLAY = 3
    }
}