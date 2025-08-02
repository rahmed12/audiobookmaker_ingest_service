-- Table: sentence_metadata
-- Purpose: Stores structured LLM/NLP classification results for a sentence: is it narration, dialogue, internal thought?
-- Also includes character, gender, emotion, and perspective metadata used for audio generation and timeline display.


CREATE TABLE IF NOT EXISTS sentence_metadata (
  sentence_id UUID PRIMARY KEY REFERENCES sentences(id),
  type TEXT,                   -- narration / dialogue / internal
  character TEXT,
  gender TEXT,
  age_group TEXT,
  emotion TEXT,
  confidence FLOAT,
  perspective TEXT
);