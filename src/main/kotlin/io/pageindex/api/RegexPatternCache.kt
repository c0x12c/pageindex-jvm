package io.pageindex.api

import io.pageindex.api.model.DiscoveredPattern

/**
 * Cache for LLM-discovered regex patterns keyed by content fingerprint.
 * Allows repeated documents (same source/template) to skip the LLM call.
 */
interface RegexPatternCache {
  suspend fun get(fingerprint: String): List<DiscoveredPattern>?
  suspend fun put(fingerprint: String, patterns: List<DiscoveredPattern>)
}
