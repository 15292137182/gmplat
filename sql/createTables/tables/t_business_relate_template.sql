DROP TABLE IF EXISTS t_business_relate_template;

CREATE TABLE "public"."t_business_relate_template" (
  "row_id"          VARCHAR(64) COLLATE "default" NOT NULL,
  "business_row_id" VARCHAR(64) COLLATE "default",
  "template_row_id" VARCHAR(64) COLLATE "default",
  CONSTRAINT "t_business_relate_template_pkey" PRIMARY KEY ("row_id")
);

ALTER TABLE "public"."t_business_relate_template"
  OWNER TO "gmplat";

COMMENT ON COLUMN "public"."t_business_relate_template"."row_id" IS '唯一标示';

COMMENT ON COLUMN "public"."t_business_relate_template"."business_row_id" IS '业务对象唯一标示';

COMMENT ON COLUMN "public"."t_business_relate_template"."template_row_id" IS '模板对象唯一标识';