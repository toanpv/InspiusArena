package vn.toanpv.sample.movies.repository.network.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import vn.toanpv.sample.movies.entity.Movie
import vn.toanpv.sample.movies.entity.Rating
import vn.toanpv.sample.repository.data.EntityMapper

@Serializable
data class OmdbMovie(
    @SerialName("Title") val title: String = "",
    @SerialName("Year") val year: String = "",
    @SerialName("Rated") val rated: String? = null,
    @SerialName("Released") val released: String? = null,
    @SerialName("Runtime") val runtime: String? = null,
    @SerialName("Genre") val genre: String? = null,
    @SerialName("Director") val director: String? = null,
    @SerialName("Writer") val writer: String? = null,
    @SerialName("Actors") val actors: String? = null,
    @SerialName("Plot") val plot: String? = null,
    @SerialName("Language") val language: String? = null,
    @SerialName("Country") val country: String? = null,
    @SerialName("Awards") val awards: String? = null,
    @SerialName("Poster") val poster: String = "",
    @SerialName("Ratings") val ratings: List<OmdbRating>? = null,
    @SerialName("Metascore") val metascore: String? = null,
    @SerialName("imdbRating") val imdbRating: String? = null,
    @SerialName("imdbVotes") val imdbVotes: String? = null,
    @SerialName("imdbID") val imdbID: String = "",
    @SerialName("Type") val type: String = "",
    @SerialName("DVD") val dvd: String? = null,
    @SerialName("BoxOffice") val boxOffice: String? = null,
    @SerialName("Production") val production: String? = null,
    @SerialName("Website") val website: String? = null
) : OmdbResponse(), EntityMapper<Movie, OmdbMovie> {
    override fun convert(): Movie {
        return Movie(
            title,
            year,
            rated,
            released,
            runtime,
            genre,
            director,
            writer,
            actors,
            plot,
            language,
            country,
            awards,
            poster,
            ratings?.map { it.convert() },
            metascore,
            imdbRating,
            imdbVotes,
            imdbID,
            type,
            dvd,
            boxOffice,
            production,
            website
        )
    }
}

@Serializable
data class OmdbRating(
    @SerialName("Source") val source: String,
    @SerialName("Value") val value: String
) : EntityMapper<Rating, OmdbRating> {
    override fun convert() = Rating(source, value)

}