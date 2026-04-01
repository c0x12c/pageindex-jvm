package io.pageindex.core

import io.pageindex.api.DocumentTreeStore
import io.pageindex.api.model.DocumentTree
import java.util.UUID

class InMemoryDocumentTreeStore : DocumentTreeStore {
  private val store = mutableMapOf<UUID, DocumentTree>()

  override suspend fun save(tree: DocumentTree) {
    if (!store.containsKey(tree.documentId)) {
      store[tree.documentId] = tree
    }
  }

  override suspend fun findByDocumentId(documentId: UUID): DocumentTree? {
    return store[documentId]
  }

  override suspend fun deleteByDocumentId(documentId: UUID) {
    store.remove(documentId)
  }
}
