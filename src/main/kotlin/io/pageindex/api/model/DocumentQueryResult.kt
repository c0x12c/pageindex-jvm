package io.pageindex.api.model

data class DocumentQueryResult<T>(
  val data: T,
  val selectedNodeIds: List<String>,
  val reasoning: String,
  val sourcePages: List<Int>
)
