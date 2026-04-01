package io.pageindex.api.model

import java.util.UUID

data class DocumentTree(
  val documentId: UUID,
  val projectId: UUID,
  val rootNodes: List<TreeNode>,
  val metadata: TreeMetadata,
  val nodeEmbeddings: Map<String, FloatArray> = emptyMap()
)
