package com.funy4.mymicromoney.ui.screens.expensescreen

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
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.funy4.mymicromoney.R
import com.funy4.mymicromoney.ui.navigation.Screen
import com.funy4.mymicromoney.ui.screens.expensescreen.viewmodel.ExpensesEvent
import com.funy4.mymicromoney.ui.screens.expensescreen.viewmodel.ExpensesUiEvent
import com.funy4.mymicromoney.ui.screens.expensescreen.viewmodel.ExpensesViewModel
import com.funy4.mymicromoney.ui.screens.expensescreen.views.AddCategoryCard
import com.funy4.mymicromoney.ui.screens.expensescreen.views.DeleteObjectAlertDialog
import com.funy4.mymicromoney.ui.screens.expensescreen.views.ExpenseTransactionsBottomSheet
import com.funy4.mymicromoney.ui.screens.expensescreen.views.ExpensesIconsList
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ExpensesScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: ExpensesViewModel = hiltViewModel()
) {
    val coroutineScope = rememberCoroutineScope()
    val bottomSheetState = rememberBottomSheetState(initialValue = BottomSheetValue.Collapsed)
    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = bottomSheetState
    )

    val expensesList by viewModel.expensesList.collectAsState(initial = emptyList())
    val transactionList by viewModel.transactionList.collectAsState(initial = emptyList())

    val density = LocalDensity.current // For animations

    LaunchedEffect(key1 = true) {
        viewModel.viewModelScope.launch {
            viewModel.uiEvent.collect { event ->
                when (event) {
//                    ExpensesUiEvent.OpenAddExpenseScreen -> {
//                        println(showAddExpenses)
//                        showAddExpenses = true
//                    }
                    is ExpensesUiEvent.OpenAddTransactionScreen -> {
                        navController.navigate(Screen.AddExpense.route + "/${event.id}")
                    }
                }
            }

        }
    }

    Image(
        painter = painterResource(id = R.drawable.background_expenses_screen),
        contentDescription = null,
        modifier = modifier.fillMaxSize(),
        contentScale = ContentScale.FillBounds
    )
    BottomSheetScaffold(
        modifier = modifier,
        scaffoldState = bottomSheetScaffoldState,
        drawerElevation = 100.dp,
        sheetShape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
        sheetContent = { ExpenseTransactionsBottomSheet(transactionList) },
        backgroundColor = Color.Transparent,
        sheetBackgroundColor = MaterialTheme.colors.primary,
    ) {
        Column {
            AnimatedVisibility(visible = viewModel.showAddExpense.value,
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
                    title = "Add expense",
                    onConfirmClick = { viewModel.onEvent(ExpensesEvent.OnSaveExpenseClick(it)) },
                    onCloseClick = { viewModel.onEvent(ExpensesEvent.OnCloseAddExpenseClick) }
                )
            }

            Spacer(Modifier.height(10.dp))
            ExpensesIconsList(
                modifier = modifier,
                expenses = expensesList,
                showAddIcon = !viewModel.showAddExpense.value,
                onItemClick = { viewModel.onEvent(ExpensesEvent.OnExpenseClick(it)) },
                onLongPressItem = { viewModel.onEvent(ExpensesEvent.OnLongPressExpense(it)) },
                onAddExpensesClick = { viewModel.onEvent(ExpensesEvent.OnAddExpenseClick) },
            )
        }
        if (viewModel.showDeleteDialog.value) {
            DeleteObjectAlertDialog(
                onDismiss = {viewModel.onEvent(ExpensesEvent.OnDismissDeleteDialogClick)},
                onConfirm = {viewModel.onEvent(ExpensesEvent.OnConfirmDeleteDialogClick)}
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
