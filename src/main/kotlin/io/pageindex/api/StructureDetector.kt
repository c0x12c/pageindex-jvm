package io.pageindex.api

import io.pageindex.api.model.IndexingConfig
import io.pageindex.api.model.ParsedPage
import io.pageindex.api.model.StructureDetectionResult

interface StructureDetector {
  suspend fun detect(pages: List<ParsedPage>, config: IndexingConfig): StructureDetectionResult?
}
