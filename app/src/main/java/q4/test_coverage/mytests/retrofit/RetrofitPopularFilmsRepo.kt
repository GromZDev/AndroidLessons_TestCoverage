package q4.test_coverage.mytests.retrofit

import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import q4.test_coverage.mytests.BuildConfig
import q4.test_coverage.mytests.model.GetMoviesResponse
import q4.test_coverage.mytests.model.PopularFilmsRepo

class RetrofitPopularFilmsRepo(private val api: IDataSource) : PopularFilmsRepo {

    private val key: String = BuildConfig.FILM_API_KEY
    override fun getPopularFilms(): Single<GetMoviesResponse> =
        api.getPopularMovies(key, "ru", 1)
            .subscribeOn(Schedulers.io())

}