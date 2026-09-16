-- cssd 医院消毒供应中心灭菌质量追溯 -- schema (liu-014)
-- 列名与基线实体契约（@TableName/@TableField）逐列对齐，改列必须同步实体。
-- 库：liu_014

CREATE TABLE IF NOT EXISTS t_cssd_batch_row (
  id bigint NOT NULL COMMENT '主键',
  batch_no varchar(64) DEFAULT NULL COMMENT '批次号',
  row_no int DEFAULT NULL COMMENT '原始行号',
  item_code varchar(64) DEFAULT NULL COMMENT '明细编码',
  qty decimal(12,2) DEFAULT NULL COMMENT '数量',
  status int DEFAULT NULL COMMENT '行状态 0待处理 1成功 2失败',
  del_flag int DEFAULT '0' COMMENT '删除标记 0正常 1删除',
  create_by varchar(64) DEFAULT NULL COMMENT '创建者',
  create_time datetime DEFAULT NULL COMMENT '创建时间',
  update_by varchar(64) DEFAULT NULL COMMENT '更新者',
  update_time datetime DEFAULT NULL COMMENT '更新时间',
  remark varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='灭菌批次明细';

CREATE TABLE IF NOT EXISTS t_cssd_dept (
  id bigint NOT NULL COMMENT '主键',
  dept_code varchar(32) DEFAULT NULL COMMENT '科室编号',
  dept_name varchar(64) DEFAULT NULL COMMENT '科室名称',
  status int DEFAULT NULL COMMENT '档案状态 0在用 1已停用',
  del_flag int DEFAULT '0' COMMENT '删除标记 0正常 1删除',
  create_by varchar(64) DEFAULT NULL COMMENT '创建者',
  create_time datetime DEFAULT NULL COMMENT '创建时间',
  update_by varchar(64) DEFAULT NULL COMMENT '更新者',
  update_time datetime DEFAULT NULL COMMENT '更新时间',
  remark varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='使用科室档案';

CREATE TABLE IF NOT EXISTS t_cssd_param_rule (
  id bigint NOT NULL COMMENT '主键',
  rule_code varchar(32) DEFAULT NULL COMMENT '判据编号',
  th1_max decimal(8,2) DEFAULT NULL COMMENT '合格档上限',
  th2_max decimal(8,2) DEFAULT NULL COMMENT '关注档上限',
  th3_max decimal(8,2) DEFAULT NULL COMMENT '警戒档上限',
  eff_start datetime DEFAULT NULL COMMENT '生效起始时刻',
  eff_end datetime DEFAULT NULL COMMENT '生效截止时刻(不含)',
  priority int DEFAULT NULL COMMENT '先后次序(数值越大越优先)',
  status int DEFAULT NULL COMMENT '判据状态 0启用 1停用',
  del_flag int DEFAULT '0' COMMENT '删除标记 0正常 1删除',
  create_by varchar(64) DEFAULT NULL COMMENT '创建者',
  create_time datetime DEFAULT NULL COMMENT '创建时间',
  update_by varchar(64) DEFAULT NULL COMMENT '更新者',
  update_time datetime DEFAULT NULL COMMENT '更新时间',
  remark varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='灭菌参数达标阈值判据';

CREATE TABLE IF NOT EXISTS t_cssd_recall (
  id bigint NOT NULL COMMENT '主键',
  biz_no varchar(64) DEFAULT NULL COMMENT '处置单号',
  stage int DEFAULT NULL COMMENT '当前环节 0..3',
  status int DEFAULT NULL COMMENT '处置状态 0待发起 1在办 2已收尾',
  content varchar(255) DEFAULT NULL COMMENT '备注',
  last_action varchar(64) DEFAULT NULL COMMENT '最近一次处置动作',
  del_flag int DEFAULT '0' COMMENT '删除标记 0正常 1删除',
  create_by varchar(64) DEFAULT NULL COMMENT '创建者',
  create_time datetime DEFAULT NULL COMMENT '创建时间',
  update_by varchar(64) DEFAULT NULL COMMENT '更新者',
  update_time datetime DEFAULT NULL COMMENT '更新时间',
  remark varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='器械召回处置单';

CREATE TABLE IF NOT EXISTS t_cssd_release (
  id bigint NOT NULL COMMENT '主键',
  bill_no varchar(64) DEFAULT NULL COMMENT '单据编号',
  node_no int DEFAULT NULL COMMENT '当前环节 0..2',
  sign_mode int DEFAULT NULL COMMENT '签批模式 0或签 1会签',
  need_count int DEFAULT NULL COMMENT '本环节应签人数',
  sign_count int DEFAULT NULL COMMENT '本环节已签票数',
  status int DEFAULT NULL COMMENT '单据状态 0审批中 1已通过 2已否决',
  del_flag int DEFAULT '0' COMMENT '删除标记 0正常 1删除',
  create_by varchar(64) DEFAULT NULL COMMENT '创建者',
  create_time datetime DEFAULT NULL COMMENT '创建时间',
  update_by varchar(64) DEFAULT NULL COMMENT '更新者',
  update_time datetime DEFAULT NULL COMMENT '更新时间',
  remark varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='灭菌放行签批单';

CREATE TABLE IF NOT EXISTS t_cssd_return (
  id bigint NOT NULL COMMENT '主键',
  return_no varchar(64) DEFAULT NULL COMMENT '归还单号',
  dept_id bigint DEFAULT NULL COMMENT '归还科室ID',
  dept_code varchar(32) DEFAULT NULL COMMENT '科室编号(冗余，以档案为准)',
  return_date datetime DEFAULT NULL COMMENT '约定归还时限',
  remain_days int DEFAULT NULL COMMENT '距归还时限剩余天数',
  returner_name varchar(64) DEFAULT NULL COMMENT '归还人',
  status int DEFAULT NULL COMMENT '状态 0待归还 1已归还 2已逾期',
  del_flag int DEFAULT '0' COMMENT '删除标记 0正常 1删除',
  create_by varchar(64) DEFAULT NULL COMMENT '创建者',
  create_time datetime DEFAULT NULL COMMENT '创建时间',
  update_by varchar(64) DEFAULT NULL COMMENT '更新者',
  update_time datetime DEFAULT NULL COMMENT '更新时间',
  remark varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='器械归还单';

CREATE TABLE IF NOT EXISTS t_cssd_steril_task (
  id bigint NOT NULL COMMENT '主键',
  item_no varchar(64) DEFAULT NULL COMMENT '条目编号',
  due_at datetime DEFAULT NULL COMMENT '到期时刻',
  amount decimal(12,2) DEFAULT NULL COMMENT '计量值',
  status int DEFAULT NULL COMMENT '状态 0待处理 1已处理 2失败',
  del_flag int DEFAULT '0' COMMENT '删除标记 0正常 1删除',
  create_by varchar(64) DEFAULT NULL COMMENT '创建者',
  create_time datetime DEFAULT NULL COMMENT '创建时间',
  update_by varchar(64) DEFAULT NULL COMMENT '更新者',
  update_time datetime DEFAULT NULL COMMENT '更新时间',
  remark varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='灭菌效期到点条目';

-- 初始档案数据（验收测试依赖 id=1 启用 / id=2 停用）
INSERT INTO t_cssd_dept (id, dept_code, dept_name, status, del_flag, create_by, create_time)
VALUES (1, 'KS-0001', '手术室', 0, 0, 'sys', NOW()),
       (2, 'KS-0002', '停用科室', 1, 0, 'sys', NOW());
