CREATE TABLE t_downloaded_access_code (
  id SERIAL PRIMARY KEY NOT NULL,
  access_code VARCHAR(12) NOT NULL,
  created_at TIMESTAMP NOT NULL DEFAULT now(),
  activated_at TIMESTAMP
);

CREATE UNIQUE INDEX t_downloaded_access_code_uk ON t_downloaded_access_code (access_code);