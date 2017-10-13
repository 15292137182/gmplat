DROP TABLE IF EXISTS t_user_relate_role;

CREATE TABLE "public"."t_user_relate_role" (
  "row_id"          VARCHAR(64) COLLATE "default" NOT NULL,
  "user_row_id" VARCHAR(64) COLLATE "default",
  "role_row_id" VARCHAR(64) COLLATE "default",

  delete_flag     VARCHAR(4)  NULL,
  etc JSONB DEFAULT '{}' :: JSONB,
  CONSTRAINT "t_user_relate_role_pkey" PRIMARY KEY ("row_id")
);

ALTER TABLE "public"."t_user_relate_role"
  OWNER TO "gmplat";

COMMENT ON TABLE "public"."t_user_relate_role" IS '用户与角色关联表';

COMMENT ON COLUMN "public"."t_user_relate_role"."row_id" IS '唯一标示';

COMMENT ON COLUMN "public"."t_user_relate_role"."user_row_id" IS '人员信息唯一标示';

COMMENT ON COLUMN "public"."t_user_relate_role"."role_row_id" IS '角色唯一标识';