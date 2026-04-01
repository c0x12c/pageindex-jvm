package io.pageindex.api

import io.pageindex.api.model.IndexingConfig
import io.pageindex.api.model.ParsedPage
import io.pageindex.api.model.StructureDetectionResult

/**
 * Detects document structure from parsed pages.
 *
 * Returns a [StructureDetectionResult] with TOC entries and the detection method used,
 * or `null` if this detector cannot handle the document.
 */
interface StructureDetector {
  suspend fun detect(pages: List<ParsedPage>, config: IndexingConfig): StructureDetectionResult?
}
