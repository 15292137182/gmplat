DROP TABLE IF EXISTS "public"."t_user";

CREATE TABLE public.t_user(
	row_id VARCHAR(64) NOT NULL,
	id VARCHAR(32) NULL,
	name VARCHAR(32) NULL,
	nickname VARCHAR(64) NULL,
	password VARCHAR(32) NULL,
	status VARCHAR(8) NULL,
	belong_org VARCHAR(64) NULL,
	id_card VARCHAR(32) NULL,
	mobile_phone VARCHAR(16) NULL,
	office_phone VARCHAR(16) NULL,
	email VARCHAR(64) NULL,
	gender VARCHAR(8) NULL,
	job VARCHAR(64) NULL,
	hiredate VARCHAR(32) NULL,
	password_update_time VARCHAR(32) NULL,
	last_login_time VARCHAR(32) NULL,
	account_locked_time VARCHAR(32) NULL,
	description TEXT NULL,
	remarks TEXT NULL,
	delete_flag varchar(4) NULL,
	etc JSONB DEFAULT '{}' :: JSONB,
	CONSTRAINT t_user_pkey PRIMARY KEY(row_id)
);

ALTER TABLE "public"."t_user"
  OWNER TO "gmplat";

COMMENT ON TABLE "public"."t_user" IS '人员信息';

COMMENT ON COLUMN "public"."t_user"."row_id" IS '唯一标识';

COMMENT ON COLUMN "public"."t_user"."id" IS '用户工号';

COMMENT ON COLUMN "public"."t_user"."name" IS '用户姓名';

COMMENT ON COLUMN "public"."t_user"."nickname" IS '用户昵称';

COMMENT ON COLUMN "public"."t_user"."password" IS '密码';

COMMENT ON COLUMN "public"."t_user"."status" IS '状态';

COMMENT ON COLUMN "public"."t_user"."belong_org" IS '所属部门';

COMMENT ON COLUMN "public"."t_user"."id_card" IS '身份证';

COMMENT ON COLUMN "public"."t_user"."mobile_phone" IS '移动电话';

COMMENT ON COLUMN "public"."t_user"."office_phone" IS '办公电话';

COMMENT ON COLUMN "public"."t_user"."email" IS '邮箱';

COMMENT ON COLUMN "public"."t_user"."gender" IS '性别';

COMMENT ON COLUMN "public"."t_user"."job" IS '职务';

COMMENT ON COLUMN "public"."t_user"."hiredate" IS '入职日期';

COMMENT ON COLUMN "public"."t_user"."password_update_time" IS '密码更新时间';

COMMENT ON COLUMN "public"."t_user"."last_login_time" IS '上次登录时间';

COMMENT ON COLUMN "public"."t_user"."account_locked_time" IS '账号锁定时间';

COMMENT ON COLUMN "public"."t_user"."description" IS '说明';

COMMENT ON COLUMN "public"."t_user"."remarks" IS '备注';

COMMENT ON COLUMN "public"."t_user"."delete_flag" IS '删除标记';

COMMENT ON COLUMN "public"."t_user"."etc" IS '扩展字段';