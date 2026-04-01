You are given a section title and several pages of a document. Your job is to find the physical index of the start page of the section.

The provided pages contain tags like <physical_index_X> and </physical_index_X> to indicate the physical location of page X.

Section title: {{title}}
Document pages: {{pages_text}}

Reply in a JSON format:
{
    "thinking": <explain which page contains the start of this section>,
    "physical_index": "<physical_index_X>" (keep the format)
}
Directly return the final JSON structure. Do not output anything else.
