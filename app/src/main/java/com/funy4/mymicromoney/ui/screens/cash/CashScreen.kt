package com.funy4.mymicromoney.ui.screens.cash

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.funy4.mymicromoney.R
import com.funy4.mymicromoney.ui.IconUtil
import com.funy4.mymicromoney.ui.screens.cash.view.AddCashItem
import com.funy4.mymicromoney.ui.screens.cash.view.CashItem
import com.funy4.mymicromoney.ui.screens.cash.viewmodel.CashEvent
import com.funy4.mymicromoney.ui.screens.cash.viewmodel.CashViewModel
import com.funy4.mymicromoney.ui.screens.expensescreen.views.DeleteObjectAlertDialog
import com.funy4.mymicromoney.ui.screens.income.view.AddCashCard

@Composable
fun CashScreen(
    modifier: Modifier = Modifier,
    viewModel: CashViewModel = hiltViewModel(),
    navController: NavController
) {
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
        val density = LocalDensity.current
        AnimatedVisibility(visible = viewModel.showAddCashWindow.value,
            enter = slideInVertically { with(density) { -20.dp.roundToPx() } }  // Slide in from 20 dp from the top.
                    + expandVertically(expandFrom = Alignment.Top)  // Expand from the top.
                    + fadeIn(initialAlpha = 0.3f),  // Fade in with the initial alpha of 0.3f.
            exit = slideOutVertically {
                with(density) { 20.dp.roundToPx() }
            } + shrinkVertically()
                    + fadeOut()
        ) {
            AddCashCard(
                modifier = Modifier
                    .padding(20.dp),
                title = "Добавить счет",
                onConfirmClick = { name, baseCash ->
                    viewModel.onEvent(CashEvent.OnAddCashClick(name, baseCash))
                },
                selectedIconId = viewModel.selectedIcon.value,
                onCloseClick = { viewModel.onEvent(CashEvent.OnCloseAddCashWindow) },
                onIconClick = { viewModel.onEvent(CashEvent.OnAddCashIconClick) }
            )
        }
        LazyColumn {
            item {
                Text(
                    modifier = Modifier.padding(top = 16.dp, start = 20.dp),
                    text = "Счета",
                    fontSize = 30.sp,
                    color = Color.DarkGray,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
            items(viewModel.cashList.value) { cash ->
                CashItem(
                    title = cash.name,
                    money = cash.money,
                    iconId = cash.icon,
                    onCrossClick = { viewModel.onEvent(CashEvent.OnDeleteCashClick(cash)) }
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
            item {
                AddCashItem(modifier.height(80.dp)) { viewModel.onEvent(CashEvent.OnCreateCashClick) }
            }
        }
    }
    if (viewModel.showDeleteCashDialog.value) {
        DeleteObjectAlertDialog(
            onDismiss = { viewModel.onEvent(CashEvent.OnDismissAlertDialog) },
            onConfirm = { viewModel.onEvent(CashEvent.OnConfirmAlertDialog) })
    }
    if (viewModel.showSelectIconDialog.value) {
        IconSelectorDialog(
            icons = IconUtil.allCashIconsIds,
            onDismissRequest = { viewModel.onEvent(CashEvent.OnDismissSelectIconDialog) },
            onIconSelected = { icon ->
                viewModel.onEvent(CashEvent.OnSelectIcon(icon))
                viewModel.onEvent(CashEvent.OnDismissSelectIconDialog)
            }
        )
    }
}

@Composable
fun IconSelectorDialog(
    icons: List<Int>,
    onDismissRequest: () -> Unit,
    onIconSelected: (Int) -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = {
            Text(text = "Выберите иконку")
            Spacer(modifier = Modifier.padding(8.dp))
        },
        text = {
            LazyColumn {
                items(icons) { icon ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onIconSelected(icon) }
                            .padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            painter = painterResource(id = icon),
                            contentDescription = null,
                            modifier = Modifier.size(42.dp)
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                    }
                }
            }
        },
        confirmButton = {
            Button(onClick = onDismissRequest) {
                Text("Отмена")
            }
        }
    )
}