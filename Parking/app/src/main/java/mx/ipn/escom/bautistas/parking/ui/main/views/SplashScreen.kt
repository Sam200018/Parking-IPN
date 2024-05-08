package mx.ipn.escom.bautistas.parking.ui.main.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import mx.ipn.escom.bautistas.parking.R

@Composable
fun SplashScreen(modifier: Modifier = Modifier) {
    Scaffold {
        Box(
            modifier
                .padding(it)
                .fillMaxSize(), contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Image(
                    modifier = modifier.size(180.dp),
                    painter = painterResource(id = R.drawable.logo_parking),
                    contentDescription = ""
                )
                Text(
                    text = stringResource(id = R.string.system_label),
                    fontSize = 24.sp,
                    color = colorResource(
                        id = R.color.guinda
                    )
                )
            }

        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun SplashPrv() {
    SplashScreen()
}