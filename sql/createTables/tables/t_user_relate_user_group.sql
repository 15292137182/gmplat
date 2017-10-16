DROP TABLE IF EXISTS public.t_user_relate_user_group;

CREATE TABLE "public"."t_user_relate_user_group" (
  "row_id"            VARCHAR(64) COLLATE "default" NOT NULL,
  "user_row_id"       VARCHAR(64) COLLATE "default",
  "user_group_row_id" VARCHAR(64) COLLATE "default",
  CONSTRAINT "t_user_relate_user_group_pkey" PRIMARY KEY ("row_id")
);

ALTER TABLE "public"."t_user_relate_user_group"
  OWNER TO "gmplat";

COMMENT ON TABLE "public"."t_user_relate_user_group" IS '用户关联用户组';

COMMENT ON COLUMN "public"."t_user_relate_user_group"."row_id" IS '唯一标示';

COMMENT ON COLUMN "public"."t_user_relate_user_group"."user_row_id" IS '用户唯一标示';

COMMENT ON COLUMN "public"."t_user_relate_user_group"."user_group_row_id" IS '用户组唯一标示';