DROP TABLE IF EXISTS t_base_org;

CREATE TABLE public.t_base_org (
  row_id           VARCHAR(64)  NOT NULL PRIMARY KEY,
  org_pid          VARCHAR(64)  NULL,
  org_id           VARCHAR(64)  NULL,
  org_name         VARCHAR(128) NULL,
  org_sort         VARCHAR(4)   NULL,
  org_level        VARCHAR(4)   NULL,
  fixed_phone      VARCHAR(32)  NULL,
  address          TEXT         NULL,
  desp             TEXT         NULL,

  etc              JSONB DEFAULT '{}',
  status           VARCHAR(8)   NULL,
  "version"        VARCHAR(16)  NULL,
  create_time      VARCHAR(32)  NULL,
  create_user      VARCHAR(16)  NULL,
  create_user_name VARCHAR(16)  NULL,
  modify_time      VARCHAR(32)  NULL,
  modify_user      VARCHAR(16)  NULL,
  modify_user_name VARCHAR(16)  NULL,
  delete_time      VARCHAR(32)  NULL,
  delete_user      VARCHAR(16)  NULL,
  delete_user_name VARCHAR(16)  NULL,
  delete_flag      VARCHAR(4)   NULL,
  belong_module    VARCHAR(32)  NULL,
  belong_system    VARCHAR(32)  NULL
);
COMMENT ON TABLE public.t_base_org IS '组织机构';