# PageIndex

LLM-powered hierarchical document indexing and retrieval for the JVM.

PageIndex builds a tree-structured index from documents (like a table of contents), then uses LLM reasoning to navigate it and find relevant sections. No vector database. No chunking. Just structure + reasoning.

## How It Works

**Stage 1: Build Index** — Detect document structure (TOC, headers, or LLM-based) and build a hierarchical tree.

**Stage 2: Retrieve** — Given a query, the LLM navigates the tree to select the most relevant sections.

```
PDF/Text → Structure Detection → Tree Index → LLM Navigation → Retrieved Sections
```

### Structure Detection Methods

PageIndex tries multiple detection strategies in order:

| Method | How |
|---|---|
| `TOC_WITH_PAGES` | Regex-based TOC detection (dot leaders, dash leaders, tab-separated) |
| `TOC_WITHOUT_PAGES` | LLM extracts TOC that lacks page numbers, then matches titles to pages |
| `HEADER_BASED` | Detects markdown-style headers (`# ## ###`) |
| `LLM_DETECTED` | LLM analyzes page groups to detect structure via map-reduce |
| `FLAT_PAGES` | Fallback: chunks pages by text length |

## Quick Start

### 1. Add Dependency

```kotlin
// build.gradle.kts
dependencies {
  implementation("io.pageindex:page-index:0.1.0")
}
```

### 2. Implement the LLM Interface

PageIndex doesn't care which LLM you use. Implement `LlmClient`:

```kotlin
class OpenAiLlmClient(private val apiKey: String) : LlmClient {
  override suspend fun chat(messages: List<LlmMessage>, tags: List<String>): String {
    // Call OpenAI, Claude, Gemini, local model, etc.
    return callYourLlm(messages)
  }
}
```

### 3. Implement Storage

```kotlin
class InMemoryTreeStore : DocumentTreeStore {
  private val store = ConcurrentHashMap<UUID, DocumentTree>()

  override suspend fun save(tree: DocumentTree) { store[tree.documentId] = tree }
  override suspend fun findByDocumentId(id: UUID) = store[id]
  override suspend fun deleteByDocumentId(id: UUID) { store.remove(id) }
}
```

### 4. Build and Query

```kotlin
// Wire up
val llmClient = OpenAiLlmClient(apiKey)
val promptProvider = ResourcePromptProvider() // loads built-in prompts
val structuredChat = DefaultStructuredChatService(llmClient)
val embeddingService = YourEmbeddingService()

val detector = ChainedStructureDetector(listOf(
  RegexTocDetector(),
  MarkdownHeaderDetector(),
  TocNoPageNumbersDetector(llmClient, promptProvider),
  LlmStructureDetector(llmClient, promptProvider),
  FlatPagesFallback()
))

val treeBuilder = DefaultTreeIndexBuilder(detector, embeddingService)
val nodeRetriever = DefaultNodeRetriever(structuredChat, maxNodes = 5)
val treeStore = InMemoryTreeStore()

val pageIndex = DefaultPageIndexManager(treeBuilder, nodeRetriever, structuredChat, llmClient, treeStore)

// Build index from parsed pages
val pages = listOf(
  ParsedPage(1, "Cover page text..."),
  ParsedPage(2, "Table of Contents\n1. Introduction ..... 3\n2. Methods ..... 5"),
  ParsedPage(3, "1. Introduction\nThis report covers..."),
  // ...
)

val config = IndexingConfig(
  maxNodeTextLength = 8000,
  summaryMaxTokens = 200,
  batchSummarySize = 10,
  tocScanPages = 10,
  minTocLineCount = 5,
  minHeaderCount = 3,
  verificationSampleSize = 3,
  verificationAccuracyThreshold = 0.7,
  maxFixAttempts = 2,
  maxExtractionRetries = 2,
  pageGroupMaxTokens = 4000,
  pageGroupOverlap = 1,
  fixSearchRadius = 3
)

val tree = pageIndex.buildAndSave(documentId, projectId, pages, config)

// Query the document
val result = pageIndex.query(
  documentId,
  listOf(LlmMessage(LlmRole.USER, "What methods were used?"))
)
// result.data = "The methods section describes..."
// result.sourcePages = [5, 6, 7]
```

## Interfaces You Implement

| Interface | Purpose |
|---|---|
| `LlmClient` | Chat with any LLM (OpenAI, Claude, Gemini, local) |
| `NodeEmbeddingService` | Generate text embeddings (for hybrid retrieval) |
| `DocumentTreeStore` | Persist/load document trees (database, file, memory) |
| `ParsedPageStore` | Persist/load parsed pages (optional) |

## Architecture

```
io.pageindex.api/          # Public interfaces and models
io.pageindex.core/         # Default implementations
  detector/                # Structure detection (regex, headers, LLM)
  retriever/               # Node retrieval (LLM-based, hybrid BM25+embedding)
  prompt/                  # Prompt template system
  verify/                  # TOC verification and fixing
  util/                    # JSON parsing, token counting, case conversion
io.pageindex.exception/    # PageIndexException
```

## Compared to Vector-Based RAG

| | Vector RAG | PageIndex |
|---|---|---|
| Chunking | Fixed-size or semantic | Natural document sections |
| Index | Embedding vectors | Hierarchical tree |
| Retrieval | Similarity search | LLM reasoning over structure |
| Interpretability | Low (which chunk matched?) | High (section title + page range) |
| Setup | Vector DB + embeddings | Just an LLM |

## Requirements

- JDK 21+
- Kotlin 2.0+
- An LLM API (any provider)

## License

Apache License 2.0 - see [LICENSE](LICENSE)
