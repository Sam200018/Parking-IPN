package mx.ipn.escom.bautistas.parking.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Input
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import mx.ipn.escom.bautistas.parking.R

@Composable
fun RecordCardComponent(modifier: Modifier = Modifier) {
    Card(onClick = {

    }) {
        Row(modifier = modifier
            .fillMaxWidth()
            .padding(10.dp)) {
            Box(modifier.size(100.dp, 100.dp), contentAlignment = Alignment.Center) {
                Icon(
                    imageVector = Icons.AutoMirrored.Outlined.Input,
                    contentDescription = "",
                    modifier = modifier.size(80.dp),
                    tint = colorResource(id = R.color.guinda)
                )
            }
            Column(
                modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .padding(10.dp), verticalArrangement = Arrangement.SpaceAround
            ) {
                Text(text = "Entrada")
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun RecordCardPrev() {
    RecordCardComponent()
}