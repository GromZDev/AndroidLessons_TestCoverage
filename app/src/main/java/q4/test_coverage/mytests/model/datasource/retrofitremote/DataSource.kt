package q4.test_coverage.mytests.model.datasource.retrofitremote


/** Источник данных для репозитория (Интернет, БД и т. п.) */
interface DataSource<T> {

    /** Coroutines -  */
    suspend fun getData(word: String): T

}