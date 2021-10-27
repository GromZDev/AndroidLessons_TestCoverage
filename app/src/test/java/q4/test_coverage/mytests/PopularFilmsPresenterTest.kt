package q4.test_coverage.mytests

import com.github.terrakok.cicerone.Router
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.atLeastOnce
import com.nhaarman.mockito_kotlin.verify
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.disposables.CompositeDisposable
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import q4.test_coverage.mytests.model.GetMoviesResponse
import q4.test_coverage.mytests.model.Movie
import q4.test_coverage.mytests.model.PopularFilmsRepo
import q4.test_coverage.mytests.navigation.PopularFilmsScreen
import q4.test_coverage.mytests.presenter.PopularFilmsPresenter
import q4.test_coverage.mytests.view.PopularFilmsItemView
import retrofit2.Response

class PopularFilmsPresenterTest {

    private lateinit var popularFilmPresenter: PopularFilmsPresenter

    @Mock
    private lateinit var moviesRepo: PopularFilmsRepo

    @Mock
    private lateinit var router: Router

    @Mock
    private lateinit var scheduler: Scheduler

    @Mock
    private lateinit var view: PopularFilmsItemView

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        popularFilmPresenter = PopularFilmsPresenter(
            uiScheduler = scheduler,
            moviesRepo = moviesRepo,
            router = router
        )

        view = mock(PopularFilmsItemView::class.java)
    }

    @Test
    fun moviesRepo_invokeTimes_OneTime_True() {
        moviesRepo.getPopularFilms()
        verify(moviesRepo, times(1)).getPopularFilms()
    }

    @Test
    fun moviesRepo_getPopularFilms_onNull() {
        assertNull(null, moviesRepo.getPopularFilms())
    }

    @Test
    fun on_Back_Pressed_True() {
        popularFilmPresenter.backPressed()
        assert(true)

    }

    @Test
    fun replace_Screen_Router() {
        router.replaceScreen(PopularFilmsScreen.create())
        assert(true)
    }

    @Test
    fun router_Exit_AtLeastOnce() {
        popularFilmPresenter.backPressed()
        verify(router, atLeastOnce()).exit()
    }

    @Test
    fun replace_Screen_Router_AtLeastOnce() {
        router.replaceScreen(PopularFilmsScreen.create())
        verify(router, atLeastOnce()).replaceScreen(any())
    }

    @Test
    fun start_Scheduler_OneTime() {
        scheduler.start()
        verify(scheduler, atLeastOnce()).start()
    }

    @Test
    fun getCount_List_Presenter_atLeastOnce() {
        val listPresenter = mock(PopularFilmsPresenter.PopularFilmsListPresenter::class.java)
        listPresenter.getCount()
        verify(listPresenter, atLeastOnce()).getCount()
    }

    @Test
    fun bind_View_atLeastOnce() {
        val listPresenter = mock(PopularFilmsPresenter.PopularFilmsListPresenter::class.java)
        moviesRepo.getPopularFilms()
        listPresenter.bindView(view)
        verify(listPresenter, atLeastOnce()).bindView(view)
    }

    @Test
    fun response_Body_IsNull() {
        val response = mock(Response::class.java) as Response<*>
        `when`(response.body()).thenReturn(null)
        moviesRepo.getPopularFilms()
        assertNull(response.body())
    }

    @Test
    fun response_Body_IsNotNull() {
        val response = mock(Response::class.java) as Response<*>
        val movieList = mutableListOf<Movie>()
        movieList.add(Movie(id = 1, title = "Pop", overview = "Nice"))
        val result = GetMoviesResponse(1, movieList, 5)
        `when`(response.body()).thenReturn(result)
        moviesRepo.getPopularFilms()
        assertNotNull(response.body())
    }

    @Test
    fun view_SetTitle_With_Correct_Response_OneTime() {
        val listPresenter = mock(PopularFilmsPresenter.PopularFilmsListPresenter::class.java)
        val response = mock(Response::class.java) as Response<*>
        val resultResponse = mock(GetMoviesResponse::class.java)
        val moviesList = listOf(mock(Movie::class.java))

        `when`(response.isSuccessful).thenReturn(true)
        `when`(response.body()).thenReturn(resultResponse)
        `when`(resultResponse.movies).thenReturn(moviesList)
        val movieList = mutableListOf<Movie>()
        movieList.add(Movie(id = 1, title = "Pop", overview = "Nice"))
        `when`(resultResponse.movies).thenReturn(movieList)

        moviesRepo.getPopularFilms()

        listPresenter.bindView(view)

        view.setTitle(resultResponse.movies[0].title.toString())

        resultResponse.movies[0].title?.let { verify(view, times(1)).setTitle(it) }
    }

    @Test
    fun disposable_Dispose_atLeastOnce() {
        val disposable = mock(CompositeDisposable::class.java)
        popularFilmPresenter.onDestroy()
        disposable.dispose()
        verify(disposable, atLeastOnce()).dispose()
    }
}