package com.example.loginusingcompose.ui.theme.homeui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*

class HomeViewModel : ViewModel() {

    private val viewModelState = MutableStateFlow(HomeViewModelState (isLoading = false))
    val uiState = viewModelState
        .map { it.toUiState() }
        .stateIn(viewModelScope, SharingStarted.Eagerly, viewModelState.value.toUiState())

//    fun sample(){
//        viewModelState.update { it.copy(isLoading = false) }
//    }

    fun onUserNameEntered(name :String){
        viewModelState.update{
            it.copy(
                userName = name

            )
        }
    }

    fun onUserEmailEntered(email : String) {
        viewModelState.update {
            it.copy(
                userEmail = email
            )
        }
    }

    fun onPasswordEntered(password : String) {
       viewModelState.update {
           it.copy(
               password = password
           )
       }
    }

    fun onConfirmPasswordEntered (confirmPassword: String) {
        viewModelState.update {
            it.copy(
                confirmPassword = confirmPassword
            )
        }
    }

    fun onErrorShown() {
        viewModelState.update{
            it.copy(
                errorMessage = null,
            )
        }
    }

    fun validateUserInputs() {
        if(uiState.value.userName?.isEmpty() == true) {
            viewModelState.update{
                it.copy(
                    errorMessage = "Enter your username"
                )
            }
        } else if(uiState.value.userEmail?.isEmpty() == true) {
            viewModelState.update {
                it.copy(
                    errorMessage = "Enter your email"
                )
            }
        } else if(!android.util.Patterns.EMAIL_ADDRESS.matcher(uiState.value.userEmail).matches()) {
            viewModelState.update {
                it.copy(
                    errorMessage = "Enter a proper email format"
                )
            }
        }
        else if(uiState.value.password?.isEmpty() == true){
            viewModelState.update {
                it.copy(
                    errorMessage = "Enter your password"
                )
            }
        } else if(uiState.value.confirmPassword?.isEmpty() == true) {
            viewModelState.update {
                it.copy(
                    errorMessage = "Retype your password"
                )
            }
        } else {
            onErrorShown()
        }
    }
}


data class HomeViewModelState(
    val isLoading: Boolean? = false,
    val userName : String? = "",
    val userEmail : String? = "",
    val password: String? = "",
    val confirmPassword: String? = "",
    val lastUpdate: Long? = System.currentTimeMillis(),
    val errorMessage: String? = "",
) {
    fun toUiState(): HomeViewModelUI {
        return HomeViewModelUI(
            isLoading = isLoading ?: false,
            lastUpdate = lastUpdate,
            userName = userName,
            userEmail = userEmail,
            password = password,
            confirmPassword = confirmPassword,
            errorMessage = errorMessage,
        )
    }
}

data class HomeViewModelUI (
    val isLoading: Boolean?,
    val lastUpdate: Long?,
    val userName : String?,
    val userEmail : String?,
    val password: String?,
    val confirmPassword: String?,
    val errorMessage: String?,
)

