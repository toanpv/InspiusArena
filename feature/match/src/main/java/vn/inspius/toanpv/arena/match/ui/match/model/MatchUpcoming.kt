package vn.inspius.toanpv.arena.match.ui.match.model

import vn.inspius.toanpv.arena.entity.Match
import vn.inspius.toanpv.arena.entity.Team
import vn.inspius.toanpv.arena.extension.parser.fromISO8601ZToCalendar
import vn.inspius.toanpv.arena.extension.parser.toDateFormat
import vn.inspius.toanpv.arena.extension.parser.toTimeFormat

data class MatchUpcoming(
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
    var reminder: Boolean = false
) : MatchUI

fun Match.toUpcoming(teamMap: Map<String, Team>, remindedIds: Set<String> = mutableSetOf()) =
    MatchUpcoming().apply {
        id = this@toUpcoming.id
        homeId = this@toUpcoming.homeId
        awayId = this@toUpcoming.awayId
        teamMap[this@toUpcoming.homeId]?.let {
            homeName = it.name
            homeLogo = it.logo ?: ""
        }
        teamMap[this@toUpcoming.awayId]?.let {
            awayName = it.name
            awayLogo = it.logo ?: ""
        }
        dateRaw = this@toUpcoming.date
        this@toUpcoming.date.fromISO8601ZToCalendar()?.let {
            date = it.toDateFormat()
            time = it.toTimeFormat()
        }
        reminder = remindedIds.contains(id)
    }