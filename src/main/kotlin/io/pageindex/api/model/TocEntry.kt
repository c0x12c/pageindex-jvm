package io.pageindex.api.model

data class TocEntry(
  val title: String,
  val pageNumber: Int?,
  val physicalIndex: Int?,
  val level: Int,
  val structure: String? = null
)
