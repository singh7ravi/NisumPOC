package com.example.nisumpoc.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.nisumpoc.data.model.User
import com.example.nisumpoc.presentation.screens.DashBoardScreen
import com.example.nisumpoc.presentation.screens.LoginScreen
import com.example.nisumpoc.presentation.screens.UserDetailsScreen


@Composable
fun ScreenNavigation() {

    val navHostController = rememberNavController()

    NavHost(navController = navHostController, startDestination = DashBoard){
        composable(DashBoard){
            DashBoardScreen(navHostController)
        }
        composable("$UserDetails/{userJson}",
            arguments = listOf(navArgument("userJson") { type = NavType.StringType })
            ){
                backStackEntry ->
            val userJson = backStackEntry.arguments?.getString("userJson") ?: ""
            val user = User.fromJson(userJson)
            UserDetailsScreen(user, navHostController)
        }
        composable(Login){
            LoginScreen(navHostController)
        }
    }
}

const val Login = "login_screen"
const val DashBoard = "dashboard_screen"
const val UserDetails = "userDetails_screen"