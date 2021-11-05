package q4.test_coverage.mytests.view.search


import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.*
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
class DetailsActivityRecordTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun detailsActivityRecordTest() {
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
        appCompatEditText.perform(replaceText(FOUNDERS_EDITION), closeSoftKeyboard())

        val materialButton = onView(
            allOf(
                withId(R.id.searchButton), withText("Search"),
                childAtPosition(
                    childAtPosition(
                        withId(android.R.id.content),
                        0
                    ),
                    3
                ),
                isDisplayed()
            )
        )
        materialButton.perform(click())

        onView(isRoot()).perform(delay())

        val materialButton2 = onView(
            allOf(
                withId(R.id.toDetailsActivityButton), withText("to details"),
                childAtPosition(
                    childAtPosition(
                        withId(android.R.id.content),
                        0
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        materialButton2.perform(click())

        val textView2 = onView(
            allOf(
                withId(R.id.totalCountTextView), withText("Number of results: 14"),
                withParent(withParent(withId(android.R.id.content))),
                isDisplayed()
            )
        )
        textView2.check(matches(withText("Number of results: 14")))

        val materialButton3 = onView(
            allOf(
                withId(R.id.incrementButton), withText("+"),
                childAtPosition(
                    childAtPosition(
                        withId(android.R.id.content),
                        0
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        materialButton3.perform(click())

        val button = onView(
            allOf(
                withId(R.id.incrementButton), withText("+"),
                withParent(withParent(withId(android.R.id.content))),
                isDisplayed()
            )
        )
        button.check(matches(isDisplayed()))

        val textView4 = onView(
            allOf(
                withId(R.id.totalCountTextView), withText("Number of results: 15"),
                withParent(withParent(withId(android.R.id.content))),
                isDisplayed()
            )
        )
        textView4.check(matches(withText("Number of results: 15")))

        val textView5 = onView(
            allOf(
                withId(R.id.totalCountTextView), withText("Number of results: 15"),
                withParent(withParent(withId(android.R.id.content))),
                isDisplayed()
            )
        )
        textView5.check(matches(withText("Number of results: 15")))

        val materialButton4 = onView(
            allOf(
                withId(R.id.decrementButton), withText("-"),
                childAtPosition(
                    childAtPosition(
                        withId(android.R.id.content),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        materialButton4.perform(click())

        val materialButton5 = onView(
            allOf(
                withId(R.id.decrementButton), withText("-"),
                childAtPosition(
                    childAtPosition(
                        withId(android.R.id.content),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        materialButton5.perform(click())

        val materialButton6 = onView(
            allOf(
                withId(R.id.decrementButton), withText("-"),
                childAtPosition(
                    childAtPosition(
                        withId(android.R.id.content),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        materialButton6.perform(click())

        val button2 = onView(
            allOf(
                withId(R.id.decrementButton), withText("-"),
                withParent(withParent(withId(android.R.id.content))),
                isDisplayed()
            )
        )
        button2.check(matches(isDisplayed()))

        val textView6 = onView(
            allOf(
                withId(R.id.totalCountTextView), withText("Number of results: 12"),
                withParent(withParent(withId(android.R.id.content))),
                isDisplayed()
            )
        )
        textView6.check(matches(isDisplayed()))

        val textView7 = onView(
            allOf(
                withId(R.id.totalCountTextView), withText("Number of results: 12"),
                withParent(withParent(withId(android.R.id.content))),
                isDisplayed()
            )
        )
        textView7.check(matches(withText("Number of results: 12")))

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
