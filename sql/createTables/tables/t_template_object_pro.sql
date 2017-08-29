DROP TABLE IF EXISTS t_template_object_pro;

CREATE TABLE public.t_template_object_pro (
  row_id              VARCHAR(70) NOT NULL,
  template_obj_row_id VARCHAR(64) NULL,
  code                VARCHAR(64) NULL,
  cname               VARCHAR(64) NULL,
  ename               VARCHAR(8)  NULL,
  value_type          VARCHAR(16) NULL,
  default_value       VARCHAR(32) NULL,
  desp                VARCHAR(16) NULL,
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
  CONSTRAINT t_template_object_copy_pkey PRIMARY KEY (row_id)
);

COMMENT ON TABLE t_template_object_pro IS '模板对象属性';