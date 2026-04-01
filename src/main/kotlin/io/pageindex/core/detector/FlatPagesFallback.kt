package io.pageindex.core.detector

import io.pageindex.api.StructureDetector
import io.pageindex.api.model.IndexingConfig
import io.pageindex.api.model.IndexingMethod
import io.pageindex.api.model.ParsedPage
import io.pageindex.api.model.StructureDetectionResult
import io.pageindex.api.model.TocEntry

class FlatPagesFallback : StructureDetector {

  override suspend fun detect(
    pages: List<ParsedPage>,
    config: IndexingConfig
  ): StructureDetectionResult {
    val entries = mutableListOf<TocEntry>()
    var currentChunkStart = 0
    var currentChunkLength = 0

    for (i in pages.indices) {
      val pageLength = pages[i].text.length
      if (currentChunkLength + pageLength > config.maxNodeTextLength && currentChunkLength > 0) {
        entries.add(buildEntry(pages, currentChunkStart, i - 1))
        currentChunkStart = i
        currentChunkLength = 0
      }
      currentChunkLength += pageLength
    }

    if (currentChunkStart < pages.size) {
      entries.add(buildEntry(pages, currentChunkStart, pages.size - 1))
    }

    return StructureDetectionResult(
      entries = entries,
      method = IndexingMethod.FLAT_PAGES
    )
  }

  private fun buildEntry(pages: List<ParsedPage>, startIdx: Int, endIdx: Int): TocEntry {
    val startPage = pages[startIdx].pageNumber
    val endPage = pages[endIdx].pageNumber
    val title = if (startPage == endPage) "Page $startPage" else "Pages $startPage-$endPage"
    return TocEntry(
      title = title,
      pageNumber = startPage,
      physicalIndex = startPage,
      level = 1
    )
  }
}
