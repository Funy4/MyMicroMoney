package com.funy4.mymicromoney.ui.screens.income

import android.widget.Toast
import androidx.compose.animation.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.funy4.mymicromoney.R
import com.funy4.mymicromoney.ui.IconUtil
import com.funy4.mymicromoney.ui.navigation.Screen
import com.funy4.mymicromoney.ui.screens.cash.IconSelectorDialog
import com.funy4.mymicromoney.ui.screens.expensescreen.viewmodel.ExpensesEvent
import com.funy4.mymicromoney.ui.screens.expensescreen.views.AddCategoryCard
import com.funy4.mymicromoney.ui.screens.expensescreen.views.DeleteObjectAlertDialog
import com.funy4.mymicromoney.ui.screens.income.view.IncomeIconsList
import com.funy4.mymicromoney.ui.screens.income.view.IncomeTransactionsBottomSheet
import com.funy4.mymicromoney.ui.screens.income.viewmodel.IncomeEvent
import com.funy4.mymicromoney.ui.screens.income.viewmodel.IncomeUiEvent
import com.funy4.mymicromoney.ui.screens.income.viewmodel.IncomeViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun IncomeScreen(
    modifier: Modifier = Modifier,
    viewModel: IncomeViewModel = hiltViewModel(),
    navController: NavController,
) {
    val coroutineScope = rememberCoroutineScope()
    val bottomSheetState = rememberBottomSheetState(initialValue = BottomSheetValue.Collapsed)
    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = bottomSheetState
    )



    val density = LocalDensity.current // For animations
    val context = LocalContext.current

    LaunchedEffect(key1 = true) {
        viewModel.viewModelScope.launch {
            viewModel.uiEvent.collect { event ->
                when (event) {
                    is IncomeUiEvent.OpenAddTransactionScreen -> {
                        navController.navigate(Screen.AddExpense.route + "/${event.id}")
                    }

                    IncomeUiEvent.ShowNoCashSize -> {
                        Toast.makeText(context, "У вас нет счетов!", Toast.LENGTH_SHORT).show()
                    }
                }
            }

        }
    }

    Image(
        painter = painterResource(id = R.drawable.background_income_screen),
        contentDescription = null,
        modifier = modifier.fillMaxSize(),
        contentScale = ContentScale.FillBounds
    )
    BottomSheetScaffold(
        modifier = modifier,
        scaffoldState = bottomSheetScaffoldState,
        drawerElevation = 100.dp,
        sheetShape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
        sheetContent = { IncomeTransactionsBottomSheet(viewModel.transactions.value) },
        backgroundColor = androidx.compose.ui.graphics.Color.Transparent,
        sheetBackgroundColor = MaterialTheme.colors.primary,
    ) {
        Column {
            AnimatedVisibility(visible = viewModel.showAddIncome.value,
                enter = slideInVertically { with(density) { -20.dp.roundToPx() } }  // Slide in from 20 dp from the top.
                        + expandVertically(expandFrom = Alignment.Top)  // Expand from the top.
                        + fadeIn(initialAlpha = 0.3f),  // Fade in with the initial alpha of 0.3f.
                exit = slideOutVertically {
                    with(density) { 20.dp.roundToPx() }
                } + shrinkVertically()
                        + fadeOut()
            ) {
                AddCategoryCard(
                    modifier = Modifier
                        .padding(horizontal = 20.dp)
                        .padding(top = 20.dp),
                    title = "Добавить категорию дохода",
                    onConfirmClick = { viewModel.onEvent(IncomeEvent.OnSaveIncomeClick(it)) },
                    onCloseClick = { viewModel.onEvent(IncomeEvent.OnCloseAddIncomeClick) },
                    iconId = viewModel.selectedIconId.value,
                    onIconClick = { viewModel.onEvent(IncomeEvent.OnCreateIncomeIconClick) }
                )
            }

            Spacer(Modifier.height(10.dp))
            IncomeIconsList(
                modifier = modifier,
                incomes = viewModel.incomeList.value,
                showAddIcon = !viewModel.showAddIncome.value,
                onItemClick = { item ->
                    coroutineScope.launch {
                        viewModel.onEvent(IncomeEvent.OnIncomeClick(item))
                    }
                },
                onAddExpensesClick = {
                    viewModel.onEvent(IncomeEvent.OnAddIncomeClick)
                },
                onLongPressItem = { viewModel.onEvent(IncomeEvent.OnLongItemPress(it)) }
            )
        }
        if (viewModel.showSelectIconDialog.value) {
            IconSelectorDialog(
                icons = IconUtil.allExpensesIncomeIconsIds,
                onDismissRequest = { viewModel.onEvent(IncomeEvent.OnCreateIncomeIconDismiss) }) {
                viewModel.onEvent(IncomeEvent.OnCreateIncomeSelectIcon(it))
            }
        }
        if(viewModel.showDeleteDialog.value) {
            DeleteObjectAlertDialog(
                onDismiss = { viewModel.onEvent(IncomeEvent.OnDismissDeleteDialog) },
                onConfirm = { viewModel.onEvent(IncomeEvent.OnAcceptDeleteIncome) }
            )
        }

    }
}