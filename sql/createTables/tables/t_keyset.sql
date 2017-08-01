DROP TABLE IF EXISTS t_keyset;


CREATE TABLE t_keyset (
  row_id varchar(70) NOT NULL,
  keyset_cod varchar(64) NULL,
  keyset_name varchar(64) NULL,
  conf_key varchar(64) NULL,
  conf_value varchar(64) NULL,
  desp text NULL,
  status varchar(8) NULL,
  "version" varchar(16) NULL,
  create_time varchar(32) NULL,
  create_user varchar(16) NULL,
  create_user_name varchar(16) NULL,
  modify_time varchar(32) NULL,
  modify_user varchar(16) NULL,
  modify_user_name varchar(16) NULL,
  delete_time varchar(32) NULL,
  delete_user varchar(16) NULL,
  delete_user_name varchar(16) NULL,
  delete_flag varchar(4) NULL,
  CONSTRAINT t_keyset_pkey PRIMARY KEY (row_id)
);

comment ON TABLE t_keyset IS '键值集合';