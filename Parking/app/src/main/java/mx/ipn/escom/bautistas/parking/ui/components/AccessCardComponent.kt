package mx.ipn.escom.bautistas.parking.ui.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ConnectWithoutContact
import androidx.compose.material.icons.filled.Contactless
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.Motorcycle
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import mx.ipn.escom.bautistas.parking.R
import mx.ipn.escom.bautistas.parking.model.AccessCard
import mx.ipn.escom.bautistas.parking.ui.main.interactions.UEUiState
import mx.ipn.escom.bautistas.parking.ui.main.viewmodels.UEViewModel
import kotlin.math.absoluteValue

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AccessCardComponent(
    modifier: Modifier = Modifier,
    accessCard: AccessCard,
    pagerState: PagerState,
    ueViewModel: UEViewModel,
    ueUiState: UEUiState,
    page: Int,
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Card(
            onClick = { /*TODO*/ },
            shape = RoundedCornerShape(10.dp),
            modifier = modifier
                .graphicsLayer {
                    val pageOffset = (
                            (pagerState.currentPage - page) + pagerState
                                .currentPageOffsetFraction
                            ).absoluteValue
                    alpha = lerp(
                        start = 0.3f,
                        stop = 1f,
                        fraction = 1f - pageOffset.coerceIn(0f, 1f)
                    ).also {
                        scale->
                        scaleX= scale
                        scaleY = scale
                    }
                }
                .height(160.dp)
        ) {
            Box(
                modifier = modifier
            ) {
                BalanceUI(
                    content1 = {
                        Column(
                            verticalArrangement = Arrangement.SpaceAround,
                            modifier = modifier.fillMaxSize()
                        ) {
                            Text(
                                text = "Placa: ${accessCard.vehiculo.placa}",
                                fontSize = 20.sp,
                                style = TextStyle(fontWeight = FontWeight.Bold)
                            )
                            Text(text = "Modelo: ${accessCard.vehiculo.modelo}")
                            Text(text = "Color: ${accessCard.vehiculo.color}")
                        }
                    }, content2 = {
                        if (accessCard.vehiculo.tipoVehiculo.equals(2.toLong())) {
                            Icon(
                                Icons.Filled.DirectionsCar,
                                contentDescription = "",
                                modifier = modifier.fillMaxSize(),
                                tint = colorResource(id = R.color.guinda)
                            )

                        } else {
                            Icon(
                                Icons.Filled.Motorcycle,
                                contentDescription = "",
                                modifier = modifier.fillMaxSize(),
                                tint = colorResource(id = R.color.guinda)
                            )
                        }
                    }
                )
            }
        }
        if (ueUiState.hashCard == accessCard.token) {
            Row(modifier.height(80.dp).padding(10.dp)) {
                Icon(
                    imageVector = Icons.Filled.Contactless,
                    contentDescription = "",
                    tint = colorResource(
                        id = R.color.guinda
                    )
                )
                Text(text = "Acerca al vigilante", color = colorResource(id = R.color.guinda))
            }
        } else {
            ButtonComponent(
                fontSize = 10.sp,
                label = "Seleccionar por defecto",
                modifier = modifier.width(202.dp)
            ) {
                ueViewModel.setCardHashAsDefault(accessCard.token)
            }
        }
    }
}
