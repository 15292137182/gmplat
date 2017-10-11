DROP TABLE IF EXISTS "public"."t_employee";


create table public.t_employee(
	row_id VARCHAR(64) NOT NULL,
	employee_no VARCHAR(32) NULL,
	employee_name VARCHAR(32) NULL,
	employee_nickname VARCHAR(64) NULL,
	password VARCHAR(32) NULL,
	status VARCHAR(8) NULL,
	belong_organization VARCHAR(64) NULL,
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
	CONSTRAINT t_employee_pkey PRIMARY KEY(row_id)
);

ALTER TABLE "public"."t_employee"
  OWNER TO "gmplat";

COMMENT ON TABLE "public"."t_employee" IS '人员信息';

COMMENT ON COLUMN "public"."t_employee"."row_id" IS '唯一标识';

COMMENT ON COLUMN "public"."t_employee"."employee_no" IS '用户工号';

COMMENT ON COLUMN "public"."t_employee"."employee_name" IS '用户姓名';

COMMENT ON COLUMN "public"."t_employee"."employee_nickname" IS '用户昵称';

COMMENT ON COLUMN "public"."t_employee"."password" IS '密码';

COMMENT ON COLUMN "public"."t_employee"."status" IS '状态';

COMMENT ON COLUMN "public"."t_employee"."belong_organization" IS '所属部门';

COMMENT ON COLUMN "public"."t_employee"."id_card" IS '身份证';

COMMENT ON COLUMN "public"."t_employee"."mobile_phone" IS '移动电话';

COMMENT ON COLUMN "public"."t_employee"."office_phone" IS '办公电话';

COMMENT ON COLUMN "public"."t_employee"."email" IS '邮箱';

COMMENT ON COLUMN "public"."t_employee"."gender" IS '性别';

COMMENT ON COLUMN "public"."t_employee"."job" IS '职务';

COMMENT ON COLUMN "public"."t_employee"."hiredate" IS '入职日期';

COMMENT ON COLUMN "public"."t_employee"."password_update_time" IS '密码更新时间';

COMMENT ON COLUMN "public"."t_employee"."last_login_time" IS '上次登录时间';

COMMENT ON COLUMN "public"."t_employee"."account_locked_time" IS '账号锁定时间';

COMMENT ON COLUMN "public"."t_employee"."description" IS '说明';

COMMENT ON COLUMN "public"."t_employee"."remarks" IS '备注';

COMMENT ON COLUMN "public"."t_employee"."delete_flag" IS '删除标记';

COMMENT ON COLUMN "public"."t_employee"."etc" IS '扩展字段';