DROP TABLE IF EXISTS t_template_object_pro;

CREATE TABLE public.t_template_object_pro (
  row_id varchar(70) NOT NULL,
  template_obj_row_id varchar(64) NULL,
  code varchar(64) NULL,
  cname varchar(64) NULL,
  ename varchar(8) NULL,
  value_type varchar(16) NULL,
  default_value varchar(32) NULL,
  desp varchar(16) NULL,
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
  CONSTRAINT t_template_object_copy_pkey PRIMARY KEY (row_id)
);

COMMENT ON TABLE t_template_object_pro IS '模板对象属性';