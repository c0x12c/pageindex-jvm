package io.pageindex.api

import io.pageindex.api.model.PromptName

interface PromptProvider {
  fun get(name: PromptName): String
}
