CREATE TABLE t_exposed_access_code (
   id SERIAL PRIMARY KEY NOT NULL,
   access_code VARCHAR(12) NOT NULL,
   active BOOLEAN NOT NULL,
   onset_date DATE NOT NULL DEFAULT now(),
   created_at TIMESTAMP NOT NULL DEFAULT now(),
   expire_at TIMESTAMP NOT NULL,
   activated_at TIMESTAMP NOT NULL DEFAULT now()
);

CREATE UNIQUE INDEX t_exposed_access_code_uk ON t_exposed_access_code (access_code);