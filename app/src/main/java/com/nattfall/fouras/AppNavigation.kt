package com.nattfall.fouras

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.nattfall.fouras.authentication.AuthenticationScreen
import com.nattfall.fouras.checkout.CheckoutScreen
import com.nattfall.fouras.home.HomeScreen
import com.nattfall.fouras.productdetail.ProductDetailScreen
import com.nattfall.fouras.settings.SettingsScreen

@Composable
fun AppNavigation(
    modifier: Modifier = Modifier,
    startDestination: AppDestination = AppDestination.Authentication,
) {
    val navController: NavHostController = rememberNavController()

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination,
    ) {
        composable<AppDestination.Authentication> {
            AuthenticationScreen(
                navigateToHome = {
                    navController.navigate(AppDestination.Home) {
                        popUpTo(AppDestination.Authentication) {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                }
            )
        }

        composable<AppDestination.Home> {
            HomeScreen(
                navigateToProductDetail = { id ->
                    navController.navigate(AppDestination.ProductDetail(id = id))
                },
                navigateToSettings = {
                    navController.navigate(AppDestination.Settings)
                },
                navigateToCheckout = {
                    navController.navigate(AppDestination.Checkout)
                }
            )
        }

        composable<AppDestination.ProductDetail> { destination ->
            val productId = destination
                .toRoute<AppDestination.ProductDetail>()
                .id

            ProductDetailScreen(
                productId = productId,
                onBack = {
                    navController.popBackStack()
                },
                navigateToCheckout = {
                    navController.navigate(AppDestination.Checkout)
                }
            )
        }

        composable<AppDestination.Checkout> {
            CheckoutScreen(
                navigateUp = {
                    navController.popBackStack()
                },
                navigateToCheckoutFinalized = {
                    navController.navigate(AppDestination.Home)
                },
            )
        }

        composable<AppDestination.Settings> {
            SettingsScreen(
                onBack = {
                    navController.popBackStack()
                },
                onLogout = {
                    navController.navigate(AppDestination.Authentication)
                },
            )
        }
    }

}