package io.pageindex.api.model

data class IndexingConfig(
  val maxNodeTextLength: Int,
  val summaryMaxTokens: Int,
  val batchSummarySize: Int,
  val tocScanPages: Int,
  val minTocLineCount: Int,
  val minHeaderCount: Int,
  val verificationSampleSize: Int,
  val verificationAccuracyThreshold: Double,
  val maxFixAttempts: Int,
  val maxExtractionRetries: Int,
  val pageGroupMaxTokens: Int,
  val pageGroupOverlap: Int,
  val fixSearchRadius: Int,
  val summaryConcurrency: Int = 5,
  val embeddingConcurrency: Int = 3,
  val structureDetectionConcurrency: Int = 5
)
