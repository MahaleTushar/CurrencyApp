package domain.model

import androidx.compose.ui.graphics.Color
import com.example.compose.freshColor
import com.example.compose.staleColor

enum class RateStatus(
    val title: String,
    val color: Color
) {
    Idle("Rates",Color.White),
    Fresh("Fresh Rates", freshColor),
    Stale("Rates", staleColor)
}


