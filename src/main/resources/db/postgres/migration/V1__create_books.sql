-- Table: books
-- Purpose: Represents an uploaded PDF or TXT file by a user. Tracks metadata such as title, upload path, and user ownership.
-- Each book will be parsed into sentences, which will be stored in the `sentences` table and linked via book_id.


CREATE TABLE IF NOT EXISTS books (
  id UUID PRIMARY KEY,
  user_id UUID NOT NULL,
  title TEXT,
  original_filename TEXT,
  language_code TEXT,
  priority_level TEXT,
  upload_path TEXT,
  plain_text_path TEXT,
  created_at TIMESTAMP DEFAULT NOW(),
  deleted_at TIMESTAMP
);
