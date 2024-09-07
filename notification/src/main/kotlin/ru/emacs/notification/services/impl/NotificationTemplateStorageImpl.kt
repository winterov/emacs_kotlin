package ru.emacs.notification.services.impl

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationEvent
import org.springframework.stereotype.Service
import ru.emacs.notification.agregators.NotificationTemplate
import ru.emacs.notification.repositories.NotificationTemplateRepository
import ru.emacs.notification.services.NotificationTemplateStorage

@Service
internal class NotificationTemplateStorageImpl @Autowired constructor(
    private val notificationTemplateRepository: NotificationTemplateRepository
): NotificationTemplateStorage {

    /*@Cacheable("getNotificationTemplate")*/
    override fun getEnabledTemplate(event: ApplicationEvent):List<NotificationTemplate> {
       return notificationTemplateRepository.findByEventClassAndActual(event.javaClass.simpleName,true)
    }
}