package io.pageindex.core.util

import io.pageindex.api.model.ParsedPage

object TokenCounter {
  private const val CHARS_PER_TOKEN = 4

  fun estimate(text: String): Int = text.length / CHARS_PER_TOKEN

  fun estimate(pages: List<ParsedPage>): Int = pages.sumOf { estimate(it.text) }
}
