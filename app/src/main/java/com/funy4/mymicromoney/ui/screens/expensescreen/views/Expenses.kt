package com.funy4.mymicromoney.ui.screens.expensescreen.views

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.funy4.domain.model.ExpenseModel
import kotlin.math.roundToInt


@Composable
fun ExpensesIconsList(
    modifier: Modifier = Modifier,
    expenses: List<ExpenseModel>,
    showAddIcon: Boolean = true,
    onItemClick: (ExpenseModel) -> Unit,
    onLongPressItem: (ExpenseModel) -> Unit,
    onAddExpensesClick: () -> Unit,
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(100.dp),
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 20.dp)
    ) {
        items(expenses) { expense ->
            CategoryItem(
                modifier = Modifier.padding(10.dp),
                title = expense.name,
                color = Color(expense.color),
                imageVector = Icons.Default.Face,
                cost = expense.money,
                onItemClick = { onItemClick(expense) },
                onLongPressItem = { onLongPressItem(expense) }
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

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CategoryItem(
    modifier: Modifier = Modifier,
    title: String,
    color: Color,
    imageVector: ImageVector,
    cost: Double,
    onItemClick: () -> Unit,
    onLongPressItem: () -> Unit
) {
    val money = (cost * 100).roundToInt() / 100.0
    Box(
        modifier
            .clip(RoundedCornerShape(20))
            .background(Color.Black.copy(alpha = 0.03f))
            .pointerInput(Unit) {
                detectTapGestures(

                )
            }
            .combinedClickable(onClick = { onItemClick() }, onLongClick = { onLongPressItem() }),
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = title,
                modifier = Modifier
                    .fillMaxWidth(),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold
            )
            Spacer(Modifier.height(6.dp))
            Card(
                modifier = Modifier
                    .size(60.dp)
                    .clip(RoundedCornerShape(30))
                    .align(Alignment.CenterHorizontally),

                backgroundColor = color

            ) {
                Icon(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(vertical = 2.dp),
                    imageVector = imageVector,
                    contentDescription = null,
                    tint = Color.White
                )
            }

            Text(
                text = "$money ₽",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                fontStyle = FontStyle.Italic,
                fontWeight = FontWeight.Bold,
                color = color,
            )
        }
    }
}

@Composable
fun CategoryItemNew(
    modifier: Modifier = Modifier,
    size: Dp = 100.dp,
    onAddExpenseClick: () -> Unit
) {
    Box(
        modifier
            .height(size)
            .width(size)
            .padding(15.dp)
            .clip(RoundedCornerShape(20))
            .background(Color.Gray.copy(alpha = 0.5f))
            .clickable { onAddExpenseClick() },
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = "Add expenses",
            modifier = Modifier
                .clip(RoundedCornerShape(20))
                .fillMaxSize(),
        )
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