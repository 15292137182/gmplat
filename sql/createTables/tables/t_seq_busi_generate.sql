DROP TABLE IF EXISTS t_seq_busi_generate;

CREATE TABLE t_seq_busi_generate (
  row_id varchar(70) NOT NULL,
  seq_row_id varchar(70) NULL,
  busi_variable_key varchar(64) NULL,
  current_value varchar(64) NULL,
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
  CONSTRAINT t_sequence_generate_pkey PRIMARY KEY (row_id)
);

COMMENT ON TABLE t_seq_busi_generate IS '业务序列值生成';