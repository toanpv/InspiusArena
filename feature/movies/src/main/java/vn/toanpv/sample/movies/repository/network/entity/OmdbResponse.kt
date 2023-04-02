package vn.toanpv.sample.movies.repository.network.entity

import io.ktor.resources.*
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
open class OmdbResponse {

    @SerialName("totalResults")
    val totalResults: Long = 0

    @SerialName("Response")
    val response: String = ""

    @SerialName("Error")
    val error: String? = null
}