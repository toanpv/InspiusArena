package vn.toanpv.sample.arena.match.ui.match.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import vn.toanpv.sample.arena.entity.Match
import vn.toanpv.sample.arena.entity.Team
import vn.toanpv.sample.arena.extension.parser.fromISO8601ZToCalendar
import vn.toanpv.sample.arena.extension.parser.toDateFormat
import vn.toanpv.sample.arena.extension.parser.toTimeFormat

@Parcelize
data class MatchPrevious(
    override var id: String = "",
    override var homeName: String = "",
    override var awayName: String = "",
    override var homeId: String = "",
    override var awayId: String = "",
    override var homeLogo: String = "",
    override var awayLogo: String = "",
    override var date: String = "",
    override var time: String = "",
    override var dateRaw: String = "",
    var winner: String = "",
    var description: String = "",
    var highlights: String = "",
    var winnerId: String = ""
) : MatchUI, Parcelable

fun Match.toPrevious(teamMap: Map<String, Team>) = MatchPrevious().apply {
    id = this@toPrevious.id
    homeId = this@toPrevious.homeId
    awayId = this@toPrevious.awayId
    teamMap[this@toPrevious.homeId]?.let {
        homeName = it.name
        homeLogo = it.logo ?: ""
    }
    teamMap[this@toPrevious.awayId]?.let {
        awayName = it.name
        awayLogo = it.logo ?: ""
    }
    dateRaw = this@toPrevious.date
    this@toPrevious.date.fromISO8601ZToCalendar()?.let {
        date = it.toDateFormat()
        time = it.toTimeFormat()
    }
    teamMap[this@toPrevious.winnerId]?.let {
        winnerId = it.id
        winner = it.name
    }
    description = this@toPrevious.description
    highlights = this@toPrevious.highlights ?: ""
}