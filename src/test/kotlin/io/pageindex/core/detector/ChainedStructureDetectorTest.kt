package io.pageindex.core.detector

import io.pageindex.api.model.IndexingMethod
import io.pageindex.api.model.ParsedPage
import io.pageindex.api.model.StructureDetectionResult
import io.pageindex.api.model.TocEntry
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

class ChainedStructureDetectorTest {

  private val config = testConfig()
  private val pages = listOf(ParsedPage(pageNumber = 1, text = "text"))

  @Test
  fun `detect - returns first non-null result`() = runTest {
    val expected = fixedResult("First")
    val detector = ChainedStructureDetector(
      listOf(
        FixedDetector(expected),
        FixedDetector(fixedResult("Second"))
      )
    )

    val result = detector.detect(pages, config)

    assertEquals(expected, result)
  }

  @Test
  fun `detect - skips detectors that return null`() = runTest {
    val expected = fixedResult("Found")
    val detector = ChainedStructureDetector(
      listOf(
        NullDetector(),
        NullDetector(),
        FixedDetector(expected)
      )
    )

    val result = detector.detect(pages, config)

    assertEquals(expected, result)
  }

  @Test
  fun `detect - tries all detectors in order`() = runTest {
    val callOrder = mutableListOf<String>()
    val detector = ChainedStructureDetector(
      listOf(
        TrackingDetector("A", null, callOrder),
        TrackingDetector("B", null, callOrder),
        TrackingDetector("C", fixedResult("C"), callOrder)
      )
    )

    detector.detect(pages, config)

    assertEquals(listOf("A", "B", "C"), callOrder)
  }

  @Test
  fun `detect - returns null when all detectors return null`() = runTest {
    val detector = ChainedStructureDetector(
      listOf(NullDetector(), NullDetector())
    )

    val result = detector.detect(pages, config)

    assertNull(result)
  }

  @Test
  fun `detect - stops after first match`() = runTest {
    val callOrder = mutableListOf<String>()
    val detector = ChainedStructureDetector(
      listOf(
        TrackingDetector("A", fixedResult("A"), callOrder),
        TrackingDetector("B", null, callOrder)
      )
    )

    detector.detect(pages, config)

    assertEquals(listOf("A"), callOrder)
  }

  private fun fixedResult(name: String) = StructureDetectionResult(
    entries = listOf(TocEntry(title = name, pageNumber = 1, physicalIndex = 1, level = 1)),
    method = IndexingMethod.HEADER_BASED
  )
}
