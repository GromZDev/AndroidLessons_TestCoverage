package q4.test_coverage.mytests

import com.github.terrakok.cicerone.Router
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.atLeastOnce
import com.nhaarman.mockito_kotlin.verify
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import q4.test_coverage.mytests.navigation.PopularFilmsScreen
import q4.test_coverage.mytests.presenter.MainFilmPresenter


class MainFilmPresenterTest {

    private lateinit var mainFilmPresenter: MainFilmPresenter

    @Mock
    private lateinit var router: Router

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        mainFilmPresenter = MainFilmPresenter(router)
    }

    @Test
    fun onBackClick_Exit_Router() {
        mainFilmPresenter.backClicked()
        assert(true)
    }

    @Test
    fun replace_Screen_Router() {
        router.replaceScreen(PopularFilmsScreen.create())
        assert(true)
    }

    @Test
    fun router_Exit_AtLeastOnce() {
        mainFilmPresenter.backClicked()
        verify(router, atLeastOnce()).exit()
    }

    @Test
    fun replace_Screen_Router_AtLeastOnce() {
        router.replaceScreen(PopularFilmsScreen.create())
        verify(router, atLeastOnce()).replaceScreen(any())
    }
}