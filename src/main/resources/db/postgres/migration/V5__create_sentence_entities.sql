-- Table: sentence_entities
-- Purpose: Stores named entities extracted from the sentence (PERSON, ORG, GPE, etc.)
-- Used to track who/what appears in a sentence and help prompt routing, context detection, or grounding.


CREATE TABLE IF NOT EXISTS sentence_entities (
  id SERIAL PRIMARY KEY,
  sentence_id UUID REFERENCES sentences(id),
  entity_text TEXT,
  entity_type TEXT,            -- PERSON, ORG, GPE
  start_token INT,
  end_token INT
);