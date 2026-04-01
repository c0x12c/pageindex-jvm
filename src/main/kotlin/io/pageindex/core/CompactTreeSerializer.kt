package io.pageindex.core

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.pageindex.api.model.DocumentTree
import io.pageindex.api.model.TreeNode

object CompactTreeSerializer {
  private val mapper: ObjectMapper = jacksonObjectMapper()

  fun toCompactJson(tree: DocumentTree): String {
    val compact = tree.copy(
      rootNodes = stripText(tree.rootNodes),
      nodeEmbeddings = emptyMap()
    )
    return mapper.writeValueAsString(compact)
  }

  fun stripText(nodes: List<TreeNode>): List<TreeNode> {
    return nodes.map { node ->
      node.copy(
        text = null,
        children = stripText(node.children)
      )
    }
  }
}
