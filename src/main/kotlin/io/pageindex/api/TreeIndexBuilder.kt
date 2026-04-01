package io.pageindex.api

import arrow.core.Either
import io.pageindex.exception.PageIndexException
import io.pageindex.api.model.DocumentTree
import io.pageindex.api.model.IndexingConfig
import io.pageindex.api.model.ParsedPage
import java.util.UUID

interface TreeIndexBuilder {
  suspend fun buildIndex(
    documentId: UUID,
    projectId: UUID,
    pages: List<ParsedPage>,
    config: IndexingConfig
  ): Either<PageIndexException, DocumentTree>
}
