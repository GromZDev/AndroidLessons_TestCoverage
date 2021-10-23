package q4.test_coverage.mytests.retrofit

interface ImageLoader<T> {

    fun loadInto(url: String, container: T)

}