package mx.ipn.escom.bautistas.parking.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import mx.ipn.escom.bautistas.parking.R

@Composable
fun BalanceUI(
    modifier: Modifier = Modifier,
    content1: @Composable ()->Unit,
    content2: @Composable ()->Unit
) {
    Row(modifier.fillMaxSize()) {
        Box(
            modifier
                .background(color = colorResource(id = R.color.grey_figma))
                .fillMaxHeight()
                .weight(1f).padding(20.dp),
            contentAlignment = Alignment.Center
        ) {
           content1()
        }
        Box(
            modifier
                .background(color = colorResource(id = R.color.grey_figma))
                .fillMaxHeight()
                .weight(1f).padding(20.dp),
            contentAlignment = Alignment.Center
        ) {
            content2()
        }
    }
}