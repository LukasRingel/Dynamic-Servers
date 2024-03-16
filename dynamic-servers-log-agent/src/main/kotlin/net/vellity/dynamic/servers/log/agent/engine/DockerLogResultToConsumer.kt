package net.vellity.dynamic.servers.log.agent.engine

import com.github.dockerjava.api.model.Frame
import com.github.dockerjava.core.InvocationBuilder

class DockerLogResultToConsumer(private val consumer: (String) -> Unit) :
  InvocationBuilder.AsyncResultCallback<Frame>() {
  override fun onNext(`object`: Frame?) {
    if (`object` != null) {
      consumer(String(`object`.payload, Charsets.UTF_8))
    }
  }
}