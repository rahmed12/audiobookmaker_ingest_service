-- =====================================================================
-- TABLE: character
-- PURPOSE: Canonical speaker per book (stable voice assignment).
-- OWNER:   langextract-service / admin UI.
-- IDS:     UUID set by app code (R2DBC).
-- =====================================================================
CREATE TABLE IF NOT EXISTS characters (
  id              UUID PRIMARY KEY,                     -- app must set
  book_id         UUID NOT NULL REFERENCES books(id) ON DELETE CASCADE,
  canonical_name  TEXT NOT NULL,                        -- e.g., "Jim Halpert"
  gender          TEXT DEFAULT 'unknown'
                     CHECK (gender IN ('male','female','nonbinary','unknown')),
  age_group       TEXT DEFAULT 'unknown'
                     CHECK (age_group IN ('child','teen','adult','senior','unknown')),
  voice_profile   JSONB,                                -- TTS preset, accent, etc.
  created_at      TIMESTAMPTZ NOT NULL DEFAULT NOW(),
  UNIQUE (book_id, canonical_name)
);
CREATE INDEX IF NOT EXISTS idx_character_book ON characters(book_id);
