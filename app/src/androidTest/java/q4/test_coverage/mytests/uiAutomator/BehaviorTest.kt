package q4.test_coverage.mytests.uiAutomator

import android.content.Context
import android.content.Intent
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SdkSuppress
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.Until
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
@SdkSuppress(minSdkVersion = 18)
class BehaviorTest {

    /** Класс UiDevice предоставляет доступ к вашему устройству.
    Именно через UiDevice вы можете управлять устройством, открывать приложения
    и находить нужные элементы на экране */
    private val uiDevice: UiDevice = UiDevice.getInstance(getInstrumentation())

    //Контекст нам понадобится для запуска нужных экранов и получения packageName
    private val context = ApplicationProvider.getApplicationContext<Context>()

    //Путь к классам нашего приложения, которые мы будем тестировать
    private val packageName = context.packageName

    @Before
    fun setup() {
        /** Для начала сворачиваем все приложения, если у нас что-то запущено */
        uiDevice.pressHome()
        /** Запускаем наше приложение */
        val intent = context.packageManager.getLaunchIntentForPackage(packageName)
        /** Мы уже проверяли Интент на null в предыдущем тесте (InitialTest), поэтому допускаем,
         * что Интент у нас не null */
        intent!!.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        //Чистим бэкстек от запущенных ранее Активити
        context.startActivity(intent)
        //Ждем, когда приложение откроется на смартфоне чтобы начать тестировать его элементы
        uiDevice.wait(Until.hasObject(By.pkg(packageName).depth(0)), TIMEOUT)
    }

    companion object {
        private const val TIMEOUT = 5000L
    }

    /** Убеждаемся, что приложение открыто. Для этого достаточно найти
     * на экране любой элемент и проверить его на null */
    @Test
    fun test_MainActivityIsStarted() {
        //Через uiDevice находим editText
        val editText = uiDevice.findObject(By.res(packageName, "searchEditText"))

        Assert.assertNotNull(editText) //Проверяем на null
    }

    /** Убеждаемся, что поиск работает как ожидается */
    @Test
    fun test_SearchIsPositive() {
        //Через uiDevice находим editText
        val editText = uiDevice.findObject(By.res(packageName, "searchEditText"))

        editText.text = "UiAutomator" //Устанавливаем значение

        // Через uiDevice находим кнопку
        val searchButton = uiDevice.findObject(By.res(packageName, "searchButton"))

        searchButton.click() // Кликаем

        //Ожидаем конкретного события: появления текстового поля totalCountTextView.
        //Это будет означать, что сервер вернул ответ с какими-то данными, то есть запрос отработал.
        val changedText =
            uiDevice.wait(
                Until.findObject(By.res(packageName, "totalCountTextView")),
                TIMEOUT
            )
        //Убеждаемся, что сервер вернул корректный результат. Обратите внимание, что количество
        //результатов может варьироваться во времени, потому что количество репозиториев
        // постоянно меняется.
        Assert.assertEquals(changedText.text.toString(), "Number of results: 686")
    }

    /** Убеждаемся, что DetailsScreen открывается */
    @Test
    fun test_OpenDetailsScreen() {

        openDetailsScreen()

        val changedText =
            uiDevice.wait(
                Until.findObject(By.res(packageName, "totalCountTextView")),
                TIMEOUT
            )

        Assert.assertEquals(changedText.text, "Number of results: 0")
    }

    @Test
    fun test_OpenDetailsScreen_andMakeSure_IsCorrectRepos() {

        val editText = uiDevice.findObject(By.res(packageName, "searchEditText"))

        editText.text = "founders edition"

        val searchButton = uiDevice.findObject(By.res(packageName, "searchButton"))

        searchButton.click()

        val receivedCountRepos = uiDevice.wait(
            Until.findObject(By.res(packageName, "totalCountTextView")),
            TIMEOUT
        )

        val memory = receivedCountRepos.text.toString()

        openDetailsScreen()

        val changedText = uiDevice.wait(
            Until.findObject(By.res(packageName, "totalCountTextView")),
            TIMEOUT
        )

        Assert.assertEquals(changedText.text, memory)
    }

    @Test
    fun test_SearchIsNegative() {

        val editText = uiDevice.findObject(By.res(packageName, "searchEditText"))

        editText.text = "UiAutomator"

        val searchButton = uiDevice.findObject(By.res(packageName, "searchButton"))

        searchButton.click()

        val changedText =
            uiDevice.wait(
                Until.findObject(By.res(packageName, "totalCountTextView")),
                TIMEOUT
            )
        Assert.assertNotEquals(changedText.text.toString(), "Number of results: 710")
    }

    @Test
    fun detailScreen_ButtonIncrementTest() {
        openDetailsScreen()

        val buttonIncrement =
            uiDevice.wait(
                Until.findObject(By.res(packageName, "incrementButton")),
                TIMEOUT
            )
        Assert.assertTrue(buttonIncrement.isClickable)
        Assert.assertTrue(buttonIncrement.isEnabled)
    }

    @Test
    fun detailScreen_ButtonDecrementTest() {
        openDetailsScreen()

        val buttonDecrement =
            uiDevice.wait(
                Until.findObject(By.res(packageName, "decrementButton")),
                TIMEOUT
            )
        Assert.assertTrue(buttonDecrement.isClickable)
        Assert.assertTrue(buttonDecrement.isEnabled)
    }

    @Test
    fun detailScreen_ButtonIncrement_WorksCorrect() {
        openDetailsScreen()

        val buttonIncrement =
            uiDevice.wait(
                Until.findObject(By.res(packageName, "incrementButton")),
                TIMEOUT
            )

        val textView = uiDevice.findObject(By.res(packageName, "totalCountTextView"))
        textView.text = "1"
        val value = textView.text[textView.text.length - 1].digitToInt()
        buttonIncrement.click()
        val newValue = textView.text[textView.text.length - 1].digitToInt()
        Assert.assertEquals(value, newValue - 1)
    }

    @Test
    fun detailScreen_ButtonDecrement_WorksCorrect() {
        openDetailsScreen()

        val buttonDecrement =
            uiDevice.wait(
                Until.findObject(By.res(packageName, "decrementButton")),
                TIMEOUT
            )

        val textView = uiDevice.findObject(By.res(packageName, "totalCountTextView"))
        textView.text = "1"
        val value = textView.text[textView.text.length - 1].digitToInt()
        buttonDecrement.click()
        val newValue = textView.text[textView.text.length - 1].digitToInt()
        Assert.assertEquals(value + 1, newValue)
    }

    @Test
    fun detailScreen_totalCountTextView_isVisible() {
        openDetailsScreen()

        val totalCountTextView =
            uiDevice.wait(
                Until.findObject(By.res(packageName, "totalCountTextView")),
                TIMEOUT
            )
        totalCountTextView.visibleBounds
        Assert.assertTrue(totalCountTextView.isEnabled)
    }

    @Test
    fun detailScreen_totalCountTextView_NotNull() {
        openDetailsScreen()

        val totalCountTextView =
            uiDevice.wait(
                Until.findObject(By.res(packageName, "totalCountTextView")),
                TIMEOUT
            )

        Assert.assertNotNull(totalCountTextView)
    }

    private fun openDetailsScreen() {
        val toDetails = uiDevice.findObject(
            By.res(
                packageName,
                "toDetailsActivityButton"
            )
        )
        toDetails.click()
    }

}