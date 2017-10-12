DROP TABLE IF EXISTS public.t_permission;

CREATE TABLE public.t_permission (
  row_id          VARCHAR(70) NOT NULL PRIMARY KEY,

  permission_id   VARCHAR(32) NOT NULL,
  permission_name VARCHAR(64),
  permission_type VARCHAR(4),
  "desc"          TEXT  DEFAULT '',
  remarks         TEXT,

  etc             JSONB DEFAULT '{}',
  delete_flag     VARCHAR(4)  NULL

);
COMMENT ON TABLE public.t_permission IS '系统权限表';