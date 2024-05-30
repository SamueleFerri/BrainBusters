package com.example.brainbusters.ui.components

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import co.yml.charts.common.model.PlotType
import co.yml.charts.ui.piechart.charts.PieChart
import co.yml.charts.ui.piechart.models.PieChartConfig
import co.yml.charts.ui.piechart.models.PieChartData

@SuppressLint("SuspiciousIndentation")
@Composable
fun PieChartScreen() {
    val pieChartData = PieChartData(
        slices = listOf(
            PieChartData.Slice(
                label = "storia",
                value = 50f,
                color = Color.Red
            ),
            PieChartData.Slice(
                label = "informatica",
                value = 30f,
                color = Color.Blue
            ),
            PieChartData.Slice(
                label = "matematica",
                value = 30f,
                color = Color.Yellow
            )
        ),
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
                .padding(16.dp), // Regola il padding a tuo piacimento
            pieChartData = pieChartData,
            pieChartConfig = pieChartConfig
        )
    }

}