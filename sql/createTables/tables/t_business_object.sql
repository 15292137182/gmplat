DROP TABLE IF EXISTS t_business_object;


CREATE TABLE t_business_object (
  row_id varchar(70) NOT NULL,
  object_code varchar(64) NULL,
  object_name varchar(64) NULL,
  relate_table_row_id varchar(70) NULL,
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
  CONSTRAINT t_business_object_pkey PRIMARY KEY (row_id)
);

COMMENT ON TABLE t_business_object IS '业务对象';