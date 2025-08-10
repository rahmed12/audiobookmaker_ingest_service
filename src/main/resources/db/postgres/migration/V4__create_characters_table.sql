-- =====================================================================
-- TABLE: character
-- PURPOSE: Canonical speakers per book (for stable voice assignment).
-- WRITES:  spacy-llm (resolver) or admin tools
-- READS:   spacy-llm when linking segments; audio pipeline for voices
-- NOTES:   Store optional voice_profile JSON (engine presets, accent).
-- IDs:     id (UUID) set by application (R2DBC).
-- =====================================================================

CREATE TABLE characters (
  id              UUID PRIMARY KEY,                     -- app must set
  book_id         UUID NOT NULL REFERENCES book(id) ON DELETE CASCADE,
  canonical_name  TEXT NOT NULL,                        -- e.g., "Jim Halpert"
  gender          TEXT DEFAULT 'unknown'
                     CHECK (gender IN ('male','female','unknown')),
  age_group       TEXT DEFAULT 'unknown'
                     CHECK (age_group IN ('child','adult','senior','unknown')),
  voice_profile   JSONB,
  created_at      TIMESTAMPTZ NOT NULL DEFAULT NOW(),
  UNIQUE (book_id, canonical_name)
);

CREATE INDEX idx_character_book ON character(book_id);
