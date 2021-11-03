package q4.test_coverage.mytests.espresso

import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import junit.framework.TestCase
import org.hamcrest.Matcher
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import q4.test_coverage.mytests.BuildConfig
import q4.test_coverage.mytests.R
import q4.test_coverage.mytests.view.details.DetailsActivity
import q4.test_coverage.mytests.view.search.MainActivity

@RunWith(AndroidJUnit4::class)
class MainActivityEspressoTest {

    private lateinit var scenario: ActivityScenario<MainActivity>

    @Before
    fun setup() {
        scenario = ActivityScenario.launch(MainActivity::class.java)
    }

    @Test
    fun activitySearch_IsWorking() {
        onView(withId(R.id.searchEditText)).perform(click())
        onView(withId(R.id.searchEditText)).perform(replaceText("algol"), closeSoftKeyboard())
        onView(withId(R.id.searchEditText)).perform(pressImeActionButton())

        if (BuildConfig.TYPE == MainActivity.FAKE) {
            onView(withId(R.id.totalCountTextView)).check(matches(withText("Number of results: 42")))
        } else {
            onView(isRoot()).perform(delay())
            onView(withId(R.id.totalCountTextView)).check(matches(withText("Number of results: 2655")))
        }
    }

    @Test
    fun activity_AssertNotNull() {
        scenario.onActivity {
            TestCase.assertNotNull(it)
        }
    }

    @Test
    fun activity_IsResumed() {
        TestCase.assertEquals(Lifecycle.State.RESUMED, scenario.state)
    }

    @Test
    fun search_EditText_NotNull() {
        onView(withId(R.id.searchEditText)).perform(click())
        onView(withId(R.id.searchEditText)).perform(replaceText("My Text"), closeSoftKeyboard())
        scenario.onActivity {
            val text =
                it.findViewById<EditText>(R.id.searchEditText).text.toString()
            TestCase.assertEquals("My Text", text)
        }
    }

    @Test
    fun search_EditText_isDisplayed() {
        onView(withId(R.id.searchEditText)).check(matches(isDisplayed()))
        onView(withId(R.id.searchEditText)).check(matches(isCompletelyDisplayed()))
    }

    @Test
    fun search_EditText_isClickable() {
        onView(withId(R.id.searchEditText)).check(matches(isClickable()))
    }

    @Test
    fun search_EditText_isVisible() {
        onView(withId(R.id.searchEditText)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
    }

    @Test
    fun toDetailActivityButton_isDisplayed() {
        onView(withId(R.id.toDetailsActivityButton)).check(matches(isDisplayed()))
        onView(withId(R.id.toDetailsActivityButton)).check(matches(isCompletelyDisplayed()))
    }

    @Test
    fun toDetailActivityButton_AreEffectiveVisible() {
        onView(withId(R.id.toDetailsActivityButton))
            .check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
    }

    @Test
    fun toDetailActivityButton_isClickable() {
        onView(withId(R.id.toDetailsActivityButton)).check(matches(isClickable()))
    }

    @Test
    fun toDetailActivityButton_Text_Correct() {
        scenario.onActivity {
            val text =
                it.findViewById<Button>(R.id.toDetailsActivityButton).text.toString()
            TestCase.assertEquals("to details", text)
        }
    }

    @Test
    fun toDetailActivityButton_goToDetailsActivity_isCorrect() {
        onView(withId(R.id.toDetailsActivityButton)).perform(click())
        scenario.onActivity { DetailsActivity }
    }

    @Test
    fun progressBar_isGone() {
        onView(withId(R.id.progressBar)).check(matches(withEffectiveVisibility(Visibility.GONE)))
    }

    @Test
    fun progressBar_isEnabled() {
        onView(withId(R.id.progressBar)).check(matches(isEnabled()))
    }

    @Test
    fun goToDetailsActivity_decrementButton_isVisible() {
        onView(withId(R.id.toDetailsActivityButton)).perform(click())
        scenario.onActivity { DetailsActivity }
        onView(withId(R.id.decrementButton)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
    }

    @Test
    fun totalCountTextView_isInvisible() {
        onView(withId(R.id.totalCountTextView)).check(matches(withEffectiveVisibility(Visibility.INVISIBLE)))
    }

    @Test
    fun totalCountTextView_isVisible() {
        onView(withId(R.id.searchEditText)).perform(click())
        onView(withId(R.id.searchEditText)).perform(replaceText("some"), closeSoftKeyboard())
        onView(withId(R.id.searchEditText)).perform(pressImeActionButton())

        if (BuildConfig.TYPE == MainActivity.FAKE) {
            onView(withId(R.id.totalCountTextView)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
        } else {
            onView(isRoot()).perform(delay())
            onView(withId(R.id.totalCountTextView)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
        }
    }

    @Test
    fun recyclerView_isPermanentlyVisible() {
        onView(withId(R.id.recyclerView)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
    }

    @Test
    fun recyclerView_isNotFocused_Clickable_andSelected() {
        onView(withId(R.id.recyclerView)).check(matches(isNotFocused()))
        onView(withId(R.id.recyclerView)).check(matches(isNotClickable()))
        onView(withId(R.id.recyclerView)).check(matches(isNotSelected()))
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

    @After
    fun close() {
        scenario.close()
    }

}
