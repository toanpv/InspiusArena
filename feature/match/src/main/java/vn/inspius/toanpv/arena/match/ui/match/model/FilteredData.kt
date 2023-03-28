package vn.inspius.toanpv.arena.match.ui.match.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class FilteredData(var teamId: String? = null) : Parcelable
