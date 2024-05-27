package com.funy4.mymicromoney.ui.screens.expensescreen

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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.funy4.mymicromoney.R
import com.funy4.mymicromoney.ui.IconUtil
import com.funy4.mymicromoney.ui.navigation.Screen
import com.funy4.mymicromoney.ui.screens.cash.IconSelectorDialog
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
                    is ExpensesUiEvent.OpenAddTransactionScreen -> {
                        navController.navigate(Screen.AddExpense.route + "/${event.id}")
                    }

                    ExpensesUiEvent.ShowNoCashSize -> {
                        Toast.makeText(context, "У вас нет счетов!", Toast.LENGTH_SHORT).show()
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
        sheetContent = { ExpenseTransactionsBottomSheet(viewModel.transactionList.value) },
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
                    title = "Добавить категорию расходов",
                    onConfirmClick = { viewModel.onEvent(ExpensesEvent.OnSaveExpenseClick(it)) },
                    onCloseClick = { viewModel.onEvent(ExpensesEvent.OnCloseAddExpenseClick) },
                    onIconClick = { viewModel.onEvent(ExpensesEvent.OnSelectIconClick) },
                    iconId = viewModel.selectedIconId.value
                )
            }

            Spacer(Modifier.height(10.dp))
            ExpensesIconsList(
                modifier = modifier,
                expenses = viewModel.expensesList.value,
                showAddIcon = !viewModel.showAddExpense.value,
                onItemClick = { viewModel.onEvent(ExpensesEvent.OnExpenseClick(it)) },
                onLongPressItem = { viewModel.onEvent(ExpensesEvent.OnLongPressExpense(it)) },
                onAddExpensesClick = { viewModel.onEvent(ExpensesEvent.OnAddExpenseClick) },
            )
        }
        if (viewModel.showDeleteDialog.value) {
            DeleteObjectAlertDialog(
                onDismiss = { viewModel.onEvent(ExpensesEvent.OnDismissDeleteDialogClick) },
                onConfirm = { viewModel.onEvent(ExpensesEvent.OnConfirmDeleteDialogClick) }
            )
        }
        if (viewModel.showSelectIconDialog.value) {
            IconSelectorDialog(
                icons = IconUtil.allExpensesIncomeIconsIds,
                onDismissRequest = { viewModel.onEvent(ExpensesEvent.OnDismissSelectedIcon) },
                onIconSelected = { iconId -> viewModel.onEvent(ExpensesEvent.OnSelectNewIcon(iconId)) })
        }
    }
}