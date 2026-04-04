package org.example.project.feature.profile.domain.usecases

import org.example.project.feature.profile.domain.models.UserProfile
import org.example.project.feature.profile.domain.repository.IProfileRepository

class GetUserProfileUseCase(private val repository: IProfileRepository) {
    suspend operator fun invoke(): Result<UserProfile> = repository.getUserProfile()
}