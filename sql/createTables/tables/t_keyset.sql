DROP TABLE IF EXISTS t_keyset;

CREATE TABLE "public"."t_keyset" (
  "row_id"           VARCHAR(70) COLLATE "default" NOT NULL,
  "keyset_code"      VARCHAR(64) COLLATE "default",
  "keyset_name"      VARCHAR(64) COLLATE "default",
  "desp"             TEXT COLLATE "default",
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
  "belong_mudule"    VARCHAR(16) COLLATE "default",
  "belong_system"    VARCHAR(16) COLLATE "default",
  CONSTRAINT "t_keyset_pkey" PRIMARY KEY ("row_id")
);
ALTER TABLE "public"."t_keyset"
  OWNER TO "gmplat";

COMMENT ON TABLE t_keyset IS '键值集合';

COMMENT ON COLUMN "public"."t_keyset"."belong_mudule" IS '所属模块';

COMMENT ON COLUMN "public"."t_keyset"."belong_system" IS '所属系统';
