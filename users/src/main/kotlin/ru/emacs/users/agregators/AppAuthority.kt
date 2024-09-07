package ru.emacs.users.agregators

import org.springframework.security.core.GrantedAuthority
import java.time.LocalDateTime


internal class AppAuthority : GrantedAuthority {
    var id: Long? = null
    var title: String? = null
    var description: String? = null
    var authority: ru.emacs.users.agregators.EAuthorities? = null
    var createdAt: LocalDateTime? = null
    var updatedAt: LocalDateTime? = null

    override fun getAuthority(): String {
        return authority.toString()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ru.emacs.users.agregators.AppAuthority

        return id == other.id
    }

    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }

}
