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
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SdkSuppress(minSdkVersion = 18)
class OpenYouTubeApp {

    private val uiDevice: UiDevice =
        UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())

    @Test
    fun openYouTube() {
        uiDevice.pressBack()
        uiDevice.pressHome()

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


        val searchTextField = appViews.getChildByDescription(
            UiSelector()
                .className(ImageView::class.java.name),
            "Search"
        )
        searchTextField.click()

        val text = "kotlin tutorial"

        val appSearchItem: UiObject = uiDevice.findObject(
            UiSelector().className("android.widget.EditText")
        )
        appSearchItem.clearTextField()
        appSearchItem.text = text

        uiDevice.pressEnter()

    }
}