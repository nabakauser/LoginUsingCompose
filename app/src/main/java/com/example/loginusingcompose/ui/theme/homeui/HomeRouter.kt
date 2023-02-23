package com.example.loginusingcompose.ui.theme.homeui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue


@Composable
fun HomeViewModelRouter(
    homeViewModel: HomeViewModel
) {
    val uiState by homeViewModel.uiState.collectAsState()
    LoginScreen(
    userName = uiState.userName,
    userEmail = uiState.userEmail,
    password = uiState.password,
    confirmPassword = uiState.confirmPassword,
    onUserNameEntered = {homeViewModel.onUserNameEntered(it)},
    onUserEmailEntered = {homeViewModel.onUserEmailEntered(it)},
    onPasswordEntered = {homeViewModel.onPasswordEntered(it)},
    onConfirmPasswordEntered = {homeViewModel.onConfirmPasswordEntered(it)},
    errorMessage = uiState.errorMessage,
    onErrorShown = { homeViewModel.onErrorShown()},
    onSubmit = {homeViewModel.validateUserInputs()})
}