package com.funy4.mymicromoney.ui.screens.ai

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
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

@Composable
fun AiScreen(modifier: Modifier = Modifier, viewModel: AiViewModel = hiltViewModel()) {
    Image(
        modifier = modifier.fillMaxSize(),
        painter = painterResource(id = R.drawable.background_ai_screen),
        contentDescription = null,
        contentScale = ContentScale.FillBounds
    )
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
                        top.linkTo(parent.top, margin = 4.dp)
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
                    onClick = { viewModel.handleAction(Action.OnDoPredictClick(daysCount.toInt())) },
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
                val (title, graphic) = createRefs()
                Text(text = "Ваш прогноз показан на графике", Modifier.constrainAs(title) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                })
                val pointsData = state.transactions.map {
                    Point(
                        it.date.dayOfYear.toFloat(),
                        it.amount.toFloat()
                    )
                }
                val ySteps = 10
                val xAxisData = AxisData.Builder()
                    .axisStepSize(100.dp)
                    .backgroundColor(Color.Transparent)
                    .steps(pointsData.size - 1)
                    .labelData { i -> i.toString() }
                    .labelAndAxisLinePadding(15.dp)
                    .build()

                val yAxisData = AxisData.Builder()
                    .steps(ySteps)
                    .backgroundColor(Color.Transparent)
                    .labelAndAxisLinePadding(20.dp)
                    .labelData { i ->
                        val max = state.transactions.maxOf { it.amount }
                        val min = state.transactions.minOf { it.amount }
                        val yScale =
                            (max - min) / ySteps
                        String.format("%.1f", i * yScale.toFloat() + min.toFloat()) + "₽"
                    }.build()
                val lineChartData = LineChartData(
                    linePlotData = LinePlotData(
                        lines = listOf(
                            Line(
                                dataPoints = pointsData,
                                LineStyle(),
                                IntersectionPoint(),
                                SelectionHighlightPoint(),
                                ShadowUnderLine(),
                                SelectionHighlightPopUp()
                            )
                        ),
                    ),
                    xAxisData = xAxisData,
                    yAxisData = yAxisData,
                    gridLines = GridLines(),
                    backgroundColor = Color.White
                )
                LineChart(
                    modifier = Modifier
                        .fillMaxSize()
                        .height(300.dp)
                        .constrainAs(graphic) {
                            top.linkTo(title.bottom, margin = 24.dp)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        },
                    lineChartData = lineChartData
                )
            }
        }
    }
}