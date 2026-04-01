package io.pageindex.api

import io.pageindex.api.model.DocumentTree
import java.util.UUID

interface DocumentTreeStore {
  suspend fun save(tree: DocumentTree)
  suspend fun findByDocumentId(documentId: UUID): DocumentTree?
  suspend fun deleteByDocumentId(documentId: UUID)
}
