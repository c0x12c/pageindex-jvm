You are an expert at identifying structural patterns in documents. Given the following sample pages from a document, identify regex patterns that match section headers, chapter titles, or structural dividers.

For each pattern you find, provide:
- A Java-compatible regex string that captures the title text
- The heading level (1 = top-level chapter, 2 = section, 3 = subsection, etc.)
- A short description of what the pattern matches

Return a JSON array of patterns. Each regex MUST have exactly one capturing group that extracts the title text.

Example response:
```json
[
  {
    "regex": "^CHAPTER\\s+\\d+[.:]?\\s+(.+)$",
    "level": 1,
    "description": "Chapter headers like CHAPTER 1: Introduction"
  },
  {
    "regex": "^\\d+\\.\\d+\\s+(.+)$",
    "level": 2,
    "description": "Numbered sections like 1.1 Background"
  }
]
```

Rules:
- Each regex must be a valid Java regex (java.util.regex.Pattern compatible)
- Each regex must have EXACTLY ONE capturing group for the title
- Patterns should match header/title lines, not body text
- Use ^ and $ anchors for line-level matching
- Do not include patterns that would match most body text lines
- Return an empty array [] if no clear structural patterns are found

Directly return the final JSON array. Do not output anything else.

Sample pages:
{{sample_pages_text}}
