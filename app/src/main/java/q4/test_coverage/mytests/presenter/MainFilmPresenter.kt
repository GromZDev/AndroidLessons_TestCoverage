package q4.test_coverage.mytests.presenter

import com.github.terrakok.cicerone.Router
import moxy.MvpPresenter
import q4.test_coverage.mytests.navigation.PopularFilmsScreen
import q4.test_coverage.mytests.view.MainView

class MainFilmPresenter(private val router: Router) :
    MvpPresenter<MainView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        router.replaceScreen(PopularFilmsScreen.create())
    }

    fun backClicked() = router.exit()

}
