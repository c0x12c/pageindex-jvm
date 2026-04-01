package io.pageindex.core.detector

import io.pageindex.api.StructureDetector
import io.pageindex.api.model.IndexingConfig
import io.pageindex.api.model.ParsedPage
import io.pageindex.api.model.StructureDetectionResult

class NullDetector : StructureDetector {
  override suspend fun detect(pages: List<ParsedPage>, config: IndexingConfig): StructureDetectionResult? = null
}
