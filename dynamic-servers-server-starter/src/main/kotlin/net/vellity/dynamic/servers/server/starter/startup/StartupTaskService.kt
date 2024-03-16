package net.vellity.dynamic.servers.server.starter.startup

interface StartupTaskService {
  fun start(task: StartupTask)
}