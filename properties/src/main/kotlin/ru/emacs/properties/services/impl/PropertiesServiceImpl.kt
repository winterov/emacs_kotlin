package ru.emacs.properties.services.impl

import jakarta.el.PropertyNotFoundException
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import ru.emacs.properties.repositories.PropertiesRepository
import ru.emacs.properties.services.PropertiesConverter

import ru.emacs.properties.services.PropertiesService

@Service
internal class PropertiesServiceImpl(
    private val propertiesRepository: PropertiesRepository,
    private val propertiesConverter: PropertiesConverter
) : PropertiesService {
    private val log = LoggerFactory.getLogger(PropertiesServiceImpl::class.java)!!
    override fun <T> getProperties(t: Class<T>): T {
        log.debug(t.name)
        val property = propertiesRepository.getProperty(t.name) ?: throw PropertyNotFoundException()
        return propertiesConverter.convertFromJson(property.json, t)
    }


}