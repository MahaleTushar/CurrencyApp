package data.local

import domain.DbRepository
import domain.model.Currency
import domain.model.RequestState
import kotlinx.coroutines.flow.Flow

class DbRepositoryImpl:DbRepository {
    override fun configureTheRealm() {
        TODO("Not yet implemented")
    }

    override suspend fun insertCurrencyData(currency: Currency) {
        TODO("Not yet implemented")
    }

    override fun reaCurrencyData(): Flow<RequestState<List<Currency>>> {
        TODO("Not yet implemented")
    }
}