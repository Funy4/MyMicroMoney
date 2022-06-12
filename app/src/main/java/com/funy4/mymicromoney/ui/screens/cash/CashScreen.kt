package com.funy4.mymicromoney.ui.screens.cash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.funy4.mymicromoney.R
import com.funy4.mymicromoney.ui.screens.cash.viewmodel.CashEvent
import com.funy4.mymicromoney.ui.screens.cash.viewmodel.CashViewModel
import com.funy4.mymicromoney.ui.screens.expensescreen.views.CloseIcon
import com.funy4.mymicromoney.ui.screens.expensescreen.views.DeleteObjectAlertDialog
import com.funy4.mymicromoney.ui.theme.MyMicroMoneyTheme

@Composable
fun CashScreen(
    modifier: Modifier = Modifier,
    viewModel: CashViewModel = hiltViewModel(),
    navController: NavController
) {
    val cashList by viewModel.cashList.collectAsState(initial = emptyList())

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect {

        }
    }
    Image(
        modifier = modifier.fillMaxSize(),
        painter = painterResource(id = R.drawable.background_cash_screen),
        contentDescription = null,
        contentScale = ContentScale.FillBounds
    )
    Column(
        modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        LazyColumn {
            item {
                Text(
                    modifier = Modifier.padding(top = 16.dp, start = 20.dp),
                    text = "Счета",
                    fontSize = 30.sp,
                    color = Color.DarkGray,
                    fontWeight = FontWeight.Bold
                )
            }
            items(cashList) { cash ->
                Spacer(modifier = Modifier.height(16.dp))
                CashItem(
                    title = cash.name,
                    money = cash.money,
                    iconId = cash.icon,
                    onCrossClick = { viewModel.onEvent(CashEvent.OnDeleteCashClick(cash)) }
                )
            }
        }
    }
    if (viewModel.showDeleteCashDialog.value) {
        DeleteObjectAlertDialog(
            onDismiss = { viewModel.onEvent(CashEvent.OnDismissAlertDialog) },
            onConfirm = { viewModel.onEvent(CashEvent.OnConfirmAlertDialog) })


    }
}

@Composable
fun CashItem(
    modifier: Modifier = Modifier,
    title: String,
    money: Double,
    iconId: Int,
    onCrossClick: () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(30.dp))
            .background(MaterialTheme.colors.secondary),
        contentAlignment = Alignment.TopEnd
    ) {
        CloseIcon {
            onCrossClick()
        }
        Box(
            modifier = modifier
                .fillMaxSize()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(25.dp)
                    .padding(end = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    modifier = Modifier.weight(6f),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Icon(
                        painterResource(id = iconId), contentDescription = "cash icon",
                        tint = Color.White
                    )
                    Spacer(Modifier.width(16.dp))
                    Text(
                        text = title,
                        fontSize = 22.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                Text(
                    modifier = Modifier.weight(3f),
                    text = "$money₽",
                    fontSize = 18.sp,
                    color = Color.White,
                    fontStyle = FontStyle.Italic,
                    textAlign = TextAlign.Right
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun Preview() {
    MyMicroMoneyTheme() {
        Column(

            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CashItem(
                title = "Наличные",
                money = 41214.0,
                iconId = R.drawable.ic_wallet,
                onCrossClick = {}
            )
        }
    }
}