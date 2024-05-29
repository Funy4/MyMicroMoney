package com.funy4.mymicromoney.ui.screens.ai

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import co.yml.charts.axis.AxisData
import co.yml.charts.common.model.Point
import co.yml.charts.ui.linechart.LineChart
import co.yml.charts.ui.linechart.model.GridLines
import co.yml.charts.ui.linechart.model.IntersectionPoint
import co.yml.charts.ui.linechart.model.Line
import co.yml.charts.ui.linechart.model.LineChartData
import co.yml.charts.ui.linechart.model.LinePlotData
import co.yml.charts.ui.linechart.model.LineStyle
import co.yml.charts.ui.linechart.model.SelectionHighlightPoint
import co.yml.charts.ui.linechart.model.SelectionHighlightPopUp
import co.yml.charts.ui.linechart.model.ShadowUnderLine
import com.funy4.mymicromoney.R
import kotlinx.coroutines.launch

@Composable
fun AiScreen(modifier: Modifier = Modifier, viewModel: AiViewModel = hiltViewModel()) {
    Image(
        modifier = modifier.fillMaxSize(),
        painter = painterResource(id = R.drawable.background_ai_screen),
        contentDescription = null,
        contentScale = ContentScale.FillBounds
    )
    val context = LocalContext.current
    LaunchedEffect(key1 = true) {
        viewModel.events.collect { event ->
            when (event) {
                AiEvent.ShowServerErrorException -> {
                    Toast.makeText(context, "Ошибка подключения к серверу", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }
    when (val state = viewModel.state.collectAsState().value) {
        AiState.EnterData -> {
            var daysCount by remember { mutableStateOf("30") }
            ConstraintLayout(
                Modifier
                    .fillMaxSize()
                    .padding(start = 16.dp, end = 16.dp)
            ) {
                val (title, daysTitle, daysField, button) = createRefs()
                Text(text = "Прогнозирование вашего бюджета", fontSize = 18.sp, modifier = Modifier
                    .constrainAs(title) {
                        top.linkTo(parent.top, margin = 16.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                )
                Text(
                    text = "Укажите количество дней, на которое хотите спрогнозировать бюджет (максимум 180 дней)",
                    modifier = Modifier
                        .constrainAs(daysTitle) {
                            top.linkTo(title.bottom, margin = 16.dp)
                            start.linkTo(parent.start)
                        }
                )

                OutlinedTextField(
                    value = daysCount,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    onValueChange = {
                        daysCount = if (it.isNotBlank() && it.toInt() > 180) "180" else it
                    },
                    label = { Text("Кол-во дней") },
                    modifier = Modifier
                        .constrainAs(daysField) {
                            top.linkTo(daysTitle.bottom, margin = 12.dp)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }
                )
                Button(
                    onClick = { viewModel.handleAction(AiAction.OnDoPredictClick(daysCount.toInt())) },
                    Modifier
                        .constrainAs(button) {
                            top.linkTo(daysField.bottom, margin = 24.dp)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }
                        .size(180.dp, 40.dp),
                    shape = RoundedCornerShape(24.dp)
                ) {
                    Text(text = "Получить прогноз")
                }
            }

        }

        AiState.Loading -> {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally

            ) {
                CircularProgressIndicator(modifier = Modifier.size(60.dp))
            }

        }

        is AiState.Showdata -> {
            ConstraintLayout {
                val (title, graphic, isPositiveConstr, ready) = createRefs()
                Text(
                    text = "Ваш прогноз показан на графике",
                    fontSize = 18.sp,
                    modifier = Modifier.constrainAs(title) {
                        top.linkTo(parent.top, margin = 16.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    })
                val pointsData = state.predict.predictedTransactions.map {
                    Point(
                        it.date.dayOfYear.toFloat(),
                        it.amount.toFloat()
                    )
                }
                val ySteps = 10
                val pointsSize = pointsData.size
                val xAxisData = AxisData.Builder()
                    .axisStepSize(
                        when {
                            pointsSize <= 8 -> 40.dp
                            pointsSize <= 14 -> 30.dp
                            else -> 20.dp
                        }
                    )
                    .backgroundColor(Color.Transparent)
                    .steps(pointsData.size - 1)
                    .labelData { i -> (i + 1).toString() }
                    .labelAndAxisLinePadding(15.dp)
                    .axisLineColor(MaterialTheme.colors.primary)
                    .build()

                val yAxisData = AxisData.Builder()
                    .steps(ySteps)
                    .backgroundColor(Color.Transparent)
                    .labelAndAxisLinePadding(20.dp)
                    .axisLineColor(MaterialTheme.colors.primary)
                    .labelData { i ->
                        val max = state.predict.predictedTransactions.maxOf { it.amount }
                        val min = state.predict.predictedTransactions.minOf { it.amount }
                        val yScale =
                            (max - min) / ySteps
                        String.format("%.1f", i * yScale.toFloat() + min.toFloat()) + "₽"
                    }.build()
                val lineChartData = LineChartData(
                    linePlotData = LinePlotData(
                        lines = listOf(
                            Line(
                                dataPoints = pointsData,
                                LineStyle(color = MaterialTheme.colors.primary),
                                IntersectionPoint(color = MaterialTheme.colors.primaryVariant),
                                SelectionHighlightPoint(),
                                ShadowUnderLine(),
                                SelectionHighlightPopUp()
                            )
                        ),
                    ),
                    xAxisData = xAxisData,
                    yAxisData = yAxisData,
                    gridLines = GridLines(),
                    backgroundColor = Color(0x22FFFFFF),
                )
                Box(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .fillMaxWidth()
                        .height(400.dp)
                        .constrainAs(graphic) {
                            top.linkTo(title.bottom, margin = 24.dp)
                            start.linkTo(parent.start, 16.dp)
                            end.linkTo(parent.end, 16.dp)
                        },
                ) {
                    LineChart(
                        modifier = Modifier.fillMaxSize(),
                        lineChartData = lineChartData
                    )
                }

                val isPositiveText = if (state.predict.isPositive) {
                    "Ваш прогноз положительный!"
                } else {
                    "Ваш прогноз отрицательный."
                }
                Text(
                    text = isPositiveText,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (state.predict.isPositive) Color(0xFF11FF00) else Color(0xFF990000),
                    modifier = Modifier.constrainAs(isPositiveConstr) {
                        end.linkTo(parent.end)
                        start.linkTo(parent.start)
                        top.linkTo(graphic.bottom, 16.dp)
                    }
                )

                Button(
                    onClick = { viewModel.handleAction(AiAction.OnReadyClick) },
                    modifier = Modifier
                        .size(96.dp, 42.dp)
                        .constrainAs(ready) {
                            top.linkTo(isPositiveConstr.bottom, 16.dp)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }
                ) {
                    Text(text = "Готово", fontSize = 16.sp)
                }
            }
        }
    }
}