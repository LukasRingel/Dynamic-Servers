package net.vellity.dynamic.servers.log.storage.rest.dto

import net.vellity.dynamic.servers.log.storage.entity.StoredLog
import java.time.Instant

data class StoredLogDto(
  val deletedAt: Long?,
  val doNotDeleteBefore: Long?,
  val content: ByteArray
) {
  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false

    other as StoredLogDto

    if (deletedAt != other.deletedAt) return false
    if (doNotDeleteBefore != other.doNotDeleteBefore) return false
    if (!content.contentEquals(other.content)) return false

    return true
  }

  override fun hashCode(): Int {
    var result = deletedAt?.hashCode() ?: 0
    result = 31 * result + (doNotDeleteBefore?.hashCode() ?: 0)
    result = 31 * result + content.contentHashCode()
    return result
  }

  companion object {
    fun fromBusinessModel(model: StoredLog): StoredLogDto {
      return StoredLogDto(
        model.deletedAt?.toEpochMilli(),
        model.doNotDeleteBefore?.toEpochMilli(),
        model.content!!
      )
    }
  }
}