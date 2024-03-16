package net.vellity.dynamic.servers.log.viewer.home.dto

import net.vellity.dynamic.servers.server.registry.http.client.dto.RegisteredServerDto
import java.time.Instant

data class RunningServerDto(
  val id: String,
  val template: String,
  val executor: String,
  val status: ServerStatusDto,
  val createdAt: Instant,
  val tags: List<String>
) {
  companion object {
    fun fromBusinessModel(businessModel: RegisteredServerDto): RunningServerDto {
      return RunningServerDto(
        id = businessModel.id.toString(),
        template = businessModel.template,
        executor = businessModel.executorId,
        status = ServerStatusDto.fromBusinessModel(businessModel.status),
        createdAt = Instant.ofEpochMilli(businessModel.createdAt),
        tags = businessModel.tags
      )
    }
  }
}