package mx.ipn.escom.bautistas.parking.ui.reader.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import mx.ipn.escom.bautistas.parking.model.Cuenta
import mx.ipn.escom.bautistas.parking.model.Persona
import mx.ipn.escom.bautistas.parking.ui.components.BalanceUI
import mx.ipn.escom.bautistas.parking.ui.components.BitmapImage

@Composable
fun ScannedPersonaComponent(
    modifier: Modifier = Modifier,
    persona: Persona,
    cuenta: Cuenta? = null,
) {
    Card(
        shape = RoundedCornerShape(10.dp),
        modifier = modifier,
        colors = CardColors(
            containerColor = Color.Transparent,
            contentColor = Color.Black,
            disabledContentColor = Color.White,
            disabledContainerColor = Color.Red
        )
    ) {
        Box(modifier = modifier) {
            Column(
                modifier
                    .fillMaxSize()
                    .padding(20.dp),
                verticalArrangement = Arrangement.SpaceAround
            ) {
                BalanceUI(modifier = modifier.height(205.dp),
                    content1 = {
                        BitmapImage(
                            filename = persona.rutaFotografia
                        )
                    },
                    content2 = {
                        Column(
                            modifier
                                .fillMaxSize()
                                .padding(5.dp),
                            verticalArrangement = Arrangement.SpaceAround
                        ) {
                            Text(
                                text = persona.nombre,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(text = persona.aPaterno, fontSize = 20.sp)
                            Text(text = persona.aMaterno, fontSize = 20.sp)
                            Text(text = persona.numeroContacto, fontSize = 20.sp)
                        }
                    })
                if (persona.idIpn != null)
                    Text(
                        text = "IPN ID: ${persona.idIpn}",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                if (cuenta?.idProgAcademico != null)
                    Text(
                        text = "Prog. Académico: ${cuenta.progAcademico.nombreProgAcademico}",
                        fontSize = 20.sp
                    )
            }
        }
    }
}
