DROP TABLE IF EXISTS t_business_object_pro;

CREATE TABLE t_business_object_pro (
  row_id varchar(70) NOT NULL,
  property_code varchar(64) NULL,
  property_name varchar(64) NULL,
  relate_table_column varchar(70) NULL,
  value_type varchar(8) NULL,
  value_resource_type varchar(8) NULL,
  value_resource_content text NULL,
  wether_expand_pro varchar(4) NULL,
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
  CONSTRAINT t_business_object_pro_pkey PRIMARY KEY (row_id)
);

COMMENT ON TABLE t_business_object_pro is'业务对象属性';