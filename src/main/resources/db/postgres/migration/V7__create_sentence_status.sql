-- Table: sentence_status
-- Purpose: Tracks processing status for each sentence: whether it’s classified, whether TTS audio is generated, and audio readiness.
-- Also tracks timing for LLM classification dispatch to prevent duplicate work.


CREATE TABLE IF NOT EXISTS sentence_status (
  sentence_id UUID PRIMARY KEY REFERENCES sentences(id),
  classification_status TEXT DEFAULT 'DONE',
  tts_status TEXT DEFAULT 'PENDING',
  audio_path TEXT,
  audio_ready BOOLEAN DEFAULT FALSE,
  llm_dispatched BOOLEAN DEFAULT TRUE,
  classification_dispatched_at TIMESTAMP
);