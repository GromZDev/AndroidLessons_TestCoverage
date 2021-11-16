package q4.test_coverage.mytests

import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.robolectric.annotation.Config
import q4.test_coverage.mytests.interactor.HistoryInterActor
import q4.test_coverage.mytests.model.AppState
import q4.test_coverage.mytests.model.DataModel
import q4.test_coverage.mytests.model.Meanings
import q4.test_coverage.mytests.model.datasource.roomlocal.RepositoryLocal
import q4.test_coverage.mytests.repository.Repository
import q4.test_coverage.mytests.viewmodel.HistoryViewModel

@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.O_MR1])
@ExperimentalCoroutinesApi
class HistoryViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var testCoroutineRule = TestCoroutineRule()

    private lateinit var historyViewModel: HistoryViewModel

    @Mock
    private lateinit var interActor: HistoryInterActor

    @Mock
    private lateinit var remoteRepository: Repository<List<DataModel>>

    @Mock
    private lateinit var localRepository: RepositoryLocal<List<DataModel>>

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        interActor = HistoryInterActor(remoteRepository, localRepository)
        historyViewModel = HistoryViewModel(interActor)
    }

    @Test
    fun live_Data_IsNotNull() {
        testCoroutineRule.runBlockingTest {
            val observer = Observer<AppState> {}
            val liveData = historyViewModel.subscribe()

            Mockito.`when`(interActor.getData("Refuse", fromRemoteSource = false))
                .thenReturn(AppState.Success(listOf(DataModel("Refuse", getTestData()))))

            try {
                liveData.observeForever(observer)
                historyViewModel.getData("Refuse", isOnline = false)
                Assert.assertNotNull(liveData.value)
            } finally {
                liveData.removeObserver(observer)
            }
        }
    }

    @Test
    fun appState_ReturnsError() {
        testCoroutineRule.runBlockingTest {
            val observer = Observer<AppState> {}
            val liveData = historyViewModel.subscribe()
            val error = Throwable("Error has occurred")

            Mockito.`when`(interActor.getData("Refuse", fromRemoteSource = false))
                .thenReturn(AppState.Error(error))

            try {
                liveData.observeForever(observer)
                historyViewModel.handleError(error)
                val value: AppState.Error = liveData.value as AppState.Error
                Assert.assertEquals(value.error.message, error.message)
            } finally {
                liveData.removeObserver(observer)
            }
        }
    }

    @Test
    fun getData_TestCorrect() {
        testCoroutineRule.runBlockingTest {

            val dataModel = DataModel("Some", getTestData())

            Mockito.`when`(localRepository.getData("Good")).thenReturn(
                listOf(dataModel)
            )

            historyViewModel.getData("Good", isOnline = false)
            Mockito.verify(localRepository, Mockito.times(1)).getData("Good")
        }
    }

    @Test
    fun saveToDB_TestCorrect() {
        testCoroutineRule.runBlockingTest {

            Mockito.`when`(localRepository.getData("Distance")).thenReturn(
                listOf(DataModel(null, listOf()))
            )

            historyViewModel.getData("Distance", isOnline = false)
            Mockito.verify(localRepository, Mockito.times(1))
                .saveToDB(AppState.Success(localRepository.getData("Distance")))
        }
    }

    @Test
    fun liveData_HasObservers_WhenSubscribe() {
        testCoroutineRule.runBlockingTest {
            val observer = Observer<AppState> {}
            val liveData = historyViewModel.subscribe()

            try {
                liveData.observeForever(observer)
                historyViewModel.getData("Some", isOnline = true)
                Assert.assertTrue(liveData.hasActiveObservers())

            } finally {
                liveData.removeObserver(observer)
            }
        }
    }

    private fun getTestData(): MutableList<Meanings> {
        val meaning1 = Meanings(translation = null, "1", "image1", "refuse1")
        val meaning2 = Meanings(translation = null, "2", "image2", "refuse2")
        val list = mutableListOf<Meanings>()
        list.add(meaning1)
        list.add(meaning2)
        return list
    }
}