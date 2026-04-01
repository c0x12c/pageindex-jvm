package io.pageindex.api.model

data class LlmMessage(
  val role: LlmRole,
  val content: String
)
