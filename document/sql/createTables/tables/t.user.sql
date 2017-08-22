DROP TABLE IF EXISTS public.t_user;

CREATE TABLE public.t_user (
  row_id               VARCHAR(70)            NOT NULL PRIMARY KEY,

  id                   VARCHAR(32) DEFAULT '' NOT NULL,
  name                 VARCHAR(64)  DEFAULT '',
  portrait_path        VARCHAR(256) DEFAULT '',
  password             VARCHAR(64)  DEFAULT '',
  sex                  VARCHAR(4)   DEFAULT '',
  deptno               VARCHAR(8)   DEFAULT '',
  mail                 VARCHAR(128) DEFAULT '',
  phone                VARCHAR(32)  DEFAULT '',
  type                 VARCHAR(8)   DEFAULT '',
  "limit"              VARCHAR(3)   DEFAULT '',
  last_login_date      VARCHAR(32)  DEFAULT '',
  last_modify_password VARCHAR(32)  DEFAULT '',
  disabled             VARCHAR(4)   DEFAULT '',

  STATUS               VARCHAR(8)             NULL,
  "version"            VARCHAR(16)            NULL,
  create_time          VARCHAR(32)            NULL,
  create_user          VARCHAR(16)            NULL,
  create_user_name     VARCHAR(16)            NULL,
  modify_time          VARCHAR(32)            NULL,
  modify_user          VARCHAR(16)            NULL,
  modify_user_name     VARCHAR(16)            NULL,
  delete_time          VARCHAR(32)            NULL,
  delete_user          VARCHAR(16)            NULL,
  delete_user_name     VARCHAR(16)            NULL,
  delete_flag          VARCHAR(4)             NULL
);

COMMENT ON TABLE public.t_user IS '平台用户表';