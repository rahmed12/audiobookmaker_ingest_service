-- Table: sentence_nlp_tokens
-- Purpose: Stores the result of full tokenization and linguistic analysis (via spaCy).
-- Used to understand sentence structure, subject/verb/objects, dependencies, and part-of-speech.


CREATE TABLE IF NOT EXISTS sentence_nlp_tokens (
  id SERIAL PRIMARY KEY,
  sentence_id UUID REFERENCES sentences(id),
  token_order INT,
  text TEXT,
  lemma TEXT,
  pos TEXT,
  tag TEXT,
  dep TEXT,
  ent_type TEXT
);