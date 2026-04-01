package io.pageindex.core.detector

import io.pageindex.api.StructureDetector
import io.pageindex.api.model.IndexingConfig
import io.pageindex.api.model.ParsedPage
import io.pageindex.api.model.StructureDetectionResult

class TrackingDetector(
  private val name: String,
  private val result: StructureDetectionResult?,
  private val callOrder: MutableList<String>
) : StructureDetector {
  override suspend fun detect(
    pages: List<ParsedPage>,
    config: IndexingConfig
  ): StructureDetectionResult? {
    callOrder.add(name)
    return result
  }
}
