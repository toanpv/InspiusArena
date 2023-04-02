package vn.toanpv.sample.movies.ui.movie.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import vn.toanpv.sample.movies.entity.Movie

@Parcelize
data class MovieItem(
    val title: String,
    val year: String,
    val rated: String? = null,
    val released: String? = null,
    val runtime: String? = null,
    val genre: String? = null,
    val director: String? = null,
    val writer: String? = null,
    val actors: String? = null,
    val plot: String? = null,
    val language: String? = null,
    val country: String? = null,
    val awards: String? = null,
    val poster: String,
    val ratings: List<Rating>? = null,
    val metascore: String? = null,
    val imdbRating: String? = null,
    val imdbVotes: String? = null,
    val imdbID: String,
    val type: String,
    val dvd: String? = null,
    val boxOffice: String? = null,
    val production: String? = null,
    val website: String? = null,
    val bookmarked: Boolean
) : Parcelable {
    fun toEntity() =
        Movie(
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
            ratings?.map { it.toEntity() },
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

fun Movie.toItemList() = MovieItem(
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
    ratings?.map { it.toItem() },
    metascore,
    imdbRating,
    imdbVotes,
    imdbID,
    type,
    dvd,
    boxOffice,
    production,
    website, false
)

@Parcelize
data class Rating(
    val source: String,
    val value: String
) : Parcelable {
    fun toEntity() = vn.toanpv.sample.movies.entity.Rating(source, value)
}

fun vn.toanpv.sample.movies.entity.Rating.toItem() = Rating(source, value)