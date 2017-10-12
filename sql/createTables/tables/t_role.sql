DROP TABLE IF EXISTS public.t_role;

CREATE TABLE public.t_role (
  row_id          VARCHAR(64) NOT NULL PRIMARY KEY,

  role_id   VARCHAR(64) NOT NULL,
  role_name VARCHAR(128),
  role_type VARCHAR(8),
  "desc"          TEXT  DEFAULT '',
  remarks         TEXT,

  delete_flag     VARCHAR(4)  NULL,
  etc JSONB DEFAULT '{}' :: JSONB
);
ALTER TABLE "public"."t_role"
  OWNER TO "gmplat";

COMMENT ON TABLE public.t_role IS '角色表';