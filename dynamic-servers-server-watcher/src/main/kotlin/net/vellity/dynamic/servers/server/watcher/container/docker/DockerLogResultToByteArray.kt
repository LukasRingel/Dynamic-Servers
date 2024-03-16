package net.vellity.dynamic.servers.server.watcher.container.docker

import com.github.dockerjava.api.model.Frame
import com.github.dockerjava.core.InvocationBuilder

class DockerLogResultToByteArray : InvocationBuilder.AsyncResultCallback<Frame>() {
  var byteArray: ByteArray = ByteArray(0)

  override fun onNext(`object`: Frame?) {
    if (`object` != null) {
      byteArray += `object`.payload
    }
  }
}