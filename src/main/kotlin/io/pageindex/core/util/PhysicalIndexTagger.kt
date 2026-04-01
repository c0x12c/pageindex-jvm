package io.pageindex.core.util

import io.pageindex.api.model.ParsedPage

object PhysicalIndexTagger {
  fun tag(pages: List<ParsedPage>): List<ParsedPage> =
    pages.map { page ->
      ParsedPage(
        pageNumber = page.pageNumber,
        text = "<physical_index_${page.pageNumber}>${page.text}</physical_index_${page.pageNumber}>"
      )
    }
}
