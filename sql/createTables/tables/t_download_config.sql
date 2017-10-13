DROP TABLE IF EXISTS t_download_config;

CREATE TABLE t_download_config (
  row_id      VARCHAR(64) NOT NULL PRIMARY KEY,

  user_id     VARCHAR(32),
  file_title  VARCHAR(16),
  file_type   VARCHAR(4),
  content     TEXT,

  etc         JSONB DEFAULT '{}',
  delete_flag VARCHAR(4)  NULL
);
COMMENT ON TABLE t_download_config IS '用户下载配置';