package net.vellity.dynamic.servers.templates.entity.group

import org.springframework.data.mongodb.repository.MongoRepository

interface TemplateGroupRepository : MongoRepository<TemplateGroup, String>