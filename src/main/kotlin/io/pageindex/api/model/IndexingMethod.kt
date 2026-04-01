package io.pageindex.api.model

enum class IndexingMethod {
  TOC_WITH_PAGES,
  TOC_WITHOUT_PAGES,
  HEADER_BASED,
  LLM_DETECTED,
  FLAT_PAGES
}
