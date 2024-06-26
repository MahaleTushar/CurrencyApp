package presentation.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.compose.headerColor
import com.example.compose.staleColor
import currencyapp.composeapp.generated.resources.Res
import currencyapp.composeapp.generated.resources.baseline_refresh
import currencyapp.composeapp.generated.resources.compose_multiplatform
import displayCurrentDateTime
import domain.model.Currency
import domain.model.CurrencyCode
import domain.model.RateStatus
import domain.model.RequestState
import org.jetbrains.compose.resources.painterResource


@Composable
fun HomeHeader(
    status: RateStatus,
    source: RequestState<Currency>,
    target: RequestState<Currency>,
    amount: Double,
    onRatesRefresh: () -> Unit,
    onSwitchClick: () -> Unit,
    onAmountChange: (Double) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth()
            .clip(RoundedCornerShape(bottomStart = 12.dp, bottomEnd = 12.dp))
            .background(headerColor)
            .padding(all = 24.dp)
    ) {
        Spacer(modifier = Modifier.height(24.dp))
        RatesStatus(status, onRatesRefresh = onRatesRefresh)
        Spacer(modifier = Modifier.height(24.dp))
        CurrencyInputs(
            source = source,
            target = target,
            onSwitchClick = onSwitchClick
        )
        Spacer(modifier = Modifier.height(24.dp))
        AmountInput(
            amount = amount,
            onAmountChange = onAmountChange
        )
    }

}

@Composable
fun RatesStatus(
    status: RateStatus,
    onRatesRefresh: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row {
            Image(
                modifier = Modifier.size(50.dp),
                painter = painterResource(Res.drawable.compose_multiplatform),
                contentDescription = "Exchange Rate illustration"
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(text = displayCurrentDateTime(), color = Color.White)
                Text(
                    text = status.title,
                    fontSize = MaterialTheme.typography.bodySmall.fontSize,
                    color = status.color
                )
            }
        }
        if (status == RateStatus.Stale) {
            IconButton(onClick = onRatesRefresh) {
                Icon(
                    modifier = Modifier.size(24.dp),
                    painter = painterResource(Res.drawable.compose_multiplatform),
                    contentDescription = "Refresh icon",
                    tint = staleColor
                )
            }
        }
    }
}


@Composable
fun CurrencyInputs(
    source: RequestState<Currency>,
    target: RequestState<Currency>,
    onSwitchClick: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        CurrencyView("from",
            currency = source,
            onClick = {})
        Spacer(modifier = Modifier.height(14.dp))

        Icon(
            modifier = Modifier.size(24.dp).clickable {
                onSwitchClick()
            },
            painter = painterResource(Res.drawable.baseline_refresh),
            tint = Color.Unspecified,
            contentDescription = "Country flag"
        )
//        IconButton(modifier = Modifier.padding(top = 24.dp), onClick = onSwitchClick) {
//            Icon(
//                painter = painterResource(Res.drawable.baseline_refresh),
//                contentDescription = "Switch icon",
//            )
//        }
        Spacer(modifier = Modifier.height(14.dp))
        CurrencyView("from",
            currency = source,
            onClick = {})
    }
}

@Composable
fun RowScope.CurrencyView(
    placeHolder: String,
    currency: RequestState<Currency>,
    onClick: () -> Unit
) {
    Column(modifier = Modifier.weight(1f)) {
        Text(
            modifier = Modifier.padding(start = 12.dp),
            text = placeHolder,
            fontSize = MaterialTheme.typography.bodySmall.fontSize,
            color = Color.White
        )
        Spacer(modifier = Modifier.height(4.dp))
        Row(
            modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(8.dp))
                .background(Color.White.copy(alpha = 0.05f))
                .height(54.dp)
                .clickable {
                    onClick()
                },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            if (currency.isSuccess()) {
                Icon(
                    modifier = Modifier.size(24.dp),
                    painter = painterResource(Res.drawable.baseline_refresh),
                    tint = Color.Unspecified,
                    contentDescription = "Country flag"
                )
                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = CurrencyCode.valueOf(currency.getSuccessData().code).name,
                    fontWeight = FontWeight.Bold,
                    fontSize = MaterialTheme.typography.titleLarge.fontSize,
                    color = Color.White
                )

            }
        }
    }

}

@Composable
fun AmountInput(amount: Double, onAmountChange: (Double) -> Unit) {
    val tet by remember { mutableStateOf("") }

    TextField(
        value = "$amount",
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(size = 8.dp))
            .animateContentSize()
            .height(54.dp),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.White.copy(0.05f),
            unfocusedContainerColor = Color.White.copy(0.05f),
            disabledContainerColor = Color.White.copy(0.05f),
            errorContainerColor = Color.White.copy(0.05f),
            focusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            cursorColor = Color.White
        ),
        textStyle = TextStyle(
            color = Color.White,
            fontSize = MaterialTheme.typography.titleLarge.fontSize,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        ),
        onValueChange = { newInput: String -> onAmountChange(newInput.toDouble()) },
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
    )

}