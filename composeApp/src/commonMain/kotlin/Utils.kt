import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime


fun displayCurrentDateTime():String{
    val currentTimeStamp = Clock.System.now()
val date = currentTimeStamp.toLocalDateTime(TimeZone.currentSystemDefault())

    return  "${date.dayOfMonth} ${date.month.name} ${date.year}"
}
