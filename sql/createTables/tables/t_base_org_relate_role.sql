DROP TABLE IF EXISTS t_base_org_relate_role;

CREATE TABLE "public"."t_base_org_relate_role" (
  "row_id"          VARCHAR(64) COLLATE "default" NOT NULL,
  "base_org_row_id" VARCHAR(64) COLLATE "default",
  "role_row_id" VARCHAR(64) COLLATE "default",

  etc JSONB DEFAULT '{}' :: JSONB,
  CONSTRAINT "t_base_org_relate_role_pkey" PRIMARY KEY ("row_id")
);

ALTER TABLE "public"."t_base_org_relate_role"
  OWNER TO "gmplat";

COMMENT ON TABLE "public"."t_base_org_relate_role" IS '组织机构与角色关联表';

COMMENT ON COLUMN "public"."t_base_org_relate_role"."row_id" IS '唯一标示';

COMMENT ON COLUMN "public"."t_base_org_relate_role"."base_org_row_id" IS '组织机构唯一标示';

COMMENT ON COLUMN "public"."t_base_org_relate_role"."role_row_id" IS '角色唯一标识';