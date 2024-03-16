package net.vellity.dynamic.servers.common.http.server.filter

import jakarta.servlet.FilterChain
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import net.vellity.dynamic.servers.common.http.server.environmentOrDefault
import org.springframework.stereotype.Service
import org.springframework.web.filter.GenericFilterBean

@Service
class RequestApiKeyFilter : GenericFilterBean() {
  override fun doFilter(request: ServletRequest?, response: ServletResponse?, chain: FilterChain?) {
    if (skip) {
      chain?.doFilter(request, response)
      return
    }

    if (request == null || response == null || chain == null) {
      return
    }

    if ((request as HttpServletRequest).getHeader(API_KEY_HEADER) == null) {
      logger.warn(request.remoteAddr + " attempted to access the API without an API key")
      (response as HttpServletResponse).sendError(HttpServletResponse.SC_UNAUTHORIZED, "Missing API key")
      return
    }

    if (!request.getHeader(API_KEY_HEADER).equals(environmentOrDefault("API_KEY", "default-api-key"))) {
      logger.warn(request.remoteAddr + " attempted to access the API with an invalid API key")
      (response as HttpServletResponse).sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid API key")
      return
    }

    chain.doFilter(request, response)
  }

  companion object {
    private const val API_KEY_HEADER = "X-API-KEY"
    private val skip = environmentOrDefault("SKIP_API_KEY_FILTER", "false").toBoolean()
  }
}