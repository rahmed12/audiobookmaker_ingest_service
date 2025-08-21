-- =====================================================================
-- TABLE: book
-- PURPOSE: One row per uploaded book. Stores storage keys and pipeline
--          barriers (text ready, segments ready).
-- OWNER:   ingest-service (create/update to TEXT_READY), langextract-service (EXTRACTING→COMPLETE).
-- IDS:     UUID set by app code (R2DBC).
-- =====================================================================

CREATE TABLE IF NOT EXISTS books (
  id                 UUID PRIMARY KEY,
  user_id            UUID NOT NULL,
  title              TEXT,

  -- canonical locations
  raw_uri            TEXT,                 -- where raw upload is stored
  text_uri           TEXT,                 -- where extracted .txt is stored

  -- helpful metadata about the text
  text_sha256        CHAR(64),
  text_length        INT,

  status             TEXT,
  text_ready_at      TIMESTAMPTZ,
  segments_ready_at  TIMESTAMPTZ,
  created_at         TIMESTAMPTZ,
  updated_at         TIMESTAMPTZ 
);
CREATE INDEX IF NOT EXISTS idx_book_status ON books(status);
CREATE INDEX IF NOT EXISTS idx_book_user   ON books(user_id);

