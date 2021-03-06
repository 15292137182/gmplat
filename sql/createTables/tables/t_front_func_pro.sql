DROP TABLE IF EXISTS t_front_func_pro;


CREATE TABLE public.t_front_func_pro (
  row_id           VARCHAR(70) NOT NULL,
  func_row_id      VARCHAR(70) NULL,
  relate_busi_pro  VARCHAR(70) NULL,
  display_title    VARCHAR(64) NULL,
  wether_display   VARCHAR(6)  NULL,
  display_widget   VARCHAR(32) NULL,
  wether_readonly  VARCHAR(6)  NULL,
  allow_empty      VARCHAR(6)  NULL,
  length_interval  VARCHAR(32) NULL,
  validate_func    TEXT        NULL,
  display_func     TEXT        NULL,
  sort             VARCHAR(8)  NULL,
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
  width_setting    VARCHAR(16) NULL,
  align            VARCHAR(16) NULL,
  exact_query      VARCHAR(5)  NULL,
  support_sort     VARCHAR(5)  NULL,
  keyword_one      VARCHAR(64) NULL,
  keyword_two      VARCHAR(64) NULL,
  keyword_three    VARCHAR(64) NULL,
  CONSTRAINT t_front_func_pro_pkey PRIMARY KEY (row_id)
);

COMMENT ON TABLE t_front_func_pro IS '功能块属性项';