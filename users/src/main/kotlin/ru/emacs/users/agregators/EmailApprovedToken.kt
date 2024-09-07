package ru.emacs.users.agregators

import java.time.LocalDateTime


internal data class EmailApprovedToken(
    val userId: Long,
    val email: String,
    val phone: String?,
    val isEmailVerified: Boolean,
    val userStatus:EUserStatus,
    val token: String?,
    val createdAt: LocalDateTime?,
    val expired: LocalDateTime?,
)
