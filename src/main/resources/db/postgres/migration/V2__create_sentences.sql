-- Table: sentences
-- Purpose: Stores every parsed sentence from a book. Each sentence links to a book and has optional embedding or revision info.
-- These are the primary units used for classification, voice assignment, TTS, and downstream NLP processing.


CREATE TABLE IF NOT EXISTS sentences (
  id UUID PRIMARY KEY,
  book_id UUID NOT NULL REFERENCES books(id),
  sentence_order INT,
  text TEXT NOT NULL,
  embedding vector(384),
  is_orphaned BOOLEAN DEFAULT FALSE,
  revision_of UUID,
  created_at TIMESTAMP DEFAULT NOW()
);