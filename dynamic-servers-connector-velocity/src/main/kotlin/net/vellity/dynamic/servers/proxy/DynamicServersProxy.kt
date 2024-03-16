package net.vellity.dynamic.servers.proxy

fun containerId(): String {
  return environmentOrDefault("DYNAMIC_SERVERS_CONTAINER_ID", "proxy-??")
}

fun serverStarterName(): String {
  return environmentOrDefault("DYNAMIC_SERVERS_SERVER_STARTER_NAME", "local")
}

fun templateId(): String {
  return environmentOrDefault("DYNAMIC_SERVERS_CONTAINER_TEMPLATE", "unknown")
}

fun containerHostname(): String {
  return environmentOrDefault("DYNAMIC_SERVERS_CONTAINER_HOSTNAME", "localhost")
}

fun containerPort(): String {
  return environmentOrDefault("DYNAMIC_SERVERS_CONTAINER_PORT", "25565")
}

fun environmentOrDefault(key: String, defaultValue: String): String {
  return if (System.getenv().containsKey(key)) {
    System.getenv(key)
  } else {
    defaultValue
  }
}