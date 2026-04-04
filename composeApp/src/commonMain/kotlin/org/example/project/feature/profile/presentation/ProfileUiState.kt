package org.example.project.feature.profile.presentation

import org.example.project.feature.profile.domain.models.UserProfile

sealed class ProfileUiState {
    object Loading : ProfileUiState()
    data class Success(val profile: UserProfile) : ProfileUiState()
    data class Error(val message: String) : ProfileUiState()
}