DROP TABLE IF EXISTS public.t_user_group;

CREATE TABLE "public"."t_user_group" (
  "row_id"           VARCHAR(64) COLLATE "default" NOT NULL,
  "group_number"           VARCHAR(64) COLLATE "default",
  "group_name"             VARCHAR(64) COLLATE "default",
  "belong_sector"    VARCHAR(64) COLLATE "default",
  "group_category"   VARCHAR(32) COLLATE "default",
  "desc"             TEXT COLLATE "default",
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
  CONSTRAINT "t_user_group_pkey" PRIMARY KEY ("row_id")
);

ALTER TABLE "public"."t_user_group"
  OWNER TO "gmplat";

COMMENT ON TABLE "public"."t_user_group" IS '用户组';

COMMENT ON COLUMN "public"."t_user_group"."row_id" IS '唯一标识';

COMMENT ON COLUMN "public"."t_user_group"."number" IS '用户组编号';

COMMENT ON COLUMN "public"."t_user_group"."name" IS '用户组名称';

COMMENT ON COLUMN "public"."t_user_group"."belong_sector" IS '所属部门';

COMMENT ON COLUMN "public"."t_user_group"."group_category" IS '组类别(系统\用户)';

COMMENT ON COLUMN "public"."t_user_group"."desc" IS '描述';

COMMENT ON COLUMN "public"."t_user_group"."remarks" IS '备注';

COMMENT ON COLUMN "public"."t_user_group"."status" IS '状态';

COMMENT ON COLUMN "public"."t_user_group"."version" IS '版本';

COMMENT ON COLUMN "public"."t_user_group"."create_time" IS '创建时间';

COMMENT ON COLUMN "public"."t_user_group"."create_user" IS '创建人';

COMMENT ON COLUMN "public"."t_user_group"."create_user_name" IS '创建人名称';

COMMENT ON COLUMN "public"."t_user_group"."modify_time" IS '修改时间';

COMMENT ON COLUMN "public"."t_user_group"."modify_user" IS '修改人';

COMMENT ON COLUMN "public"."t_user_group"."modify_user_name" IS '修改人名称';

COMMENT ON COLUMN "public"."t_user_group"."delete_time" IS '删除时间';

COMMENT ON COLUMN "public"."t_user_group"."delete_user" IS '删除人';

COMMENT ON COLUMN "public"."t_user_group"."delete_user_name" IS '删除人名称';

COMMENT ON COLUMN "public"."t_user_group"."delete_flag" IS '删除标记';

COMMENT ON COLUMN "public"."t_user_group"."etc" IS '扩展字段';