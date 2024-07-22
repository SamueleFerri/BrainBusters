package com.example.brainbusters.ui.components

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import co.yml.charts.common.model.PlotType
import co.yml.charts.ui.piechart.charts.PieChart
import co.yml.charts.ui.piechart.models.PieChartConfig
import co.yml.charts.ui.piechart.models.PieChartData
import com.example.brainbusters.ui.viewModels.QuizDoneViewModel

@SuppressLint("SuspiciousIndentation")
@Composable
fun PieChartScreen(userId: Int, viewModel: QuizDoneViewModel) {
    val numQuizDoneByCategory by viewModel.numQuizDoneByCategory.collectAsState()

    LaunchedEffect(userId) {
        viewModel.loadNumQuizDoneByCategory(userId)
    }

    val pieChartData = PieChartData(
        slices = if (numQuizDoneByCategory.isNotEmpty()) {
            // Controlla se tutti i valori sono zero
            val allZero = numQuizDoneByCategory.all { (_, count) -> count == 0 }

            if (allZero) {
                listOf(
                    PieChartData.Slice(
                        label = "No Data",
                        value = 1f,
                        color = Color.Gray
                    )
                )
            } else {
                numQuizDoneByCategory.map { (category, count) ->
                    PieChartData.Slice(
                        label = category,
                        value = count.toFloat(),
                        color = when (category) {
                            "Geography" -> Color.Green
                            "Science" -> Color.Red
                            "Math" -> Color.Blue
                            else -> Color.Cyan
                        }
                    )
                }
            }
        } else {
            listOf(
                PieChartData.Slice(
                    label = "No Data",
                    value = 1f,
                    color = Color.Gray
                )
            )
        },
        plotType = PlotType.Pie
    )

    val pieChartConfig = PieChartConfig(
        isAnimationEnable = true,
        showSliceLabels = true,
        activeSliceAlpha = 0.5f,
        animationDuration = 1600,
        backgroundColor = Color.Transparent
    )

    Box(
        modifier = Modifier
            .width(350.dp)
            .height(350.dp)
            .background(Color.Transparent)
    ) {
        PieChart(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.Transparent)
                .padding(16.dp),
            pieChartData = pieChartData,
            pieChartConfig = pieChartConfig
        )
    }
}