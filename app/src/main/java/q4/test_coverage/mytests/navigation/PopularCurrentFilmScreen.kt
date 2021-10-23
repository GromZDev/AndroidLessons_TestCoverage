package q4.test_coverage.mytests.navigation

import android.os.Bundle
import com.github.terrakok.cicerone.androidx.FragmentScreen
import q4.test_coverage.mytests.view.PopularCurrentFilmFragment

class PopularCurrentFilmScreen() {

    fun create(bundle: Bundle) = FragmentScreen { PopularCurrentFilmFragment.newInstance(bundle) }

}