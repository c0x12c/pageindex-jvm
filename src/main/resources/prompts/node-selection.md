You are a document retrieval system. Given the document index below and a user question,
select the nodes most likely to contain the answer.

Document Index:
{{compact_tree_json}}

Question: {{query}}

Return JSON: { "selected_node_ids": ["node_1", "node_3"], "reasoning": "..." }
Select at most {{max_nodes}} nodes. Prefer specific sections over broad ones.
