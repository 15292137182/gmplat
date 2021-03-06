DROP TABLE IF EXISTS t_sequence_generate;

CREATE TABLE t_sequence_generate (
  row_id           VARCHAR(70)  NOT NULL,
  seq_row_id       VARCHAR(70)  NULL,
  variable_key     VARCHAR(64)  NULL,
  current_value    VARCHAR(64)  NULL,
  branch_sign      VARCHAR(128) NULL,
  object_signs     VARCHAR(128) NULL,
  status           VARCHAR(8)   NULL,
  "version"        VARCHAR(16)  NULL,
  create_time      VARCHAR(32)  NULL,
  create_user      VARCHAR(16)  NULL,
  create_user_name VARCHAR(16)  NULL,
  modify_time      VARCHAR(32)  NULL,
  modify_user      VARCHAR(16)  NULL,
  modify_user_name VARCHAR(16)  NULL,
  delete_time      VARCHAR(32)  NULL,
  delete_user      VARCHAR(16)  NULL,
  delete_user_name VARCHAR(16)  NULL,
  delete_flag      VARCHAR(4)   NULL,
  CONSTRAINT t_sequence_generate_pkey1 PRIMARY KEY (row_id)
);


COMMENT ON TABLE t_sequence_generate IS '序列号生成';