package mx.ipn.escom.bautistas.parking.ui.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import mx.ipn.escom.bautistas.parking.R
import mx.ipn.escom.bautistas.parking.ui.main.interactions.AdminNavState

@Composable
fun AdminNavRail(
    modifier: Modifier = Modifier,
    adminNavState: AdminNavState,
    changeContent: (AdminNavState) -> Unit
) {
    NavigationRail(
        modifier.width(150.dp),
        header = {
            Text(text = "hola")
        },
        contentColor = colorResource(id = R.color.guinda),
    ) {
        adminNavigationItems.forEach { item ->
            NavigationRailItem(modifier= modifier.size(100.dp),selected = item.adminNavState == adminNavState, onClick = {
                changeContent(item.adminNavState!!)
            }, icon = {
                IconNavItem(
                    icon = item.icon,
                    title = item.title,
                    size = 40.dp,
                    color = R.color.guinda
                )
            })
            Spacer(modifier = Modifier.height(20.dp))
        }
    }

}