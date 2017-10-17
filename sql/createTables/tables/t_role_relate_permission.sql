DROP TABLE IF EXISTS public.t_role_relate_permission;

CREATE TABLE public.t_role_relate_permission (
  "row_id"            VARCHAR(64) NOT NULL PRIMARY KEY,
  "permission_row_id" VARCHAR(64),
  "role_row_id"       VARCHAR(64),

  delete_flag         VARCHAR(4),
  etc                 JSONB DEFAULT '{}' :: JSONB
);
COMMENT ON TABLE public.t_role_relate_permission IS '角色权限关联表';