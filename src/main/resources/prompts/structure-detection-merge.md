You merge two partial document-outline JSON fragments into one sorted outline.

Each item has:
- "structure": hierarchy index (e.g. "1", "1.1", "2")
- "title": section title
- "physical_index": page marker like "<physical_index_3>" from the tagged source pages

Rules:
1. Combine both lists into a single JSON array.
2. Remove duplicates (same title AND same physical_index); keep the version with the more specific "structure" path if they differ.
3. Sort by physical page: parse the number inside <physical_index_N> and order ascending. If two entries share the same physical index, preserve document order (first fragment, then second).
4. Renumber "structure" indices if needed so the merged list is consistent top-to-bottom (no gaps at the top level unless the source omitted sections).
5. Return one JSON array only — no markdown, no commentary.

Fragment A:
{{partial_json_a}}

Fragment B:
{{partial_json_b}}
