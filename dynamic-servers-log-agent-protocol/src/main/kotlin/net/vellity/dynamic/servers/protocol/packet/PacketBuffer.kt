package net.vellity.dynamic.servers.protocol.packet

import io.netty.buffer.ByteBuf
import java.util.*

class PacketBuffer(private val byteBuf: ByteBuf) {
  fun writeBoolean(value: Boolean) {
    byteBuf.writeBoolean(value)
  }

  fun readBoolean(): Boolean {
    return byteBuf.readBoolean()
  }

  fun writeByte(value: Byte) {
    byteBuf.writeByte(value.toInt())
  }

  fun readByte(): Byte {
    return byteBuf.readByte()
  }

  fun writeShort(value: Short) {
    byteBuf.writeShort(value.toInt())
  }

  fun readShort(): Short {
    return byteBuf.readShort()
  }

  fun writeInt(value: Int) {
    byteBuf.writeInt(value)
  }

  fun readInt(): Int {
    return byteBuf.readInt()
  }

  fun writeLong(value: Long) {
    byteBuf.writeLong(value)
  }

  fun readLong(): Long {
    return byteBuf.readLong()
  }

  fun writeFloat(value: Float) {
    byteBuf.writeFloat(value)
  }

  fun readFloat(): Float {
    return byteBuf.readFloat()
  }

  fun writeDouble(value: Double) {
    byteBuf.writeDouble(value)
  }

  fun readDouble(): Double {
    return byteBuf.readDouble()
  }

  fun readElement(clazz: Class<out Element>): Element {
    val element = clazz.getConstructor().newInstance()
    element.read(this)
    return element
  }

  fun writeElement(element: Element) {
    element.write(this)
  }

  fun writeVarInt(value: Int) {
    var value = value
    while (true) {
      if (value and -0x80 == 0) {
        byteBuf.writeByte(value)
        return
      }
      byteBuf.writeByte(value and 0x7F or 0x80)
      value = value ushr 7
    }
  }

  fun readVarInt(): Int {
    var value = 0
    var size = 0
    var b: Int
    while (true) {
      b = byteBuf.readByte().toInt()
      value = value or (b and 0x7F shl size++ * 7)
      if (size > 5) {
        throw RuntimeException("VarInt too big")
      }
      if (b and 0x80 != 0x80) {
        break
      }
    }
    return value
  }

  fun writeVarLong(value: Long) {
    var value = value
    while (true) {
      if (value and -0x80L == 0L) {
        byteBuf.writeByte(value.toInt())
        return
      }
      byteBuf.writeByte((value and 0x7FL or 0x80L).toInt())
      value = value ushr 7
    }
  }

  fun readVarLong(): Long {
    var value = 0L
    var size = 0
    var b: Long
    while (true) {
      b = byteBuf.readByte().toLong()
      value = value or (b and 0x7FL shl size++ * 7)
      if (size > 10) {
        throw RuntimeException("VarLong too big")
      }
      if (b and 0x80L != 0x80L) {
        break
      }
    }
    return value
  }

  fun writeString(value: String) {
    val bytes = value.toByteArray()
    writeVarInt(bytes.size)
    byteBuf.writeBytes(bytes)
  }

  fun readString(): String {
    val length = readVarInt()
    val bytes = ByteArray(length)
    byteBuf.readBytes(bytes)
    return String(bytes)
  }

  fun writeByteArray(value: ByteArray) {
    writeVarInt(value.size)
    byteBuf.writeBytes(value)
  }

  fun readByteArray(): ByteArray {
    val length = readVarInt()
    val bytes = ByteArray(length)
    byteBuf.readBytes(bytes)
    return bytes
  }

  fun writeIntArray(value: IntArray) {
    writeVarInt(value.size)
    value.forEach { writeInt(it) }
  }

  fun readIntArray(): IntArray {
    val length = readVarInt()
    val ints = IntArray(length)
    for (i in 0 until length) {
      ints[i] = readInt()
    }
    return ints
  }

  fun writeLongArray(value: LongArray) {
    writeVarInt(value.size)
    value.forEach { writeLong(it) }
  }

  fun readLongArray(): LongArray {
    val length = readVarInt()
    val longs = LongArray(length)
    for (i in 0 until length) {
      longs[i] = readLong()
    }
    return longs
  }

  fun writeUUID(value: UUID) {
    writeLong(value.mostSignificantBits)
    writeLong(value.leastSignificantBits)
  }

  fun readUUID(): UUID {
    return UUID(readLong(), readLong())
  }

  fun writeEnum(value: Enum<*>) {
    writeVarInt(value.ordinal)
  }

  fun readEnum(enumClass: Class<out Enum<*>>): Enum<*> {
    return enumClass.enumConstants[readVarInt()]
  }

  fun <T> writeList(collection: Collection<T>, writer: (T) -> Unit) {
    writeVarInt(collection.size)
    collection.forEach { writer(it) }
  }

  fun <T> readList(reader: () -> T): List<T> {
    val length = readVarInt()
    val list = mutableListOf<T>()
    for (i in 0 until length) {
      list.add(reader())
    }
    return list
  }

  fun <T> writeSet(collection: Collection<T>, writer: (T) -> Unit) {
    writeVarInt(collection.size)
    collection.forEach { writer(it) }
  }

  fun <T> readSet(reader: () -> T): Set<T> {
    val length = readVarInt()
    val set = mutableSetOf<T>()
    for (i in 0 until length) {
      set.add(reader())
    }
    return set
  }

  fun <K, V> writeMap(map: Map<K, V>, keyWriter: (K) -> Unit, valueWriter: (V) -> Unit) {
    writeVarInt(map.size)
    map.forEach { keyWriter(it.key); valueWriter(it.value) }
  }

  fun <K, V> readMap(keyReader: () -> K, valueReader: () -> V): Map<K, V> {
    val length = readVarInt()
    val map = mutableMapOf<K, V>()
    for (i in 0 until length) {
      map[keyReader()] = valueReader()
    }
    return map
  }
}