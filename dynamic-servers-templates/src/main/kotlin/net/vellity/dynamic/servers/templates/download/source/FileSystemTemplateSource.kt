package net.vellity.dynamic.servers.templates.download.source

import net.vellity.dynamic.servers.templates.entity.group.TemplateGroupProvider
import net.vellity.dynamic.servers.templates.entity.template.ContainerTemplateProvider
import org.apache.commons.compress.archivers.tar.TarArchiveEntry
import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream
import org.apache.commons.compress.utils.IOUtils
import org.springframework.stereotype.Component
import java.io.BufferedOutputStream
import java.io.ByteArrayOutputStream
import java.io.File
import java.util.zip.GZIPOutputStream

@Component
class FileSystemTemplateSource(
  private val templateMappingProvider: ContainerTemplateProvider,
  private val templateGroupProvider: TemplateGroupProvider
) : TemplateSource {
  override fun getTemplateAsGZip(containerTemplateId: String): ByteArray {
    val template = templateMappingProvider.getContainerTemplateById(containerTemplateId)
    val group = templateGroupProvider.getGroupsOfTemplate(template!!.id!!)
    val paths = template.pathsToCopy + group.flatMap { it.pathsToCopy }
    val result = compressDirectoriesToGzip(paths)
    return result
  }

  private fun compressDirectoriesToGzip(directoryPaths: List<String>): ByteArray {
    val byteStream = ByteArrayOutputStream()
    val fileContents = mutableMapOf<String, ByteArrayOutputStream>()

    // First, collect all files and merge content if filenames are the same
    directoryPaths.forEach { directoryPath ->
      val directory = File(directoryPath)
      directory.walk().filter { it.isFile }.forEach { file ->
        val entryName = file.absolutePath.substring(directory.absolutePath.length + 1)
        val outputStream = fileContents.getOrPut(entryName) { ByteArrayOutputStream() }
        if (outputStream.size() > 0) {
          // Append newline if we are about to append to an existing file content
          outputStream.write("\n".toByteArray())
        }
        file.inputStream().use { inputStream ->
          IOUtils.copy(inputStream, outputStream)
        }
      }
    }

    // Then, write the merged contents to the tar.gz archive
    TarArchiveOutputStream(BufferedOutputStream(GZIPOutputStream(byteStream))).use { tarOutput ->
      fileContents.forEach { (entryName, contentStream) ->
        val tarEntry = TarArchiveEntry(entryName)
        tarEntry.size = contentStream.size().toLong()
        tarOutput.putArchiveEntry(tarEntry)
        contentStream.writeTo(tarOutput)
        tarOutput.closeArchiveEntry()
      }
    }

    return byteStream.toByteArray()
  }
}