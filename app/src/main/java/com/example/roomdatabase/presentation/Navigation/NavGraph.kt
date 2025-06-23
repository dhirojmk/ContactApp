package com.example.roomdatabase.presentation.Navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.roomdatabase.presentation.Model.ContactViewModel
import com.example.roomdatabase.presentation.screens.AddEditScreen
import com.example.roomdatabase.presentation.screens.HomeScreen

@Composable
fun NavGraph(navHostController: NavHostController, viewModel: ContactViewModel, modifier: Modifier) {
    val state by viewModel.state.collectAsState()
    NavHost(navController = navHostController, startDestination = Routs.home.Route) {
        composable(Routs.AddEdit.Route) {
            AddEditScreen(
                navHostController = navHostController,
                state = viewModel.state.collectAsState().value,
                onEvent = {
                    viewModel.saveContact()
                })

        }
        composable(Routs.home.Route) {
            HomeScreen( navHostController = navHostController,
                state = state,
                viewModel = viewModel
                )

        }
    }
}