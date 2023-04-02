package vn.toanpv.sample.movies.repository.network.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class OmdbSearchResponse : OmdbResponse() {
    @SerialName("Search")
    val search: List<OmdbMovie>? = null
}