DROP TABLE IF EXISTS public.t_user_group_relate_role;


CREATE TABLE "public"."t_user_group_relate_role" (
  "row_id"            VARCHAR(64) COLLATE "default" NOT NULL,
  "user_group_row_id" VARCHAR(64) COLLATE "default",
  "role_row_id"       VARCHAR(64) COLLATE "default",
  CONSTRAINT "t_user_group_relate_role_pkey" PRIMARY KEY ("row_id")
);

ALTER TABLE "public"."t_user_group_relate_role"
  OWNER TO "gmplat";

COMMENT ON TABLE "public"."t_user_group_relate_role" IS '用户组关联角色';

COMMENT ON COLUMN "public"."t_user_group_relate_role"."row_id" IS '唯一标识';

COMMENT ON COLUMN "public"."t_user_group_relate_role"."user_group_row_id" IS '用户组唯一标识';

COMMENT ON COLUMN "public"."t_user_group_relate_role"."role_row_id" IS '角色唯一标识';