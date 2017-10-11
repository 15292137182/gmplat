DROP TABLE IF EXISTS public.t_page;

CREATE TABLE "public"."t_page" (
  "row_id"           VARCHAR(64) COLLATE "default" NOT NULL,
  "page_number"      VARCHAR(64) COLLATE "default",
  "page_name"        VARCHAR(128) COLLATE "default",
  "page_url"         TEXT COLLATE "default",
  "grant_auth"       VARCHAR(8) COLLATE "default",
  "belong_module"    VARCHAR(16) COLLATE "default",
  "remarks"          TEXT COLLATE "default",
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
  CONSTRAINT "t_page_pkey" PRIMARY KEY ("row_id")
);

ALTER TABLE "public"."t_page"
  OWNER TO "gmplat";

COMMENT ON TABLE public.t_page IS '页面资源';

COMMENT ON COLUMN "public"."t_page"."page_number" IS '页面编号';

COMMENT ON COLUMN "public"."t_page"."page_name" IS '页面明称';

COMMENT ON COLUMN "public"."t_page"."page_url" IS '页面url';

COMMENT ON COLUMN "public"."t_page"."grant_auth" IS '是否授权';

COMMENT ON COLUMN "public"."t_page"."belong_module" IS '所属模块';

COMMENT ON COLUMN "public"."t_page"."remarks" IS '备注';

COMMENT ON COLUMN "public"."t_page"."status" IS '状态';

COMMENT ON COLUMN "public"."t_page"."version" IS '版本';

COMMENT ON COLUMN "public"."t_page"."create_time" IS '创建时间';

COMMENT ON COLUMN "public"."t_page"."create_user" IS '创建人';

COMMENT ON COLUMN "public"."t_page"."create_user_name" IS '创建人名称';

COMMENT ON COLUMN "public"."t_page"."modify_time" IS '修改时间';

COMMENT ON COLUMN "public"."t_page"."modify_user" IS '修改人';

COMMENT ON COLUMN "public"."t_page"."modify_user_name" IS '修改人名称';

COMMENT ON COLUMN "public"."t_page"."delete_time" IS '删除时间';

COMMENT ON COLUMN "public"."t_page"."delete_user" IS '删除人';

COMMENT ON COLUMN "public"."t_page"."delete_user_name" IS '删除人名称';

COMMENT ON COLUMN "public"."t_page"."delete_flag" IS '删除标记';

COMMENT ON COLUMN "public"."t_page"."etc" IS '扩展字段';