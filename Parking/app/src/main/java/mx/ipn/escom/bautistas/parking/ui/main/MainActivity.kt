package mx.ipn.escom.bautistas.parking.ui.main

import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import dagger.hilt.android.AndroidEntryPoint
import mx.ipn.escom.bautistas.parking.ui.main.interactions.AuthStatus
import mx.ipn.escom.bautistas.parking.ui.main.viewmodels.AuthViewModel
import mx.ipn.escom.bautistas.parking.ui.main.views.GenAccessCardScreen
import mx.ipn.escom.bautistas.parking.ui.main.views.HomeScreen
import mx.ipn.escom.bautistas.parking.ui.main.views.LoginScreen
import mx.ipn.escom.bautistas.parking.ui.main.views.ManualRegistrationScreen
import mx.ipn.escom.bautistas.parking.ui.main.views.NewAccountUserScreen
import mx.ipn.escom.bautistas.parking.ui.main.views.NewIncidentScreen
import mx.ipn.escom.bautistas.parking.ui.main.views.NewUserScreen
import mx.ipn.escom.bautistas.parking.ui.main.views.NewVehicleScreen
import mx.ipn.escom.bautistas.parking.ui.main.views.SplashScreen
import mx.ipn.escom.bautistas.parking.ui.theme.ParkingTheme

@ExperimentalMaterial3WindowSizeClassApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        val packageManager = this.packageManager
        val hasNFC = packageManager.hasSystemFeature(PackageManager.FEATURE_NFC)


        setContent {
            val windowSizeClass = calculateWindowSizeClass(this)
            val authViewModel: AuthViewModel by viewModels()
            val authState by authViewModel.authState.collectAsStateWithLifecycle()


            ParkingTheme {
                val navController = rememberNavController()


                val startDestination = when (authState.authStatus) {
                    AuthStatus.Unauthenticated -> Routes.LoginScreen.route
                    AuthStatus.Authenticated -> Routes.HomeScreen.route
                    else -> Routes.SplashScreen.route
                }


                NavHost(
                    navController = navController,
                    startDestination = startDestination
                ) {
                    composable(Routes.SplashScreen.route) {
                        SplashScreen()
                    }
                    composable(Routes.LoginScreen.route) {
                        LoginScreen(
                            windowSizeClass = windowSizeClass,
                            authViewModel = authViewModel,
                            authState = authState,
                        )
                    }
                    composable(Routes.HomeScreen.route) {
                        HomeScreen(
                            windowSizeClass = windowSizeClass,
                            authState = authState,
                            logout = {
                                authViewModel.logout()
                            },
                            hasNFC = hasNFC,
                            navSelectUser = {
                                navController.navigate(Routes.GenerateAccessCard.route)
                            },
                            navToManualRegistration = {
                                navController.navigate(Routes.ManualRegistration.route)
                            },
                            navToNewIncident = {
                                navController.navigate(Routes.NewIncident.route)
                            },
                            navToIncidentDetail = {
                                navController.navigate(Routes.IncidentDetail.route)
                            },
                            navToCreateAccount = {
                                navController.navigate(Routes.NewAccountUser.route)
                            }
                        )
                    }

                    composable(Routes.GenerateAccessCard.route) {
                        GenAccessCardScreen(mainActivity = this@MainActivity,
                            navToNewUser = { navController.navigate(Routes.NewAccountUser.route) },
                            navToNewVehicle = { navController.navigate(Routes.NewVehicle.route) },
                        ) {
                            navController.popBackStack()
                        }
                    }
                    composable(Routes.NewAccountUser.route) {
                        NewAccountUserScreen(
                            mainActivity = this@MainActivity,
                        ) {
                            navController.popBackStack()
                        }
                    }
                    composable(Routes.NewVehicle.route) {
                        NewVehicleScreen(
                            windowSizeClass = windowSizeClass
                        ) {
                            navController.popBackStack()
                        }
                    }
                    composable(Routes.NewUser.route) {
                        NewUserScreen() {
                            navController.popBackStack()
                        }
                    }
                    composable(Routes.ManualRegistration.route) {
                        ManualRegistrationScreen(
                            navToNewUser = { navController.navigate(Routes.NewUser.route) },
                            navToNewVehicle = { navController.navigate(Routes.NewVehicle.route) }
                        ) {
                            navController.popBackStack()
                        }
                    }
                    composable(Routes.NewIncident.route) {
                        NewIncidentScreen()
                    }
                    composable(
                        "${Routes.CompleteUserInfo.route}/{id_incident}",
                        listOf(navArgument("id_incident") { type = NavType.LongType })
                    ) { backStackEntry ->
                        NewIncidentScreen()
                    }
                }
            }
        }
    }
}

