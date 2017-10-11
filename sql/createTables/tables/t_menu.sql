DROP TABLE IF EXISTS "public"."t_menu";


CREATE TABLE "public"."t_menu" (
  "row_id"           VARCHAR(64) COLLATE "default" NOT NULL,
  "parent_number"    VARCHAR(64) COLLATE "default",
  "number"           VARCHAR(64) COLLATE "default",
  "name"             VARCHAR(128) COLLATE "default",
  "category"         VARCHAR(8) COLLATE "default",
  "url"              TEXT COLLATE "default",
  "sort"             VARCHAR(4) COLLATE "default",
  "grant_auth"       VARCHAR(4) COLLATE "default",
  "avail"            VARCHAR(4) COLLATE "default",
  "icon"             VARCHAR(64) COLLATE "default",
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
  CONSTRAINT "t_menu_pkey" PRIMARY KEY ("row_id")
);

ALTER TABLE "public"."t_menu"
  OWNER TO "gmplat";

COMMENT ON TABLE public.t_menu IS '菜单';

COMMENT ON TABLE "public"."t_menu" IS '菜单';

COMMENT ON COLUMN "public"."t_menu"."row_id" IS '唯一标识';

COMMENT ON COLUMN "public"."t_menu"."parent_number" IS '菜单父编号';

COMMENT ON COLUMN "public"."t_menu"."number" IS '菜单编号';

COMMENT ON COLUMN "public"."t_menu"."name" IS '菜单名称';

COMMENT ON COLUMN "public"."t_menu"."category" IS '菜单类别';

COMMENT ON COLUMN "public"."t_menu"."url" IS '菜单URL';

COMMENT ON COLUMN "public"."t_menu"."sort" IS '菜单排序';

COMMENT ON COLUMN "public"."t_menu"."grant_auth" IS '是否授权';

COMMENT ON COLUMN "public"."t_menu"."avail" IS '是否可用';

COMMENT ON COLUMN "public"."t_menu"."icon" IS '菜单图标';

COMMENT ON COLUMN "public"."t_menu"."remarks" IS '备注';

COMMENT ON COLUMN "public"."t_menu"."status" IS '状态';

COMMENT ON COLUMN "public"."t_menu"."version" IS '版本';

COMMENT ON COLUMN "public"."t_menu"."create_time" IS '创建时间';

COMMENT ON COLUMN "public"."t_menu"."create_user" IS '创建人';

COMMENT ON COLUMN "public"."t_menu"."create_user_name" IS '创建人名称';

COMMENT ON COLUMN "public"."t_menu"."modify_time" IS '修改时间';

COMMENT ON COLUMN "public"."t_menu"."modify_user" IS '修改人';

COMMENT ON COLUMN "public"."t_menu"."modify_user_name" IS '修改人名称';

COMMENT ON COLUMN "public"."t_menu"."delete_time" IS '删除时间';

COMMENT ON COLUMN "public"."t_menu"."delete_user" IS '删除人';

COMMENT ON COLUMN "public"."t_menu"."delete_user_name" IS '删除人名称';

COMMENT ON COLUMN "public"."t_menu"."delete_flag" IS '删除标记';

COMMENT ON COLUMN "public"."t_menu"."etc" IS '扩展字段';