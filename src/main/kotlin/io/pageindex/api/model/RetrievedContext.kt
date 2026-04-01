package io.pageindex.api.model

data class RetrievedContext(
  val selectedNodeIds: List<String>,
  val reasoning: String,
  val sectionsText: String,
  val sourcePages: List<Int>
)
