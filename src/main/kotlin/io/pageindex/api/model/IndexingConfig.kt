package io.pageindex.api.model

/**
 * Configuration for document indexing. All parameters have sensible defaults.
 *
 * Use `IndexingConfig()` for defaults, or override specific values:
 * ```kotlin
 * IndexingConfig(maxNodeTextLength = 12000, summaryMaxTokens = 300)
 * ```
 *
 * @property maxNodeTextLength Maximum character length per tree node before splitting. Larger values
 *   keep more context per node but increase LLM token usage. Default: 8000.
 * @property summaryMaxTokens Token limit for auto-generated node summaries. Default: 200.
 * @property batchSummarySize Number of nodes to summarize in a single LLM call. Default: 10.
 * @property tocScanPages Number of pages from the start to scan for a table of contents. Default: 10.
 * @property minTocLineCount Minimum lines on a page to consider it a TOC page. Default: 3.
 * @property minHeaderCount Minimum markdown headers required for header-based detection. Default: 3.
 * @property verificationSampleSize Number of TOC entries to sample for accuracy verification. Default: 3.
 * @property verificationAccuracyThreshold Fraction of sampled entries that must match (0.0–1.0). Default: 0.7.
 * @property maxFixAttempts Maximum attempts to auto-fix incorrect TOC mappings. Default: 2.
 * @property maxExtractionRetries Maximum retries for LLM-based structure extraction. Default: 2.
 * @property pageGroupMaxTokens Token budget per page group when using LLM structure detection. Default: 4000.
 * @property pageGroupOverlap Number of overlapping pages between adjacent groups. Default: 1.
 * @property fixSearchRadius Pages to search around a TOC entry when fixing page mappings. Default: 3.
 * @property summaryConcurrency Maximum concurrent summary generation requests. Default: 5.
 * @property embeddingConcurrency Maximum concurrent embedding requests. Default: 3.
 * @property structureDetectionConcurrency Maximum concurrent structure detection requests. Default: 5.
 */
data class IndexingConfig(
  val maxNodeTextLength: Int = 8000,
  val summaryMaxTokens: Int = 200,
  val batchSummarySize: Int = 10,
  val tocScanPages: Int = 10,
  val minTocLineCount: Int = 3,
  val minHeaderCount: Int = 3,
  val verificationSampleSize: Int = 3,
  val verificationAccuracyThreshold: Double = 0.7,
  val maxFixAttempts: Int = 2,
  val maxExtractionRetries: Int = 2,
  val pageGroupMaxTokens: Int = 4000,
  val pageGroupOverlap: Int = 1,
  val fixSearchRadius: Int = 3,
  val summaryConcurrency: Int = 5,
  val embeddingConcurrency: Int = 3,
  val structureDetectionConcurrency: Int = 5
)
