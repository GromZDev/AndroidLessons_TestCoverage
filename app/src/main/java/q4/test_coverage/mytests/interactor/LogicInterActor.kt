package q4.test_coverage.mytests.interactor


/** Тут чистая логика */
interface LogicInterActor<T> {

    /** Coroutines -  */
    suspend fun getData(word: String, fromRemoteSource: Boolean): T
}