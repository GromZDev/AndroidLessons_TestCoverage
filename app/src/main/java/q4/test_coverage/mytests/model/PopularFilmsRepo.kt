package q4.test_coverage.mytests.model

import io.reactivex.rxjava3.core.Single
import q4.test_coverage.mytests.model.GetMoviesResponse

interface PopularFilmsRepo {
    fun getPopularFilms(): Single<GetMoviesResponse>
}