package q4.test_coverage.mytests.retrofit

import io.reactivex.rxjava3.core.Single
import q4.test_coverage.mytests.model.GetMoviesResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface IDataSource {
    @GET("movie/popular")
    fun getPopularMovies(
        @Query("api_key") apiKey: String,
        @Query("language") lang: String,
        @Query("page") page: Int
    ): Single<GetMoviesResponse>

}