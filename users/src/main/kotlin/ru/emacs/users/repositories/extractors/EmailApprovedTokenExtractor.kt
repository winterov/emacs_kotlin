package ru.emacs.users.repositories.extractors


import org.springframework.dao.DataAccessException
import org.springframework.jdbc.core.ResultSetExtractor
import ru.emacs.users.agregators.EUserStatus
import ru.emacs.users.agregators.EmailApprovedToken
import java.sql.ResultSet
import java.sql.SQLException


internal class EmailApprovedTokenExtractor : ResultSetExtractor<EmailApprovedToken?> {
    @Throws(SQLException::class, DataAccessException::class)
    override fun extractData(rs: ResultSet): EmailApprovedToken? {
        if (!rs.next()) {
            return null
        }
        val id = rs.getLong("userid")
        val isEmailVerified = rs.getBoolean("email_verified")
        val userStatus = EUserStatus.valueOf(rs.getString("user_status"))
        val email = rs.getString("email")
        val phone = rs.getString("phone")
        val token = rs.getString("token")
        val expired = rs.getTimestamp("token_expired")?.toLocalDateTime()
        val createdAt = rs.getTimestamp("token_created")?.toLocalDateTime()
        return EmailApprovedToken(id, email,phone,isEmailVerified, userStatus,token,createdAt,expired)
    }
}
