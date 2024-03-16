package net.vellity.dynamic.servers.server.starter.startup.port

interface PortResolver {
  fun findFreePort(): Int
}