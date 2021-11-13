package q4.test_coverage.mytests

import android.os.Bundle
import android.view.View
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.Matcher
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import q4.test_coverage.mytests.view.PopularFilmsFragment
import q4.test_coverage.mytests.view.PopularFilmsRVAdapter

@RunWith(AndroidJUnit4::class)
class PopularFilmsFragmentEspressoTest {

    private lateinit var scenario: FragmentScenario<PopularFilmsFragment>

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
    fun fragment_Is_NotNull() {
        scenario.onFragment {
            Assert.assertNotNull(it)
        }
    }

    @Test
    fun fragment_RecyclerView_IsScrolledToPosition7() {
        onView(withId(R.id.rv_users)).perform(
            RecyclerViewActions
                .scrollToPosition<PopularFilmsRVAdapter.ViewHolder>(7)
        )
    }

    @Test
    fun fragment_RecyclerView_IsDisplayed_AndClickedPosition0() {
        onView(isRoot()).perform(delay())
        onView(withId(R.id.rv_users))
            .perform(
                RecyclerViewActions
                    .actionOnItemAtPosition<PopularFilmsRVAdapter.ViewHolder>(0, click())
            )
    }

    @Test
    fun fragment_RecyclerView_HasThisFilm() {
        onView(isRoot()).perform(delay())
        onView(withId(R.id.rv_users))
            .perform(
                RecyclerViewActions
                    .scrollTo<PopularFilmsRVAdapter.ViewHolder>(hasDescendant(withText("Гениальное ограбление")))
            )
    }

    @Test
    fun fragment_RecyclerView_HasThisRating() {
        onView(isRoot()).perform(delay())
        onView(withId(R.id.rv_users))
            .perform(
                RecyclerViewActions
                    .scrollTo<PopularFilmsRVAdapter.ViewHolder>(hasDescendant(withText("8.1")))
            )
    }

    @Test
    fun fragment_RecyclerView_Test_ScrollAndClick() {
        onView(isRoot()).perform(delay())
        onView(withId(R.id.rv_users))
            .perform(
                RecyclerViewActions
                    .scrollTo<PopularFilmsRVAdapter.ViewHolder>(hasDescendant(withText("Преступный квест")))
            )

        onView(withId(R.id.rv_users))
            .perform(
                RecyclerViewActions
                    .actionOnItem<PopularFilmsRVAdapter.ViewHolder>(
                        hasDescendant(withText("Главный герой")),
                        click()
                    )
            )

    }

    private fun delay(): ViewAction? {
        return object : ViewAction {
            override fun getConstraints(): Matcher<View> = isRoot()
            override fun getDescription(): String = "wait for $2 seconds"
            override fun perform(uiController: UiController, v: View?) {
                uiController.loopMainThreadForAtLeast(2000)
            }
        }
    }

}
