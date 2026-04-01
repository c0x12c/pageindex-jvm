package io.pageindex.api

import io.pageindex.api.model.PromptName

/**
 * Provides prompt templates by name.
 *
 * Default implementation [io.pageindex.core.prompt.ResourcePromptProvider] loads
 * prompts from classpath resources.
 */
interface PromptProvider {
  fun get(name: PromptName): String
}
