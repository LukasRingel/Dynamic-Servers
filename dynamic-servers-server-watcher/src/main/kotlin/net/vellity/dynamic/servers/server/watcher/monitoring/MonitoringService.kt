package net.vellity.dynamic.servers.server.watcher.monitoring

interface MonitoringService {
  fun announceIncident(incident: Incident, containerId: String = "")
}