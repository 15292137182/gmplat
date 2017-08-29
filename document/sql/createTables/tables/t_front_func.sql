DROP TABLE IF EXISTS t_front_func;

CREATE TABLE public.t_front_func (
	row_id           VARCHAR(70) NOT NULL,
	func_code        VARCHAR(64) NULL,
	func_name        VARCHAR(64) NULL,
	func_type        VARCHAR(8)  NULL,
	relate_busi_obj  VARCHAR(70) NULL,
	wether_available VARCHAR(4)  NULL,
	desp             TEXT        NULL,
	status           VARCHAR(8)  NULL,
	"version"        VARCHAR(16) NULL,
	create_time      VARCHAR(32) NULL,
	create_user      VARCHAR(16) NULL,
	create_user_name VARCHAR(16) NULL,
	modify_time      VARCHAR(32) NULL,
	modify_user      VARCHAR(16) NULL,
	modify_user_name VARCHAR(16) NULL,
	delete_time      VARCHAR(32) NULL,
	delete_user      VARCHAR(16) NULL,
	delete_user_name VARCHAR(16) NULL,
	delete_flag      VARCHAR(4)  NULL,
	belong_module    VARCHAR(32) NULL,
	belong_system    VARCHAR(32) NULL,
	CONSTRAINT t_front_func_pkey PRIMARY KEY (row_id)
);

COMMENT ON TABLE t_front_func IS '前端功能块';