package q4.test_coverage.mytests

import android.os.Build
import android.widget.TextView
import androidx.test.core.app.ActivityScenario
import androidx.test.ext.junit.runners.AndroidJUnit4
import junit.framework.TestCase.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import q4.test_coverage.mytests.presenter.details.DetailsPresenter
import q4.test_coverage.mytests.view.details.DetailsActivity

private const val justValue = 55

@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.O_MR1])
class DetailsPresenterTest {

    private lateinit var scenario: ActivityScenario<DetailsActivity>
    private lateinit var presenter: DetailsPresenter

    @Before
    fun setUp() {
        scenario = ActivityScenario.launch(DetailsActivity::class.java)
        presenter = DetailsPresenter()
        scenario.onActivity {
            presenter.onAttach(it)
        }
    }

    @After
    fun onDetach_closeScenario() {
        scenario.onActivity {
            presenter.onDetach(it)
        }
        scenario.close()
    }

    @Test
    fun setCounter_View_TestSuccess() {
        presenter.setCounter(justValue)
        presenter.onIncrement()
        scenario.onActivity {
            val totalCountTextView = it.findViewById<TextView>(R.id.totalCountTextView)
            assertEquals("Number of results: 56", totalCountTextView.text)
        }
    }

    @Test
    fun onIncrement_View_TestSuccess() {
        presenter.onIncrement()
        scenario.onActivity {
            val totalCountTextView = it.findViewById<TextView>(R.id.totalCountTextView)
            assertEquals("Number of results: 1", totalCountTextView.text)
        }
    }

    @Test
    fun onDecrement_View_TestSuccess() {
        presenter.onDecrement()
        scenario.onActivity {
            val totalCountTextView = it.findViewById<TextView>(R.id.totalCountTextView)
            assertEquals("Number of results: -1", totalCountTextView.text)
        }
    }
}