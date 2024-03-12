package com.example.firstcomposeapp.ui.navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.firstcomposeapp.ui.routes.Screen

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.MainScreen.route) {
        composable(route = Screen.MainScreen.route) {
            MainScreen(navController)
        }
        composable(route = Screen.DetailsScreen.route  + "/{name}", arguments = listOf(navArgument("name") {
            type = NavType.StringType
            defaultValue = "Ashif"
            nullable = true
        })) {entry->
            DetailScreen(name = entry.arguments?.getString("name"))
        }
    }
}

@Composable
fun DetailScreen(name: String?) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Hello $name")
    }
}

@Composable
fun MainScreen(navController: NavHostController) {
    var name by remember {
        mutableStateOf("")
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp), value = name, onValueChange = {
            name = it
        })
        Spacer(modifier = Modifier.height(10.dp))
        Button(onClick = {
            navController.navigate(Screen.DetailsScreen.withArgs(name))
        }) {
            Text(text = "Detail Screen")
        }
    }
}
