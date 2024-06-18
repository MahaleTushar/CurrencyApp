package domain

import domain.model.Currency
import domain.model.RequestState
import kotlinx.coroutines.flow.Flow

interface DbRepository {
    fun configureTheRealm()
    suspend fun insertCurrencyData(currency: Currency)
    fun reaCurrencyData():Flow<RequestState<List<Currency>>>
}