DROP TABLE IF EXISTS t_keyset_pro;


CREATE TABLE t_keyset_pro (
  row_id               VARCHAR(70) NOT NULL,
  relate_keyset_row_id VARCHAR(70) NULL,
  conf_key             VARCHAR(64) NULL,
  conf_value           VARCHAR(64) NULL,
  desp                 TEXT        NULL,
  status               VARCHAR(8)  NULL,
  "version"            VARCHAR(16) NULL,
  create_time          VARCHAR(32) NULL,
  create_user          VARCHAR(16) NULL,
  create_user_name     VARCHAR(16) NULL,
  modify_time          VARCHAR(32) NULL,
  modify_user          VARCHAR(16) NULL,
  modify_user_name     VARCHAR(16) NULL,
  delete_time          VARCHAR(32) NULL,
  delete_user          VARCHAR(16) NULL,
  delete_user_name     VARCHAR(16) NULL,
  delete_flag          VARCHAR(4)  NULL,
  CONSTRAINT t_keyset_copy_pkey PRIMARY KEY (row_id)
);

COMMENT ON TABLE t_keyset_pro IS '键值集合明细';