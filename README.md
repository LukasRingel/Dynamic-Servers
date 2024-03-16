# Dynamic Servers
Start minecraft or proxy servers per request with templates on docker environment.

### Backend Services
- **Log-Storage**: _Store & Get logs from servers_
- **Operations**: _Entrypoint for services/users not related to the application_
- **Server-Registry**: _Store & Get servers from the system_
- **Startup-Queue**: _Store & Get servers to start_
- **Templates**: _Provide templates for servers_
- **Log-Viewer**: _Backend for the log viewer web service_

### Node Services
- **Server-Starter**: _Start servers from the queue on the local docker environment_
- **Server-Watcher**: _Check for container state (updates) and watch logs for exceptions_
- **Log-Agent**: _Serves live-logs for the log viewer web service_

### Web Frontends
- **Log-Viewer**: _View logs from the log-storage or running servers_

## Used Technologies
- **Docker**: _Containerization of the services_
- **Kotlin**: _Backend services and node services_
- **React**: _Web frontends_
- **MongoDB**: _Database for the backend services_
- **Spring Boot**: _Backend services_

## How to run
Contact me