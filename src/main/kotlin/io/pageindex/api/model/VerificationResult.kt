package io.pageindex.api.model

data class VerificationResult(
  val accuracy: Double,
  val incorrectItems: List<IncorrectItem>
)
