package ru.emacs.users.services.impl


import jakarta.validation.Validator
import org.hibernate.validator.internal.constraintvalidators.hv.EmailValidator
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.MessageSource
import org.springframework.context.event.ApplicationEventMulticaster
import org.springframework.context.i18n.LocaleContextHolder
import org.springframework.http.HttpStatus
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.emacs.dtos.AppResponseErrorDto
import ru.emacs.events.users.UserAccountWithToken
import ru.emacs.events.users.UserEmailApprovedTokenSend
import ru.emacs.properties.services.SecurityPropertiesService
import ru.emacs.users.agregators.EUserStatus
import ru.emacs.users.agregators.UserAccount
import ru.emacs.users.dtos.email.ApprovedEmailRequestDto
import ru.emacs.users.repositories.UserEmailRepository
import ru.emacs.users.services.UserEmailService
import ru.emacs.users.utils.ApprovedTokenUtils
import ru.emacs.validators.validateDto
import java.time.LocalDateTime


@Service
internal class UserEmailServiceImpl @Autowired constructor(
    private val userEmailRepository: UserEmailRepository,
    private val userDetailsService: UserDetailsService,
    private val securityPropertiesService: SecurityPropertiesService,
    private val validator: Validator,
    private val messageSource: MessageSource,
    private val eventMulticaster: ApplicationEventMulticaster
) : UserEmailService {
    private val log = LoggerFactory.getLogger(UserEmailServiceImpl::class.java)
    @Transactional
    override fun generateVerifiedEmailTokenAndSend(email: String?): Pair<Any?, HttpStatus> {
        if(email==null) return Pair(null, HttpStatus.BAD_REQUEST)
       /* val userAccount = userDetailsService.loadUserByUsername(email) as UserAccount?*/
        val emailApprovedToken = userEmailRepository.getVerifiedToken(email)
        if (emailApprovedToken == null) {
            val notValidMessage:String =  messageSource.getMessage(
                "auth.notValidParams",null,
                LocaleContextHolder.getLocale())
            val errorDto = AppResponseErrorDto(HttpStatus.BAD_REQUEST,notValidMessage )
            return Pair(errorDto,HttpStatus.BAD_REQUEST)
        }
        if (emailApprovedToken.userStatus != EUserStatus.NEW_USER
            && emailApprovedToken.userStatus != EUserStatus.ACTIVE
        ) {
            val notValidMessage:String =  messageSource.getMessage(
                "auth.accountBlocked",null,
                LocaleContextHolder.getLocale())
            val errorDto = AppResponseErrorDto(HttpStatus.BAD_REQUEST,notValidMessage )
            return Pair(errorDto,HttpStatus.BAD_REQUEST)
        }

        val securityProperties = securityPropertiesService.getSecurityProperty()
        if (emailApprovedToken.expired!=null&&emailApprovedToken.expired.isBefore(LocalDateTime.now())) {
            return Pair(null,HttpStatus.OK)
        }
        val token: String =
            ApprovedTokenUtils.generateApprovedToken(securityProperties.approvedEmailProperty.approvedEmailTokenLength)
        val expired = LocalDateTime.now().plus(
            securityProperties.approvedEmailProperty.approvedEmailTokenLifetime,
            securityProperties.approvedEmailProperty.unit
        )
        userEmailRepository.saveVerifiedEmailToken(emailApprovedToken.userId, token, expired)
        log.debug("создали токен")
        eventMulticaster.multicastEvent(UserEmailApprovedTokenSend(UserAccountWithToken(emailApprovedToken.email,emailApprovedToken.phone,null,null,token)))
        return Pair(null,HttpStatus.OK)
    }

    @Transactional
    override fun approvedUserEmail(dto: ApprovedEmailRequestDto): Pair<Any?, HttpStatus> {
        val errors = validateDto(validator,dto)
        if (errors.isNotEmpty()) {
            val errorDto = AppResponseErrorDto(HttpStatus.BAD_REQUEST, errors)
            return Pair(errorDto,HttpStatus.BAD_REQUEST)
        }
        val emailApprovedToken = userEmailRepository.getVerifiedToken(dto.email!!)
        if (emailApprovedToken == null||
            !emailApprovedToken.token.equals(dto.token)
            ) {
            val notValidMessage:String =  messageSource.getMessage(
                "validation.token",null,
                LocaleContextHolder.getLocale())
            val errorDto = AppResponseErrorDto(HttpStatus.BAD_REQUEST,notValidMessage )
            return Pair(errorDto,HttpStatus.BAD_REQUEST)
        }
        val userAccount = userDetailsService.loadUserByUsername(dto.email) as UserAccount?
        if (userAccount != null) {
            if (!(userAccount.status == EUserStatus.NEW_USER
                        || userAccount.status == EUserStatus.ACTIVE)
            ) {
                val notValidMessage:String =  messageSource.getMessage(
                    "{auth.accountBlocked}",null,
                    LocaleContextHolder.getLocale())
                val errorDto = AppResponseErrorDto(HttpStatus.BAD_REQUEST,notValidMessage )
                return Pair(errorDto,HttpStatus.BAD_REQUEST)
            }
        }else{
            val notValidMessage:String =  messageSource.getMessage(
                "validation.token",null,
                LocaleContextHolder.getLocale())
            val errorDto = AppResponseErrorDto(HttpStatus.BAD_REQUEST,notValidMessage )
            return Pair(errorDto,HttpStatus.BAD_REQUEST)
        }
        userEmailRepository.updateUserEmailStatusByUserId(emailApprovedToken.userId, true)
        userEmailRepository.deleteUsedEmailApprovedTokenByUserId(emailApprovedToken.userId)
        return Pair(null,HttpStatus.OK)
    }

    @Transactional
    override fun emailBusyCheck(email: String): Pair<Any?, HttpStatus> {
        val emailValidator = EmailValidator()
        if (!emailValidator.isValid(email, null)) {
            val notValidMessage:String =  messageSource.getMessage(
                "validation.email.NotValid",null,
                LocaleContextHolder.getLocale())
            val errorDto = AppResponseErrorDto(HttpStatus.BAD_REQUEST, notValidMessage)
            return Pair(errorDto, HttpStatus.BAD_REQUEST)
        }
        val statuses = listOf(EUserStatus.NEW_USER,EUserStatus.ACTIVE)
        if(userEmailRepository.countOfUsageEmail(email,statuses) > 0){
            val notValidMessage:String =  messageSource.getMessage(
                "email.emailIsBusy",null,
                LocaleContextHolder.getLocale())
            val errorDto = AppResponseErrorDto(HttpStatus.CONFLICT, notValidMessage)
            return Pair(errorDto, HttpStatus.CONFLICT)
        }
        return Pair(null, HttpStatus.OK)
    }
}
