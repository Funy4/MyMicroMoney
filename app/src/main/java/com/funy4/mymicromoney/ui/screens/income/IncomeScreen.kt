package com.funy4.mymicromoney.ui.screens.income

import androidx.compose.animation.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.funy4.mymicromoney.R
import com.funy4.mymicromoney.ui.navigation.Screen
import com.funy4.mymicromoney.ui.screens.expensescreen.views.AddCategoryCard
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

    val incomeList by viewModel.incomeList.collectAsState(initial = emptyList())
    val transactionList by viewModel.transactionList.collectAsState(initial = emptyList())

    val density = LocalDensity.current // For animations

    LaunchedEffect(key1 = true) {
        viewModel.viewModelScope.launch {
            viewModel.uiEvent.collect { event ->
                when (event) {
                    is IncomeUiEvent.OpenAddTransactionScreen -> {
                        navController.navigate(Screen.AddExpense.route + "/${event.id}")
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
        sheetContent = { IncomeTransactionsBottomSheet(transactionList) },
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
                    title = "Add income",
                    onConfirmClick = { viewModel.onEvent(IncomeEvent.OnSaveIncomeClick) },
                    onCloseClick = { viewModel.onEvent(IncomeEvent.OnCloseAddIncomeClick) }
                )
            }

            Spacer(Modifier.height(10.dp))
            IncomeIconsList(
                modifier = modifier,
                incomes = incomeList,
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
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewExpScreen() {
    Box(Modifier.fillMaxSize()) {
//        ExpensesIconsList(expenses = Mocks.expensesList, onItemClick = {}) {
//        ExpensesScreen(navController = rememberNavController())
    }
}