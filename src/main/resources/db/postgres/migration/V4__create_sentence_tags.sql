-- Table: sentence_tags
-- Purpose: Stores classification flags for a sentence, e.g. "quote_block", "chapter_header", "skip_tts"
-- These tags may come from rule-based logic or external systems (spaCy, prompt router, etc.)


CREATE TABLE IF NOT EXISTS sentence_tags (
  id SERIAL PRIMARY KEY,
  sentence_id UUID REFERENCES sentences(id),
  tag TEXT,                    -- e.g., quote_block, skip_llm
  confidence FLOAT,
  source TEXT                  -- e.g., "spacy-llm"
);