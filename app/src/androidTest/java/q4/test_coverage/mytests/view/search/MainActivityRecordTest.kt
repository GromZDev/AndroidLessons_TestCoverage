package q4.test_coverage.mytests.view.search


import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import q4.test_coverage.mytests.FOUNDERS_EDITION
import q4.test_coverage.mytests.R

@LargeTest
@RunWith(AndroidJUnit4::class)
class MainActivityRecordTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun mainActivityRecordTest() {

        val appCompatEditText = onView(
            allOf(
                withId(R.id.searchEditText),
                childAtPosition(
                    childAtPosition(
                        withId(android.R.id.content),
                        0
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        appCompatEditText.perform(replaceText(""), closeSoftKeyboard())

        val button = onView(
            allOf(
                withId(R.id.searchButton), withText("SEARCH"),
                withParent(withParent(withId(android.R.id.content))),
                isDisplayed()
            )
        )
        button.check(matches(isDisplayed()))

        val button2 = onView(
            allOf(
                withId(R.id.toDetailsActivityButton), withText("TO DETAILS"),
                withParent(withParent(withId(android.R.id.content))),
                isDisplayed()
            )
        )
        button2.check(matches(isDisplayed()))


        val appCompatEditText2 = onView(
            allOf(
                withId(R.id.searchEditText),
                childAtPosition(
                    childAtPosition(
                        withId(android.R.id.content),
                        0
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        appCompatEditText2.perform(replaceText(FOUNDERS_EDITION), closeSoftKeyboard())

        val editText2 = onView(
            allOf(
                withId(R.id.searchEditText), withText(FOUNDERS_EDITION),
                withParent(withParent(withId(android.R.id.content))),
                isDisplayed()
            )
        )
        editText2.check(matches(withText(FOUNDERS_EDITION)))

    }

    private fun childAtPosition(
        parentMatcher: Matcher<View>, position: Int
    ): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }
}
