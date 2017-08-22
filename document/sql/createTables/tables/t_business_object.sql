DROP TABLE IF EXISTS t_business_object;


CREATE TABLE t_business_object (
  row_id              VARCHAR(70) NOT NULL,
  object_code         VARCHAR(64) NULL,
  object_name         VARCHAR(64) NULL,
  relate_table_row_id VARCHAR(70) NULL,
  status              VARCHAR(8)  NULL,
  "version"           VARCHAR(16) NULL,
  create_time         VARCHAR(32) NULL,
  create_user         VARCHAR(16) NULL,
  create_user_name    VARCHAR(16) NULL,
  modify_time         VARCHAR(32) NULL,
  modify_user         VARCHAR(16) NULL,
  modify_user_name    VARCHAR(16) NULL,
  delete_time         VARCHAR(32) NULL,
  delete_user         VARCHAR(16) NULL,
  delete_user_name    VARCHAR(16) NULL,
  delete_flag         VARCHAR(4)  NULL,
  CONSTRAINT t_business_object_pkey PRIMARY KEY (row_id)
);

COMMENT ON TABLE t_business_object IS '业务对象';