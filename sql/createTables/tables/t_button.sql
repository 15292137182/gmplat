DROP TABLE IF EXISTS public.t_button;

CREATE TABLE "public"."t_button" (
  "row_id"           VARCHAR(64) COLLATE "default" NOT NULL,
  "page_ident"       VARCHAR(64) COLLATE "default",
  "button_number"    VARCHAR(64) COLLATE "default",
  "button_name"      VARCHAR(64) COLLATE "default",
  "avail"            VARCHAR(8) COLLATE "default",
  "grant_auth"       VARCHAR(8) COLLATE "default",
  "sort"             VARCHAR(8) COLLATE "default",
  "custom_style"     VARCHAR(64) COLLATE "default",
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
  CONSTRAINT "t_button_pkey" PRIMARY KEY ("row_id")
);

ALTER TABLE "public"."t_button"
  OWNER TO "gmplat";

COMMENT ON TABLE "public"."t_button" IS '按钮';

COMMENT ON COLUMN "public"."t_button"."row_id" IS '唯一标识';

COMMENT ON COLUMN "public"."t_button"."page_ident" IS '页面标识';

COMMENT ON COLUMN "public"."t_button"."button_number" IS '按钮编号';

COMMENT ON COLUMN "public"."t_button"."button_name" IS '按钮名称';

COMMENT ON COLUMN "public"."t_button"."avail" IS '是否可用';

COMMENT ON COLUMN "public"."t_button"."grant_auth" IS '是否授权';

COMMENT ON COLUMN "public"."t_button"."sort" IS '排序';

COMMENT ON COLUMN "public"."t_button"."custom_style" IS '自定义样式';

COMMENT ON COLUMN "public"."t_button"."remarks" IS '备注';

COMMENT ON COLUMN "public"."t_button"."status" IS '状态';

COMMENT ON COLUMN "public"."t_button"."version" IS '版本';

COMMENT ON COLUMN "public"."t_button"."create_time" IS '创建时间';

COMMENT ON COLUMN "public"."t_button"."create_user" IS '创建人';

COMMENT ON COLUMN "public"."t_button"."create_user_name" IS '创建人名称';

COMMENT ON COLUMN "public"."t_button"."modify_time" IS '修改时间';

COMMENT ON COLUMN "public"."t_button"."modify_user" IS '修改人';

COMMENT ON COLUMN "public"."t_button"."modify_user_name" IS '修改人名称';

COMMENT ON COLUMN "public"."t_button"."delete_time" IS '删除时间';

COMMENT ON COLUMN "public"."t_button"."delete_user" IS '删除人';

COMMENT ON COLUMN "public"."t_button"."delete_user_name" IS '删除人名称';

COMMENT ON COLUMN "public"."t_button"."delete_flag" IS '删除标记';

COMMENT ON COLUMN "public"."t_button"."etc" IS '扩展字段';