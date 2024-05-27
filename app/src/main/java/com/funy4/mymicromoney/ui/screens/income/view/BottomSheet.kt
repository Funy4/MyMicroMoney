package com.funy4.mymicromoney.ui.screens.income.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.funy4.domain.model.IncomeModel
import com.funy4.domain.model.IncomeWithTransactionsModel
import com.funy4.domain.model.TransactionModel
import com.funy4.domain.model.type.TransactionType
import com.funy4.mymicromoney.R
import com.funy4.mymicromoney.ui.theme.MyMicroMoneyTheme
import java.util.*


@Composable
fun IncomeTransactionsBottomSheet(transactions: List<IncomeWithTransactionsModel>) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column {
            Box(
                modifier = Modifier
                    .height(50.dp)
                    .fillMaxWidth(), contentAlignment = Alignment.Center
            ) {
                Text(text = "Транзакции", fontSize = 24.sp, fontWeight = FontWeight.Bold)
            }
            Spacer(Modifier.height(16.dp))
            LazyColumn(contentPadding = PaddingValues(horizontal = 16.dp)) {
                items(transactions) { expenseWithTransactions ->
                    Text(
                        text = expenseWithTransactions.income.name,
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                    )
                    Spacer(Modifier.height(5.dp))
                    for (transaction in expenseWithTransactions.transactions) {
                        Spacer(Modifier.height(16.dp))
                        TransactionItem(
                            incomeModel = expenseWithTransactions.income,
                            transactionModel = transaction
                        )
                    }
                }
            }
        }

    }
}

@Composable
fun TransactionItem(
    modifier: Modifier = Modifier,
    incomeModel: IncomeModel,
    transactionModel: TransactionModel,
    backgroundColor: Color = MaterialTheme.colors.secondary
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(backgroundColor),
    ) {
        Row(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier.weight(2f),
                painter = painterResource(id = incomeModel.iconId),
                tint = Color(incomeModel.color),
                contentDescription = ""
            )
            Column(
                modifier = Modifier
                    .weight(6f)
                    .padding(end = 16.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = incomeModel.name,
                    fontSize = 22.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
                Spacer(Modifier.height(5.dp))
                Text(text = transactionModel.name, color = Color.Gray, fontStyle = FontStyle.Italic)
            }
            Text(
                modifier = Modifier.padding(end = 16.dp),
                text = "${transactionModel.cost}$",
                color = Color.White
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewTransactionItem() {
    MyMicroMoneyTheme() {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            TransactionItem(
                incomeModel = IncomeModel(
                    id = UUID.randomUUID(),
                    name = "Food",
                    color = Color.Red.toArgb(),
                    iconId = R.drawable.ic_8,
                    money = 523.0
                ),
                transactionModel = TransactionModel(
                    id = UUID.randomUUID(),
                    name = "покупка",
                    cost = 5235.0,
                    cashId = UUID.randomUUID(),
                    transactionType = TransactionType.EXPENSE,
                    date = "2022-06-01"
                )
            )
        }
    }
}