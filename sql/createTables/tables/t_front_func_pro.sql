DROP TABLE  IF EXISTS t_front_func_pro;


CREATE TABLE t_front_func_pro (
  row_id varchar(70) NOT NULL,
  func_row_id varchar(70) NULL,
  relate_busi_pro varchar(70) NULL,
  display_title varchar(64) NULL,
  wether_display varchar(4) NULL,
  display_widget varchar(32) NULL,
  wether_readonly varchar(4) NULL,
  allow_empty varchar(4) NULL,
  length_interval varchar(32) NULL,
  validate_func text NULL,
  display_func text NULL,
  sort varchar(8) NULL,
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
  CONSTRAINT t_front_func_pro_pkey PRIMARY KEY (row_id)
);

COMMENT ON TABLE t_front_func_pro is '功能块属性项';