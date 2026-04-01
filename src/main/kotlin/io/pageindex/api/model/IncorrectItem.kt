package io.pageindex.api.model

data class IncorrectItem(
  val entry: TocEntry,
  val expectedPhysicalIndex: Int?,
  val reason: String
)
