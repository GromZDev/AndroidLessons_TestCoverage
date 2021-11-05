package q4.test_coverage.mytests

import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.UiObject2
import androidx.test.uiautomator.Until

internal const val TEST_NUMBER = 42
internal const val TEST_NUMBER_OF_RESULTS_ZERO = "Number of results: 0"
internal const val TEST_NUMBER_OF_RESULTS_PLUS_1 = "Number of results: 1"
internal const val TEST_NUMBER_OF_RESULTS_MINUS_1 = "Number of results: -1"
internal const val ZERO_VALUE = 0
internal const val SEARCH_EDIT_TEXT = "searchEditText"
internal const val SEARCH_BUTTON = "searchButton"
internal const val TOTAL_COUNT_TEXTVIEW = "totalCountTextView"
internal const val NUMBER_OF_RESULTS = "Number of results: "
internal const val INCREMENT_BUTTON = "incrementButton"
internal const val SOME_QUERY_TEXT = "some query"
internal const val TIMEOUT = 5000L
internal const val DECREMENT_BUTTON = "decrementButton"
internal const val FOUNDERS_EDITION = "founders edition"

internal class SharedTestData {

    fun findObjectByRes(uiDevice: UiDevice, pName: String, view: String) : UiObject2 {
        return uiDevice.wait(
            Until.findObject(By.res(pName, view)),
            TIMEOUT
        )
    }
}
