package net.vellity.dynamic.servers.startup.queue.queue

interface StartupQueue {
  fun poll(): StartupQueueEntry?

  fun all(): List<StartupQueueEntry>

  fun add(entry: StartupQueueEntry)

  fun clear()
}