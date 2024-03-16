package net.vellity.dynamic.servers.startup.queue.queue

import org.springframework.stereotype.Component
import java.util.*

@Component
class LocalStartupQueue : StartupQueue {
  private val queue: Queue<StartupQueueEntry> = PriorityQueue(Comparator.comparing(StartupQueueEntry::priority))

  override fun poll(): StartupQueueEntry? {
    synchronized(queue) {
      return queue.poll()
    }
  }

  override fun all(): List<StartupQueueEntry> {
    synchronized(queue) {
      return queue.toList()
    }
  }

  override fun add(entry: StartupQueueEntry) {
    synchronized(queue) {
      queue.add(entry)
    }
  }

  override fun clear() {
    synchronized(queue) {
      queue.clear()
    }
  }
}