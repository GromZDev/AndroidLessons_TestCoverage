package q4.test_coverage.mytests.utils

interface ImageLoader<T> {

    fun loadInto(url: String, container: T)

}