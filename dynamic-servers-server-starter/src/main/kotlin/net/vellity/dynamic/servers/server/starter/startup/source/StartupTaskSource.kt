package net.vellity.dynamic.servers.server.starter.startup.source

import net.vellity.dynamic.servers.server.starter.startup.StartupTask

interface StartupTaskSource {
  fun getNextStartupTask(): StartupTask?
}