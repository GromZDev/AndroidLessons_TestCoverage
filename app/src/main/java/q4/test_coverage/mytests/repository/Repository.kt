package q4.test_coverage.mytests.repository

/** Репозиторий представляет собой слой получения
 * и хранения данных, которые он передаёт интерактору */
interface Repository<T> {

    /** Coroutines -  */
    suspend fun getData(word: String): T


}