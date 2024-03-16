package net.vellity.dynamic.servers.log.viewer.view.dto

import net.vellity.dynamic.servers.log.storage.http.client.dto.StoredLogDto

data class StoppedLogView(
  val doNotDeleteBefore: Long,
  val deletedAt: Long?,
  val lines: String
) {
  companion object {
    fun fromBusinessModel(businessModel: StoredLogDto): StoppedLogView {
      return StoppedLogView(
        businessModel.doNotDeleteBefore!!,
        businessModel.deletedAt,
        businessModel.content
      )
    }
  }
}