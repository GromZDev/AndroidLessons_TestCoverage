package q4.test_coverage.mytests.uiAutomator

import android.widget.ImageView
import android.widget.TextView
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SdkSuppress
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.UiObject
import androidx.test.uiautomator.UiScrollable
import androidx.test.uiautomator.UiSelector
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SdkSuppress(minSdkVersion = 18)
class OpenYouTubeApp {

    private val uiDevice: UiDevice =
        UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())

    @Before
    fun init() {
        uiDevice.pressBack()
        uiDevice.pressHome()
    }

    @Test
    fun openYouTube() {

        val appViews = openApp()

        val text = setText(appViews)

        search(text)

    }

    private fun search(text: String) {
        val appSearchItem: UiObject = uiDevice.findObject(
            UiSelector().className("android.widget.EditText")
        )
        appSearchItem.clearTextField()
        appSearchItem.text = text

        uiDevice.pressEnter()
    }

    private fun setText(appViews: UiScrollable): String {
        val searchTextField = appViews.getChildByDescription(
            UiSelector()
                .className(ImageView::class.java.name),
            "Search"
        )
        searchTextField.click()

        return "kotlin tutorial"
    }

    private fun openApp(): UiScrollable {
        uiDevice.swipe(500, 1500, 500, 0, 5)
        val appViews = UiScrollable(UiSelector().scrollable(false))
        val youTubeApp = appViews
            .getChildByText(
                UiSelector()
                    .className(TextView::class.java.name),
                "YouTube"
            )

        youTubeApp.clickAndWaitForNewWindow() // Открываем

        val youTubeValidation =
            uiDevice.findObject(UiSelector().packageName("com.google.android.youtube"))
        Assert.assertTrue(youTubeValidation.exists())
        return appViews
    }
}