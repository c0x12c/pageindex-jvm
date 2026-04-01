package io.pageindex.api

import arrow.core.Either
import io.pageindex.exception.PageIndexException
import io.pageindex.api.model.LlmMessage

interface StructuredChatService {
  suspend fun <T> chat(
    messages: List<LlmMessage>,
    responseType: Class<T>,
    tags: List<String> = emptyList()
  ): Either<PageIndexException, T>

  suspend fun <T> chatList(
    messages: List<LlmMessage>,
    elementType: Class<T>,
    tags: List<String> = emptyList()
  ): Either<PageIndexException, List<T>>
}
