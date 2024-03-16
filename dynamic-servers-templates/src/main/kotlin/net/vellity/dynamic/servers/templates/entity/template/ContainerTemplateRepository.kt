package net.vellity.dynamic.servers.templates.entity.template

import org.springframework.data.mongodb.repository.MongoRepository

interface ContainerTemplateRepository : MongoRepository<ContainerTemplate, String>