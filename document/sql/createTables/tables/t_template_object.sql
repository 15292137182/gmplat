DROP TABLE IF EXISTS t_template_object;

CREATE TABLE public.t_template_object (
  row_id varchar(70) NOT NULL,
  template_code varchar(64) NULL,
  template_name varchar(64) NULL,
  desp varchar(64) NULL,
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
  CONSTRAINT t_template_object_pkey PRIMARY KEY (row_id)
);

COMMENT ON TABLE t_template_object IS '模板对象';