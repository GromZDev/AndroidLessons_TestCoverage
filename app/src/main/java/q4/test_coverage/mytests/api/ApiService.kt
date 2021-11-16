package q4.test_coverage.mytests.api


import kotlinx.coroutines.Deferred
import q4.test_coverage.mytests.model.DataModel
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("words/search")
            /** Coroutines - возвращаем данные через Deferred */
    fun searchAsync(@Query("search") wordToSearch: String): Deferred<List<DataModel>>
}