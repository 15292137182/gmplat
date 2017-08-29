DROP TABLE IF EXISTS t_template_object;

CREATE TABLE public.t_template_object (
  row_id           VARCHAR(70) NOT NULL,
  template_code    VARCHAR(64) NULL,
  template_name    VARCHAR(64) NULL,
  desp             VARCHAR(64) NULL,
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
  belong_module    VARCHAR(32) NULL,
  belong_system    VARCHAR(32) NULL,
  CONSTRAINT t_template_object_pkey PRIMARY KEY (row_id)
);

COMMENT ON TABLE t_template_object IS '模板对象';