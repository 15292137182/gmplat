DROP TABLE IF EXISTS t_keyset;


CREATE TABLE t_keyset (
  row_id           VARCHAR(70) NOT NULL,
  number           VARCHAR(32) NULL,
  keyset_cod       VARCHAR(64) NULL,
  keyset_name      VARCHAR(64) NULL,
  conf_key         VARCHAR(64) NULL,
  conf_value       VARCHAR(64) NULL,
  desp             TEXT        NULL,
  status           VARCHAR(8)  NULL,
  "version"        VARCHAR(16) NULL,
  create_time      VARCHAR(32) NULL,
  create_user      VARCHAR(16) NULL,
  create_user_name VARCHAR(16) NULL,
  modify_time      VARCHAR(32) NULL,
  modify_user      VARCHAR(16) NULL,
  modify_user_name VARCHAR(16) NULL,
  delete_time      VARCHAR(32) NULL,
  delete_user      VARCHAR(16) NULL,
  delete_user_name VARCHAR(16) NULL,
  delete_flag      VARCHAR(4)  NULL,
  CONSTRAINT t_keyset_pkey PRIMARY KEY (row_id)
);

COMMENT ON TABLE t_keyset IS '键值集合';