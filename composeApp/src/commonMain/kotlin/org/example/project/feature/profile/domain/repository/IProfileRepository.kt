package org.example.project.feature.profile.domain.repository

import org.example.project.feature.profile.domain.models.UserProfile

interface IProfileRepository {
    suspend fun getUserProfile(): Result<UserProfile>
}