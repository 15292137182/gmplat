DROP TABLE IF EXISTS public.t_func_operat;

CREATE TABLE "public"."t_func_operat" (
  "row_id"           VARCHAR(64) COLLATE "default" NOT NULL,
  "operat_number"    VARCHAR(64) COLLATE "default",
  "operat_name"      VARCHAR(128) COLLATE "default",
  "intercept_url"    TEXT COLLATE "default",
  "avail"            VARCHAR(8) COLLATE "default",
  "desc"             TEXT COLLATE "default",
  "status"           VARCHAR(8) COLLATE "default",
  "version"          VARCHAR(16) COLLATE "default",
  "create_time"      VARCHAR(32) COLLATE "default",
  "create_user"      VARCHAR(16) COLLATE "default",
  "create_user_name" VARCHAR(16) COLLATE "default",
  "modify_time"      VARCHAR(32) COLLATE "default",
  "modify_user"      VARCHAR(16) COLLATE "default",
  "modify_user_name" VARCHAR(16) COLLATE "default",
  "delete_time"      VARCHAR(32) COLLATE "default",
  "delete_user"      VARCHAR(16) COLLATE "default",
  "delete_user_name" VARCHAR(16) COLLATE "default",
  "delete_flag"      VARCHAR(4) COLLATE "default",
  "etc"              JSONB DEFAULT '{}' :: JSONB,
  CONSTRAINT "t_func_operat_pkey" PRIMARY KEY ("row_id")
);

ALTER TABLE "public"."t_func_operat"
  OWNER TO "gmplat";

COMMENT ON TABLE "public"."t_func_operat" IS '功能操作';

COMMENT ON COLUMN "public"."t_func_operat"."row_id" IS '唯一标识';

COMMENT ON COLUMN "public"."t_func_operat"."operat_number" IS '操作编号';

COMMENT ON COLUMN "public"."t_func_operat"."operat_name" IS '操作名称';

COMMENT ON COLUMN "public"."t_func_operat"."intercept_url" IS '拦截url';

COMMENT ON COLUMN "public"."t_func_operat"."avail" IS '是否可用';

COMMENT ON COLUMN "public"."t_func_operat"."desc" IS '描述';

COMMENT ON COLUMN "public"."t_func_operat"."status" IS '状态';

COMMENT ON COLUMN "public"."t_func_operat"."version" IS '版本';

COMMENT ON COLUMN "public"."t_func_operat"."create_time" IS '创建时间';

COMMENT ON COLUMN "public"."t_func_operat"."create_user" IS '创建人';

COMMENT ON COLUMN "public"."t_func_operat"."create_user_name" IS '创建人名称';

COMMENT ON COLUMN "public"."t_func_operat"."modify_time" IS '修改时间';

COMMENT ON COLUMN "public"."t_func_operat"."modify_user" IS '修改人';

COMMENT ON COLUMN "public"."t_func_operat"."modify_user_name" IS '修改人名称';

COMMENT ON COLUMN "public"."t_func_operat"."delete_time" IS '删除时间';

COMMENT ON COLUMN "public"."t_func_operat"."delete_user" IS '删除人';

COMMENT ON COLUMN "public"."t_func_operat"."delete_user_name" IS '删除人名称';

COMMENT ON COLUMN "public"."t_func_operat"."delete_flag" IS '删除标记';

COMMENT ON COLUMN "public"."t_func_operat"."etc" IS '扩展字段';