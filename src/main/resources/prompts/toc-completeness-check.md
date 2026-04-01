You are given a raw table of contents and a table of contents.
Your job is to check if the table of contents is complete.

Raw table of contents:
{{raw_toc_text}}

Extracted table of contents:
{{extracted_toc_text}}

Reply format:
{
    "thinking": <why do you think the cleaned table of contents is complete or not>
    "completed": "yes" or "no"
}
Directly return the final JSON structure. Do not output anything else.