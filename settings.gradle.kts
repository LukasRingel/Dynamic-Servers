plugins {
  id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
}
rootProject.name = "dynamic-servers"

// connector
include("dynamic-servers-connector-velocity")

// common
include("dynamic-servers-common-http-server")
include("dynamic-servers-common-http-client")

// microservices (backend)
include("dynamic-servers-server-registry")
include("dynamic-servers-server-registry-http-client")

include("dynamic-servers-templates")
include("dynamic-servers-templates-http-client")

include("dynamic-servers-startup-queue")
include("dynamic-servers-startup-queue-http-client")

include("dynamic-servers-log-storage")
include("dynamic-servers-log-storage-http-client")

include("dynamic-servers-log-viewer")

// microservices (node)
include("dynamic-servers-server-starter")
include("dynamic-servers-server-watcher")
include("dynamic-servers-server-watcher-http-client")

// microservices (frontend)
include("dynamic-servers-operations")
include("dynamic-servers-log-agent")
include("dynamic-servers-log-agent-protocol")