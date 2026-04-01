package io.pageindex.api

import arrow.core.Either
import io.pageindex.exception.PageIndexException
import io.pageindex.api.model.DocumentTree
import io.pageindex.api.model.LlmMessage
import io.pageindex.api.model.RetrievedContext

interface NodeRetriever {
  suspend fun retrieve(
    tree: DocumentTree,
    messages: List<LlmMessage>
  ): Either<PageIndexException, RetrievedContext>
}
