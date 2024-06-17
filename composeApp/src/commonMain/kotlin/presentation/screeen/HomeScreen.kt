package presentation.screeen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import cafe.adriel.voyager.core.screen.Screen
import data.remote.api.CurrencyApiServiceImpl
import domain.CurrencyApiService

class HomeScreen:Screen {
    @Composable
    override fun Content() {
        LaunchedEffect(Unit){
            CurrencyApiServiceImpl().getLatestExchangeRates()
        }
    }
}