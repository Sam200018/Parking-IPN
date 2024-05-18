package mx.ipn.escom.bautistas.parking.ui.main.views

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Contactless
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import mx.ipn.escom.bautistas.parking.R
import mx.ipn.escom.bautistas.parking.ui.components.IconNavItem
import mx.ipn.escom.bautistas.parking.ui.components.TopBarComponent

@Composable
fun ScannerScreen(
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            TopBarComponent(title = stringResource(id = R.string.top_title))
        }
    ) {
        Box(modifier = modifier.padding(it).fillMaxSize()) {
            Column(modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
                Box(
                    modifier
                        .fillMaxSize()
                        .weight(1f)
                        .padding(20.dp),
                    contentAlignment = Alignment.Center
                ) {
                    IconNavItem(
                        icon = Icons.Filled.Contactless,
                        title = R.string.nfc_scanner_label,
                        color = R.color.guinda,
                        size = 180.dp
                    )
                }
                Box(
                    modifier
                        .fillMaxSize()
                        .weight(1f)
                        .padding(20.dp),
                    contentAlignment = Alignment.Center
                ) {

                }
            }
        }
    }
}

@Preview
@Composable
private fun ScannerPrev() {
    ScannerScreen()
}