package vn.toanpv.sample.movies.repository.data.network

import vn.toanpv.sample.movies.entity.Movie

/**
 * An interface for interacting with the OMDB API to search for movies and retrieve movie details.
 */
interface MovieRemoteSource {

    /**
     * Searches for movies with the given title and type.
     *
     * @param searchTitle The movie title to search for.
     * @param type The type of content to search for: "movie", "series", "episode", or null (default is null, searches for all types).
     * @param page The page number to return (default is 1).
     * @return A list of movies.
     */
    suspend fun searchMovies(searchTitle: String, type: String? = null, page: Int = 1): List<Movie>?

    /**
     * Retrieves movie details for the given title.
     *
     * @param title The title of the movie to retrieve details for.
     * @return An Movie object containing detailed information about the movie, or null if an error occurs.
     */
    suspend fun getMovieDetails(title: String): Movie?

    /**
     * Retrieves movie details for the given IMDb ID.
     *
     * @param imdbId The IMDb ID of the movie to retrieve details for.
     * @return An Movie object containing detailed information about the movie, or null if an error occurs.
     */
    suspend fun getMovieDetailsById(imdbId: String): Movie?
}