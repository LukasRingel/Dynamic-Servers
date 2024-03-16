package net.vellity.dynamic.servers.server.watcher.container.docker

import com.github.dockerjava.api.model.Frame
import com.github.dockerjava.core.InvocationBuilder

class DockerLogResultToConsumer(private val consumer: (ByteArray) -> Unit) :
  InvocationBuilder.AsyncResultCallback<Frame>() {
  override fun onNext(`object`: Frame?) {
    if (`object` != null) {
      consumer(`object`.payload)
    }
  }
}