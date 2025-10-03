package com.example.mypokemonapplication.feature.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.mypokemonapplication.data.models.UISkeletonData


@Composable
fun UISkeleton(data: UISkeletonData, displayFunction: @Composable () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 48.dp)
    ) {

        when {
            data.isLoading -> {
                CircularProgressIndicator(modifier = Modifier.align(alignment = Alignment.Center))
            }

            data.isError -> {
                Row(modifier = Modifier.align(Alignment.Center)) {
                    Text("An error occurred while fetching the pokemon data. Please try again!")
                }

            }

            data.isDataEmpty == true -> Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("There is no data to display!", fontWeight = FontWeight.Bold)
            }

            else -> displayFunction()

        }
    }
}