package org.example.project.feature.profile.data.repository

import org.example.project.feature.profile.data.models.toDomain
import org.example.project.feature.profile.data.remote.ProfileApi
import org.example.project.feature.profile.domain.models.UserProfile
import org.example.project.feature.profile.domain.repository.IProfileRepository

class ProfileRepositoryImpl(
    private val api: ProfileApi
) : IProfileRepository {
    override suspend fun getUserProfile(): Result<UserProfile> {
        return runCatching {
            api.getProfile().toDomain()
        }
    }
}