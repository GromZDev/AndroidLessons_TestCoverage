package q4.test_coverage.mytests.navigation

import com.github.terrakok.cicerone.androidx.FragmentScreen
import q4.test_coverage.mytests.view.PopularFilmsFragment

object PopularFilmsScreen {

    fun create() = FragmentScreen { PopularFilmsFragment.newInstance() }

}