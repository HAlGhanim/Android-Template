package com.example.androidtemplate.navigation

sealed class Screen(val route: String){
    // Authentication
    data object Login : Screen("login")
    data object Register : Screen("register")

    //
    data object Home : Screen("home")

    // Admin
    data object AdminDashboard : Screen("admin_dashboard")
}
