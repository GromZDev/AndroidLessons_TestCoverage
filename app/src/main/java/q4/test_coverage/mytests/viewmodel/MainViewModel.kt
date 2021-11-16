package q4.test_coverage.mytests.viewmodel

import androidx.lifecycle.LiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import q4.test_coverage.mytests.interactor.MainInterActor
import q4.test_coverage.mytests.model.AppState
import q4.test_coverage.mytests.utils.networkstatus.parseOnlineSearchResults

class MainViewModel(
    private val interActor: MainInterActor
) : BaseViewModel<AppState>() {

    private val livedataToObserve: LiveData<AppState> = _mutableLiveData

    fun subscribe(): LiveData<AppState> {
        return livedataToObserve
    }

    /** Coroutines -  */
    override fun getData(word: String, isOnline: Boolean) {
        _mutableLiveData.value = AppState.Loading(null)
        cancelJob()

        viewModelCoroutineScope.launch { startInterActor(word, isOnline) }
    }

    private suspend fun startInterActor(word: String, isOnline: Boolean) =
        withContext(Dispatchers.IO) {
            _mutableLiveData.postValue(
                parseOnlineSearchResults(
                    interActor.getData(
                        word,
                        isOnline
                    )
                )
            )
        }

    override fun handleError(error: Throwable) {
        _mutableLiveData.postValue(AppState.Error(error))
    }

    override fun onCleared() {
        _mutableLiveData.value = AppState.Success(null)
        super.onCleared()
    }
}