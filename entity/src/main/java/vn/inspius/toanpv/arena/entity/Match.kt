package vn.inspius.toanpv.arena.entity

data class Match(
    var id: String = "",
    val date: String,
    val description: String,
    val home: String,
    val away: String,
    val winner: String? = null,
    val highlights: String? = null,
    var homeId: String = "",
    var awayId: String = "",
    var winnerId: String = ""
)