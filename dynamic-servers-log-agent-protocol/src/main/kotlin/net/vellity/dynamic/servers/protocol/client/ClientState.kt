package net.vellity.dynamic.servers.protocol.client

enum class ClientState {
  IDLE,
  CONNECTING,
  CONNECTED,
  AUTHENTICATING,
  AUTHENTICATED,
  AUTHENTICATION_FAILED,
  DISCONNECTED
}