package net.vellity.dynamic.servers.log.storage.rest.dto

data class StoreLogRequestDto(
  val serverId: String,
  val content: ByteArray
) {
  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false

    other as StoreLogRequestDto

    if (serverId != other.serverId) return false
    if (!content.contentEquals(other.content)) return false

    return true
  }

  override fun hashCode(): Int {
    var result = serverId.hashCode()
    result = 31 * result + content.contentHashCode()
    return result
  }
}
