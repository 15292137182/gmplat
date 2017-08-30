DROP TABLE IF EXISTS t_template_record;

CREATE TABLE "public"."t_template_record" (
  "row_id"              VARCHAR(64) COLLATE "default" NOT NULL,
  "relate_busin_row_id" VARCHAR(64) COLLATE "default",
  "template_data"       VARCHAR(64) COLLATE "default",
  CONSTRAINT "t_template_record_pkey" PRIMARY KEY ("row_id")
);

ALTER TABLE "public"."t_template_record"
  OWNER TO "gmplat";

COMMENT ON COLUMN "public"."t_template_record"."row_id" IS '唯一标识';

COMMENT ON COLUMN "public"."t_template_record"."relate_busin_row_id" IS '关联业务对象唯一标识';

COMMENT ON COLUMN "public"."t_template_record"."template_data" IS '模板数据';