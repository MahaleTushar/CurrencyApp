package di

import com.russhwolf.settings.Settings
import data.local.DbRepositoryImpl
import data.remote.api.CurrencyApiServiceImpl
import data.remote.api.PreferencesImpl
import domain.CurrencyApiService
import domain.DbRepository
import domain.PreferencesRepository
import org.koin.core.context.startKoin
import org.koin.dsl.module
import presentation.screeen.HomeViewModel
import kotlin.math.sin

val appModule = module {
    single {
        Settings()
    }
    single<PreferencesRepository> { PreferencesImpl(get()) }
    single<CurrencyApiService> { CurrencyApiServiceImpl(get()) }
    single<DbRepository> { DbRepositoryImpl() }
    factory {
        HomeViewModel(preferences = get(), api = get(), dbRepository = get())
    }
}

fun initializeKoin() {
    startKoin {
        modules(appModule)
    }
}