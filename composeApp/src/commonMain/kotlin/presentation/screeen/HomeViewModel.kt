package presentation.screeen

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import cafe.adriel.voyager.core.model.screenModelScope
import domain.CurrencyApiService
import domain.DbRepository
import domain.PreferencesRepository
import domain.model.Currency
import domain.model.RateStatus
import domain.model.RequestState
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock


sealed class HomeUiEvent {
    data object RefreshRates : HomeUiEvent()
}

class HomeViewModel(
    private val preferences: PreferencesRepository, private val api: CurrencyApiService,
    private val dbRepository: DbRepository
) : ScreenModel {
    private var _rateStatus: MutableState<RateStatus> = mutableStateOf(RateStatus.Idle)
    val rateStatus: State<RateStatus> get() = _rateStatus


    private var _sourceCurrency:MutableState<RequestState<Currency>> = mutableStateOf(RequestState.Idle)
    val  sourceCurrency : State<RequestState<Currency>> = _sourceCurrency

    private var _targetCurrency:MutableState<RequestState<Currency>> = mutableStateOf(RequestState.Idle)
    val  targetCurrency : State<RequestState<Currency>> = _targetCurrency
    init {
        screenModelScope.launch {
            fetchNewRates()
            getRateStatus()
        }
    }


    fun sendEvent(event: HomeUiEvent){
        when(event){
            HomeUiEvent.RefreshRates->{
                screenModelScope.launch {
                    fetchNewRates()
                }
            }
        }
    }
    private suspend fun fetchNewRates() {
        try {
            api.getLatestExchangeRates()
            getRateStatus()
        } catch (e: Exception) {
            println(e.message)
        }
    }

    private suspend fun getRateStatus() {
        _rateStatus.value = if (preferences.isDataFresh(
                currentTimestamp = Clock.System.now().toEpochMilliseconds()
            )
        ) RateStatus.Fresh
        else RateStatus.Stale
    }

}