package io.pageindex.api

import io.pageindex.api.model.ParsedPage
import java.util.UUID

interface ParsedPageStore {
  suspend fun savePages(documentId: UUID, pages: List<ParsedPage>)
  suspend fun loadPages(documentId: UUID): List<ParsedPage>
  suspend fun countPages(documentId: UUID): Long
  suspend fun deletePages(documentId: UUID)
}
