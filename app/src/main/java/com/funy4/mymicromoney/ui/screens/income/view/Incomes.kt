package com.funy4.mymicromoney.ui.screens.income.view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.funy4.domain.model.IncomeModel
import com.funy4.mymicromoney.ui.screens.expensescreen.views.CategoryItem
import com.funy4.mymicromoney.ui.screens.expensescreen.views.CategoryItemNew


@Composable
fun IncomeIconsList(
    modifier: Modifier = Modifier,
    incomes: List<IncomeModel>,
    showAddIcon: Boolean = true,
    onItemClick: (IncomeModel) -> Unit,
    onLongPressItem: (IncomeModel) -> Unit,
    onAddExpensesClick: () -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(100.dp),
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 20.dp)
    ) {
        items(incomes) { income ->
            CategoryItem(
                modifier = Modifier.padding(10.dp),
                title = income.name,
                color = Color(income.color),
                imageVector = Icons.Default.Face,
                cost = income.money,
                onItemClick = { onItemClick(income) },
                onLongPressItem = {onLongPressItem(income)}
            )
        }

        item {
            AnimatedVisibility(
                visible = showAddIcon,
                enter = fadeIn(
                    // Fade in with the initial alpha of 0.3f.
                    initialAlpha = 0.3f
                ),
                exit = fadeOut()
            ) {
                CategoryItemNew(
                    modifier = Modifier.padding(10.dp),
                    onAddExpenseClick = { onAddExpensesClick() }
                )
            }
        }
    }
}


@Composable
@Preview
fun IconPreview() {
    CategoryItem(
        title = "food",
        color = Color.Black,
        imageVector = Icons.Default.Favorite,
        cost = 100.0,
        onItemClick = {}
    ) {
    }
}