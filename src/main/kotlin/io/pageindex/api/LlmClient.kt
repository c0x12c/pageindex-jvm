package io.pageindex.api

import io.pageindex.api.model.LlmMessage

interface LlmClient {
  suspend fun chat(messages: List<LlmMessage>, tags: List<String> = emptyList()): String

  suspend fun <T> chatStructured(
    messages: List<LlmMessage>,
    responseType: Class<T>,
    tags: List<String> = emptyList()
  ): String = chat(messages, tags)

  suspend fun <T> chatStructuredList(
    messages: List<LlmMessage>,
    elementType: Class<T>,
    tags: List<String> = emptyList()
  ): String = chat(messages, tags)
}
