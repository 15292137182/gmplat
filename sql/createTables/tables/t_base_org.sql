DROP TABLE IF EXISTS t_base_org;

CREATE TABLE t_base_org (
  row_id      VARCHAR(64)  NOT NULL PRIMARY KEY,
  org_pid     VARCHAR(64)  NULL,
  org_id      VARCHAR(64)  NULL,
  org_name    VARCHAR(128) NULL,
  org_sort    INT          NULL,
  org_level   VARCHAR(4)   NULL,
  fixed_phone VARCHAR(32)  NULL,
  address     TEXT         NULL,
  desp        TEXT         NULL,

  etc         JSONB DEFAULT '{}',
  delete_flag VARCHAR(4)   NULL
);

COMMENT ON TABLE t_base_org IS '组织机构';