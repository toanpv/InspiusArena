package vn.toanpv.sample.arena.core.ui.util

import android.util.Log

object LogUtils {

    private var DEBUG = true

    private const val TRACE_METHOD = "trace"

    private const val START_LOG_METHOD = "startLogMethod"
    private const val END_LOG_METHOD = "endLogMethod"

    private const val CLASS_NAME_INDEX = 0

    private const val METHOD_NAME_INDEX = 1

    fun setMode(debugMode: Boolean) {
        DEBUG = debugMode
    }

    fun i(message: String) {
        if (DEBUG) {
            val msg = trace()
            if (msg != null) {
                i(msg[CLASS_NAME_INDEX], msg[METHOD_NAME_INDEX] + message)
            }
        }
    }

    private fun i(tag: String, message: String) {
        if (DEBUG) {
            Log.i(tag, message)
        }
    }

    fun e(message: String) {
        if (DEBUG) {
            val msg = trace()
            if (msg != null) {
                e(msg[CLASS_NAME_INDEX], msg[METHOD_NAME_INDEX] + message)
            }
        }
    }

    private fun e(tag: String, message: String) {
        if (DEBUG) {
            Log.e(tag, message)
        }
    }

    fun d(message: String) {
        if (DEBUG) {
            val msg = trace()
            if (msg != null) {
                d(msg[CLASS_NAME_INDEX], "[ " + msg[METHOD_NAME_INDEX] + " ] " + message)
            }
        }
    }

    fun w(message: String) {
        if (DEBUG) {
            val msg = trace()
            if (msg != null) {
                w(msg[CLASS_NAME_INDEX], "[ " + msg[METHOD_NAME_INDEX] + " ] " + message)
            }
        }
    }

    private fun d(tag: String, message: String) {
        if (DEBUG) {
            Log.d(tag, message)
        }
    }

    private fun w(tag: String, message: String) {
        if (DEBUG) {
            Log.w(tag, message)
        }
    }

    private fun trace(): Array<String>? {
        var index = 0
        val stackTraceElements = Thread.currentThread().stackTrace ?: return null
        for (i in stackTraceElements.indices) {
            val ste = stackTraceElements[i]
            if ((ste.className == LogUtils::class.java.name) && (ste.methodName.contains(
                    TRACE_METHOD
                ))
            ) {
                index = i + 2
                if (index < stackTraceElements.size && stackTraceElements[index].methodName.contains(
                        START_LOG_METHOD
                    ) || index < stackTraceElements.size && stackTraceElements[index].methodName.contains(
                        END_LOG_METHOD
                    )
                ) {
                    break
                }
                index = i + 1
                break
            }
        }
        index++
        if ((stackTraceElements.size >= index) && (stackTraceElements[index] != null)) {
            return arrayOf(
                stackTraceElements[index].fileName,
                stackTraceElements[index].methodName + "[" + stackTraceElements[index].lineNumber + "] "
            )
        }
        return null
    }
}
