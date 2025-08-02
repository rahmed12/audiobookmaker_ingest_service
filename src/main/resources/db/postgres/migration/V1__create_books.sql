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
