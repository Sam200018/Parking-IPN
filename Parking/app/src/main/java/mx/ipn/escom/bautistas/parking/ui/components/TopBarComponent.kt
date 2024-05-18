package mx.ipn.escom.bautistas.parking.ui.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import mx.ipn.escom.bautistas.parking.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarComponent(
    modifier: Modifier = Modifier,
    title: String,
    actions: @Composable() (RowScope.() -> Unit) = {},
    backAction: (() -> Unit)? = null
) {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = colorResource(id = R.color.guinda),
            titleContentColor = colorResource(id = R.color.white),
        ),
        navigationIcon = {
            if (backAction != null)
                IconButton(onClick = backAction) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBackIosNew,
                        contentDescription = "",
                        tint = colorResource(
                            id = R.color.white
                        )
                    )
                }
        },
        title = { Text(text = title) },
        actions = actions,
    )
}

@Preview
@Composable
private fun TopBarPrev() {
    TopBarComponent(title = "Hola")
}