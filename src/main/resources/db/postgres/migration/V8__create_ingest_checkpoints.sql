-- Table: ingest_checkpoints
-- Purpose: Tracks ingest progress per book. Allows resumable windowed classification (e.g., batch 1–100 complete, now do 101–200).
-- Useful for rolling window processing and chunk-based ingestion with retry logic.


CREATE TABLE IF NOT EXISTS ingest_checkpoints (
  id SERIAL PRIMARY KEY,
  book_id UUID NOT NULL REFERENCES books(id),
  user_id UUID NOT NULL,
  last_sentence_index INT DEFAULT 0,
  total_sentences INT,
  next_batch_start INT,
  status TEXT DEFAULT 'IN_PROGRESS',  -- IN_PROGRESS, COMPLETED, ERROR
  message TEXT,
  updated_at TIMESTAMP DEFAULT NOW(),
  created_at TIMESTAMP DEFAULT NOW()
);