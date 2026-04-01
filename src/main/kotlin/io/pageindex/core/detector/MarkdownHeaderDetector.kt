package io.pageindex.core.detector

import io.pageindex.api.StructureDetector
import io.pageindex.api.model.IndexingConfig
import io.pageindex.api.model.IndexingMethod
import io.pageindex.api.model.ParsedPage
import io.pageindex.api.model.StructureDetectionResult
import io.pageindex.api.model.TocEntry

class MarkdownHeaderDetector : StructureDetector {

  override suspend fun detect(
    pages: List<ParsedPage>,
    config: IndexingConfig
  ): StructureDetectionResult? {
    val entries = mutableListOf<TocEntry>()

    for (page in pages) {
      val lines = page.text.lines()
      var inCodeBlock = false

      for (line in lines) {
        if (line.trimStart().startsWith("```")) {
          inCodeBlock = !inCodeBlock
          continue
        }
        if (inCodeBlock) continue

        val match = HEADER_PATTERN.find(line) ?: continue
        val hashes = match.groupValues[1]
        val title = match.groupValues[2].trim()
        if (title.isBlank()) continue

        entries.add(
          TocEntry(
            title = title,
            pageNumber = null,
            physicalIndex = page.pageNumber,
            level = hashes.length
          )
        )
      }
    }

    if (entries.size < config.minHeaderCount) return null

    return StructureDetectionResult(
      entries = entries,
      method = IndexingMethod.HEADER_BASED
    )
  }

  companion object {
    private val HEADER_PATTERN = Regex("^(#{1,6})\\s+(.+)$")
  }
}
