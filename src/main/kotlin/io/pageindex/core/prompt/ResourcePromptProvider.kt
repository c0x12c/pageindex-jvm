package io.pageindex.core.prompt

import io.pageindex.api.PromptProvider
import io.pageindex.api.model.PromptName
import java.util.concurrent.ConcurrentHashMap

class ResourcePromptProvider : PromptProvider {
  private val cache = ConcurrentHashMap<PromptName, String>()

  override fun get(name: PromptName): String {
    return cache.getOrPut(name) {
      val path = "prompts/${name.fileName}.md"
      javaClass.classLoader?.getResourceAsStream(path)
        ?.bufferedReader()?.readText()
        ?: error("Prompt resource not found: $path")
    }
  }
}
