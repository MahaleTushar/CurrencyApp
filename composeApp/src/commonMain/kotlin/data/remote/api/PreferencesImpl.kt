package data.remote.api

import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.ObservableSettings
import com.russhwolf.settings.Settings
import com.russhwolf.settings.coroutines.FlowSettings
import com.russhwolf.settings.coroutines.toFlowSettings
import domain.PreferencesRepository
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

@OptIn(ExperimentalSettingsApi::class)
class PreferencesImpl(private val settings: Settings) : PreferencesRepository {
    companion object {
        const val TIMESTAMP_KEY = "lastUpdate"
    }

    override suspend fun saveLastUpdated(lastUpdated: String) {
        flowSettings.putLong(
            key = TIMESTAMP_KEY,
            value = Instant.parse(lastUpdated).toEpochMilliseconds()
        )
    }


    private val flowSettings: FlowSettings = (settings as ObservableSettings).toFlowSettings()

    override suspend fun isDataFresh(currentTimestamp: Long): Boolean {
        val savedTimeStamp = flowSettings.getLong(TIMESTAMP_KEY, 0L)

        return if (savedTimeStamp != 0L) {
            val currentTimeStamp = Instant.fromEpochMilliseconds(currentTimestamp)
            val savedInstant = Instant.fromEpochMilliseconds(savedTimeStamp)

            val currentDateTime = currentTimeStamp.toLocalDateTime(TimeZone.currentSystemDefault())
            val savedDateTime = savedInstant.toLocalDateTime(TimeZone.currentSystemDefault())

            val daysDifference = currentDateTime.date.dayOfYear - savedDateTime.date.dayOfYear
            daysDifference > 1
        } else false
    }
}