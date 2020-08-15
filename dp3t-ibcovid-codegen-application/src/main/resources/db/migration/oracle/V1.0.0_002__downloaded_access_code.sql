create sequence t_downloaded_access_code_id start with 1 increment by 1 nocache;
create table t_downloaded_access_code (
  id bigint not null default t_downloaded_access_code_id.nextval primary key,
  access_code varchar(12) not null,
  created_at timestamp not null default now(),
  activated_at timestamp
);

CREATE UNIQUE INDEX t_downloaded_access_code_uk ON t_downloaded_access_code (access_code);