package io.pageindex.api

/**
 * Generates text embeddings for tree nodes.
 *
 * Used for hybrid retrieval (BM25 + embedding similarity).
 * Use [io.pageindex.core.NoOpEmbeddingService] if you only need LLM-based retrieval.
 */
interface NodeEmbeddingService {
  suspend fun embed(texts: List<String>): List<FloatArray>
  suspend fun embedSingle(text: String): FloatArray
}
