package io.pageindex.api

interface NodeEmbeddingService {
  suspend fun embed(texts: List<String>): List<FloatArray>
  suspend fun embedSingle(text: String): FloatArray
}
