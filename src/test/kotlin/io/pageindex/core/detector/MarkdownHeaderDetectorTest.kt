package io.pageindex.core.detector

import io.pageindex.api.model.IndexingMethod
import io.pageindex.api.model.ParsedPage
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

class MarkdownHeaderDetectorTest {

  private val detector = MarkdownHeaderDetector()
  private val config = testConfig()

  @Test
  fun `detect - finds headers and maps to levels`() = runTest {
    val pages = listOf(
      ParsedPage(
        pageNumber = 1,
        text = """
          # Introduction
          Some intro text.
          ## Background
          Background details.
        """.trimIndent()
      )
    )

    val result = detector.detect(pages, config)

    assertNotNull(result)
    assertEquals(IndexingMethod.HEADER_BASED, result?.method)
    assertEquals(2, result?.entries?.size)
    assertEquals("Introduction", result?.entries?.get(0)?.title)
    assertEquals(1, result?.entries?.get(0)?.level)
    assertEquals("Background", result?.entries?.get(1)?.title)
    assertEquals(2, result?.entries?.get(1)?.level)
  }

  @Test
  fun `detect - returns null when fewer than 2 headers`() = runTest {
    val pages = listOf(
      ParsedPage(pageNumber = 1, text = "# Only One Header\nSome text.")
    )

    val result = detector.detect(pages, config)

    assertNull(result)
  }

  @Test
  fun `detect - sets correct physicalIndex from page number`() = runTest {
    val pages = listOf(
      ParsedPage(pageNumber = 3, text = "# First Header\nText."),
      ParsedPage(pageNumber = 7, text = "# Second Header\nMore text.")
    )

    val result = detector.detect(pages, config)

    assertNotNull(result)
    assertEquals(3, result?.entries?.get(0)?.physicalIndex)
    assertEquals(7, result?.entries?.get(1)?.physicalIndex)
    assertNull(result?.entries?.get(0)?.pageNumber)
  }

  @Test
  fun `detect - handles mixed header levels`() = runTest {
    val pages = listOf(
      ParsedPage(
        pageNumber = 1,
        text = """
          # Chapter 1
          ## Section 1.1
          ### Subsection 1.1.1
          ## Section 1.2
        """.trimIndent()
      )
    )

    val result = detector.detect(pages, config)

    assertNotNull(result)
    assertEquals(4, result?.entries?.size)
    assertEquals(1, result?.entries?.get(0)?.level)
    assertEquals(2, result?.entries?.get(1)?.level)
    assertEquals(3, result?.entries?.get(2)?.level)
    assertEquals(2, result?.entries?.get(3)?.level)
  }

  @Test
  fun `detect - ignores headers inside code blocks`() = runTest {
    val pages = listOf(
      ParsedPage(
        pageNumber = 1,
        text = """
          # Real Header
          Some text.
          ```
          # This is a comment in code
          ## Also not a header
          ```
          ## Another Real Header
        """.trimIndent()
      )
    )

    val result = detector.detect(pages, config)

    assertNotNull(result)
    assertEquals(2, result?.entries?.size)
    assertEquals("Real Header", result?.entries?.get(0)?.title)
    assertEquals("Another Real Header", result?.entries?.get(1)?.title)
  }
}
