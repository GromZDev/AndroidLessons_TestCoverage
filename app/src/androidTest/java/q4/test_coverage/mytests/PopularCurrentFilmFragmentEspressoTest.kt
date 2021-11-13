package q4.test_coverage.mytests

import android.os.Bundle
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import q4.test_coverage.mytests.model.Movie
import q4.test_coverage.mytests.view.PopularCurrentFilmFragment

@RunWith(AndroidJUnit4::class)
class PopularCurrentFilmFragmentEspressoTest {

    private lateinit var scenario: FragmentScenario<PopularCurrentFilmFragment>

    @Before
    fun setUp() {

        scenario = launchFragmentInContainer()
        /** для запуска Фрагмента с UI */
    }

    @Test
    fun fragment_Is_Visible() {
        scenario.onFragment { fragment ->
            fragment.isVisible
        }
    }

    @Test
    fun fragment_Is_Added() {
        scenario.onFragment { fragment ->
            fragment.isAdded
        }
    }

    @Test
    fun fragment_Is_Resumed() {
        scenario.onFragment {
            Assert.assertTrue(it.isResumed)
        }
    }

    @Test
    fun fragment_Bundle_IsNull() {
        var a: Bundle? = null
        scenario.onFragment { fragment ->
            a = fragment.arguments
        }
        Assert.assertNull(a)
    }

    @Test
    fun fragment_Bundle_IsNotNull() {
        val currentFilm = Movie(id = 1, title = "Good", overview = "Film")
        val bundle = Bundle()
        bundle.putParcelable(PopularCurrentFilmFragment.BUNDLE_EXTRA, currentFilm)

        launchFragmentInContainer<PopularCurrentFilmFragment>(bundle)

        val assertion = matches(withText("Good"))

        onView(withId(R.id.tw_popular_film_name)).check(assertion)

    }

    @Test
    fun fragment_Rating_IsNull() {
        onView(withId(R.id.tw_popular_film_rating)).check(matches(withText("null")))
    }

    @Test
    fun fragment_FilmTime_IsNull() {
        onView(withId(R.id.tw_popular_film_time)).check(matches(withText("null")))
    }

    @Test
    fun fragment_FilmYear_IsEmpty() {
        onView(withId(R.id.tw_popular_film_year)).check(matches(withText("")))
    }

    @Test
    fun fragment_Data_With_Bundle_IsNotNull() {
        val currentFilm = Movie(
            id = 1, title = "Good", overview = "Film Description",
            popularity = 2888f, releaseDate = "12.11.2021", rating = 8.8f
        )
        val bundle = Bundle()
        bundle.putParcelable(PopularCurrentFilmFragment.BUNDLE_EXTRA, currentFilm)

        launchFragmentInContainer<PopularCurrentFilmFragment>(bundle)

        onView(withId(R.id.tw_popular_film_name)).check(matches(withText("Good")))
        onView(withId(R.id.tw_popular_film_year)).check(matches(withText("12.11.2021")))
        onView(withId(R.id.tw_popular_film_time)).check(matches(withText("2888.0")))
        onView(withId(R.id.tw_popular_film_rating)).check(matches(withText("8.8")))
        onView(withId(R.id.tw_popular_film_description)).check(matches(withText("Film Description")))

    }

}