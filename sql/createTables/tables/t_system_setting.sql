DROP TABLE IF EXISTS t_system_setting;

CREATE TABLE t_system_setting (
  row_id      VARCHAR(70) NOT NULL PRIMARY KEY,

  "key"       VARCHAR(32) NOT NULL,
  name        VARCHAR(32) DEFAULT '',
  value       VARCHAR(64) DEFAULT '',
  remarks     VARCHAR(64) DEFAULT '',

  etc         JSONB       DEFAULT '{}',
  delete_flag VARCHAR(4)  NULL

);
COMMENT ON TABLE t_system_setting IS '系统设置表';