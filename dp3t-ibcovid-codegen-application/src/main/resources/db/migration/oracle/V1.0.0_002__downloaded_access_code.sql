create sequence www_radarcov.rdv_downloaded_access_code_id start with 1 increment by 1 nocache;
create table www_radarcov.rdv_downloaded_access_code (
                                                 id number(19) not null,
                                                 access_code varchar2(12) not null,
                                                 created_at timestamp  default sysdate not null,
                                                 activated_at timestamp,
                                                 CONSTRAINT rdv_downloaded_access_code_pk PRIMARY KEY (id)
);