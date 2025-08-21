-- =====================================================================
-- TABLE: extract_state
-- PURPOSE: Single checkpoint row per book for progress & resume.
-- OWNER:   langextract-service (UPSERT each successful chunk).
-- =====================================================================
CREATE TABLE IF NOT EXISTS extract_state (
  book_id          UUID PRIMARY KEY REFERENCES books(id) ON DELETE CASCADE,
  next_char_offset INT  NOT NULL DEFAULT 0,     -- resume position in TXT
  pass_no          INT  NOT NULL DEFAULT 1,     -- current/next pass number
  chunk_index      INT  NOT NULL DEFAULT 0,     -- chunk within the pass
  jsonl_key        TEXT,                        -- where JSONL is written (optional)
  status           TEXT NOT NULL DEFAULT 'PENDING'
                     CHECK (status IN ('PENDING','RUNNING','DONE','FAILED')),
  last_error       TEXT,
  updated_at       TIMESTAMPTZ NOT NULL DEFAULT NOW()
);
