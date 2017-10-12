DROP TABLE IF EXISTS public.t_permission;

CREATE TABLE public.t_permission (
  row_id           VARCHAR(70) NOT NULL PRIMARY KEY,

  "key"            VARCHAR(32) NOT NULL,
  name             VARCHAR(32) DEFAULT '',
  value            VARCHAR(64) DEFAULT '',
  remark           VARCHAR(64) DEFAULT '',

  etc              JSONB       DEFAULT '{}',
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
  belong_system    VARCHAR(32) NULL
);
COMMENT ON TABLE public.t_permission IS '系统权限表';