create sequence t_exposed_access_code_id start with 1 increment by 1 nocache;
create table t_exposed_access_code (
  id bigint not null default t_downloaded_access_code_id.nextval primary key,
  access_code varchar(12) not null,
  active boolean not null,
  onset_date date not null default now(),
  created_at timestamp not null default now(),
  expire_at timestamp not null,
  activated_at timestamp not null default now(),
  CONSTRAINT t_exposed_access_code_uk UNIQUE (access_code)
)