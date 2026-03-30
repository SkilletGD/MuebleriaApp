package org.example.project.feature.auth.ui


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.example.project.core.network.utils.NetworkResult
import org.example.project.feature.auth.domain.repository.AuthRepository

class LoginViewModel(private val repository: AuthRepository) : ViewModel() {

    // Estado inicial: null o un estado "Idle"
    private val _loginState = MutableStateFlow<NetworkResult<Any>?>(null)
    val loginState: StateFlow<NetworkResult<Any>?> = _loginState

    fun login(email: String, pass: String) {
        viewModelScope.launch {
            _loginState.value = NetworkResult.Loading
            val result = repository.login(email, pass)
            _loginState.value = result
        }
    }

    fun resetState() {
        _loginState.value = null
    }
}