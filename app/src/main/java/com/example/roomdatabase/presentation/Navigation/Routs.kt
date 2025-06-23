package com.example.roomdatabase.presentation.Navigation

sealed class Routs(var Route: String) {

    object AddEdit : Routs("add_edit_screen")
    object home : Routs("home_screen")
}