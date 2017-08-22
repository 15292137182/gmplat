DROP TABLE IF EXISTS t_front_func;

CREATE TABLE t_front_func (
	row_id varchar(70) NOT NULL,
	func_code varchar(64) NULL,
	func_name varchar(64) NULL,
	func_type varchar(8) NULL,
	relate_busi_obj varchar(70) NULL,
	wether_available varchar(4) NULL,
	desp text NULL,
	status varchar(8) NULL,
	"version" varchar(16) NULL,
	create_time varchar(32) NULL,
	create_user varchar(16) NULL,
	create_user_name varchar(16) NULL,
	modify_time varchar(32) NULL,
	modify_user varchar(16) NULL,
	modify_user_name varchar(16) NULL,
	delete_time varchar(32) NULL,
	delete_user varchar(16) NULL,
	delete_user_name varchar(16) NULL,
	delete_flag varchar(4) NULL,
	CONSTRAINT t_front_func_pkey PRIMARY KEY (row_id)
);

COMMENT ON TABLE t_front_func IS '前端功能块';