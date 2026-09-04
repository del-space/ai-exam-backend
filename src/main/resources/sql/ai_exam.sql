-- ============================================================
-- AI Exam Platform - Database Initialization
-- Project : ai-exam
-- Stack   : Spring Boot 4.0.7 + JDK 21 + PostgreSQL
-- ============================================================

-- 1. 创建专用用户（不要用 postgres 超级用户跑业务）
CREATE USER aiexam WITH PASSWORD 'AiExam@2026!';

-- 2. 创建数据库
CREATE DATABASE ai_exam
    WITH
    OWNER = aiexam
    ENCODING = 'UTF8'
    LC_COLLATE = 'C.UTF-8'
    LC_CTYPE = 'C.UTF-8'
    TEMPLATE = template0
    CONNECTION LIMIT = 200;

-- 3. 授权
GRANT ALL PRIVILEGES ON DATABASE ai_exam TO aiexam;

-- 4. 连接到新库，创建 Schema（逻辑隔离，方便后续扩展）
\c ai_exam

CREATE SCHEMA IF NOT EXISTS exam
    AUTHORIZATION aiexam;

-- 设置默认搜索路径（省得每次写 schema 前缀）
ALTER DATABASE ai_exam SET search_path TO exam, public;

-- 5. 扩展：UUID 生成（JDK 21 + Spring Boot 常用）
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

SELECT uuid_generate_v4()

-- ============================================================
-- AI Exam Platform - 最终表结构
-- 多租户 SaaS + AI 全链路 + 混合题库模式
-- ============================================================

SET search_path TO exam;

-- ============================================================
-- AI Exam Platform - 最终表结构
-- 主键 VARCHAR(64)，Java 层生成，不自增
-- ============================================================

SET search_path TO exam;

-- ============================================================
-- AI Exam Platform - PostgreSQL 最终表结构
-- 主键 VARCHAR(64)，Java 层生成，不自增
-- ============================================================

SET search_path TO exam;

-- ========== 租户/机构表 ==========
CREATE TABLE exam.tenant (
    id          VARCHAR(64) PRIMARY KEY,
    name        VARCHAR(200) NOT NULL,
    code        VARCHAR(50) UNIQUE,
    status      INT DEFAULT 1,
    expire_at   TIMESTAMP,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL
);

COMMENT ON TABLE exam.tenant IS '租户/机构表';
COMMENT ON COLUMN exam.tenant.id IS '主键ID';
COMMENT ON COLUMN exam.tenant.name IS '机构名称';
COMMENT ON COLUMN exam.tenant.code IS '机构编码';
COMMENT ON COLUMN exam.tenant.status IS '1=正常 0=禁用';
COMMENT ON COLUMN exam.tenant.expire_at IS '到期时间（SaaS收费用）';
COMMENT ON COLUMN exam.tenant.create_time IS '创建时间';

-- ========== 用户表 ==========
CREATE TABLE exam.user (
    id                VARCHAR(64) PRIMARY KEY,
    tenant_id         VARCHAR(64) NOT NULL REFERENCES exam.tenant(id),
    username          VARCHAR(50) NOT NULL,
    password          VARCHAR(255) NOT NULL,
    real_name         VARCHAR(50),
    email             VARCHAR(100),
    phone             VARCHAR(20),
    role              VARCHAR(20) NOT NULL,
    enabled           BOOLEAN DEFAULT true,
    last_login_time   TIMESTAMP,
    last_login_ip     VARCHAR(50),
    last_login_device VARCHAR(200),
    is_delete         SMALLINT DEFAULT 0 NOT NULL,
    create_time       TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    update_time       TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    edit_time         TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    UNIQUE (tenant_id, username)
);

COMMENT ON TABLE exam.user IS '用户表';
COMMENT ON COLUMN exam.user.id IS '主键ID';
COMMENT ON COLUMN exam.user.tenant_id IS '所属机构ID';
COMMENT ON COLUMN exam.user.username IS '用户名';
COMMENT ON COLUMN exam.user.password IS '密码';
COMMENT ON COLUMN exam.user.real_name IS '真实姓名';
COMMENT ON COLUMN exam.user.email IS '邮箱';
COMMENT ON COLUMN exam.user.phone IS '手机号';
COMMENT ON COLUMN exam.user.role IS '角色: SUPER_ADMIN / ORG_ADMIN / TEACHER / STUDENT';
COMMENT ON COLUMN exam.user.enabled IS '是否启用';
COMMENT ON COLUMN exam.user.last_login_time IS '最后登录时间';
COMMENT ON COLUMN exam.user.last_login_ip IS '最后登录IP';
COMMENT ON COLUMN exam.user.last_login_device IS '最后登录设备信息';
COMMENT ON COLUMN exam.user.is_delete IS '是否删除';
COMMENT ON COLUMN exam.user.create_time IS '创建时间';
COMMENT ON COLUMN exam.user.update_time IS '更新时间';
COMMENT ON COLUMN exam.user.edit_time IS '编辑时间';

-- ========== 班级表 ==========
CREATE TABLE exam.class (
    id          VARCHAR(64) PRIMARY KEY,
    tenant_id   VARCHAR(64) NOT NULL REFERENCES exam.tenant(id),
    name        VARCHAR(100) NOT NULL,
    teacher_id  VARCHAR(64) REFERENCES exam.user(id),
    is_delete   SMALLINT DEFAULT 0 NOT NULL,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    edit_time   TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL
);

COMMENT ON TABLE exam.class IS '班级表';
COMMENT ON COLUMN exam.class.id IS '主键ID';
COMMENT ON COLUMN exam.class.tenant_id IS '所属机构ID';
COMMENT ON COLUMN exam.class.name IS '班级名称';
COMMENT ON COLUMN exam.class.teacher_id IS '班主任/负责老师ID';
COMMENT ON COLUMN exam.class.is_delete IS '是否删除';
COMMENT ON COLUMN exam.class.create_time IS '创建时间';
COMMENT ON COLUMN exam.class.update_time IS '更新时间';
COMMENT ON COLUMN exam.class.edit_time IS '编辑时间';

-- ========== 学生-班级关联表 ==========
CREATE TABLE exam.class_student (
    id          VARCHAR(64) PRIMARY KEY,
    class_id    VARCHAR(64) NOT NULL REFERENCES exam.class(id) ON DELETE CASCADE,
    student_id  VARCHAR(64) NOT NULL REFERENCES exam.user(id),
    is_delete   SMALLINT DEFAULT 0 NOT NULL,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    edit_time   TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    UNIQUE (class_id, student_id)
);

COMMENT ON TABLE exam.class_student IS '学生班级关联表';
COMMENT ON COLUMN exam.class_student.id IS '主键ID';
COMMENT ON COLUMN exam.class_student.class_id IS '班级ID';
COMMENT ON COLUMN exam.class_student.student_id IS '学生ID';
COMMENT ON COLUMN exam.class_student.is_delete IS '是否删除';
COMMENT ON COLUMN exam.class_student.create_time IS '创建时间';
COMMENT ON COLUMN exam.class_student.update_time IS '更新时间';
COMMENT ON COLUMN exam.class_student.edit_time IS '编辑时间';

-- ========== 题库表 ==========
CREATE TABLE exam.question_bank (
    id          VARCHAR(64) PRIMARY KEY,
    tenant_id   VARCHAR(64) NOT NULL REFERENCES exam.tenant(id),
    name        VARCHAR(200) NOT NULL,
    description TEXT,
    visibility  VARCHAR(20) DEFAULT 'PRIVATE',
    created_by  VARCHAR(64) REFERENCES exam.user(id),
    is_delete   SMALLINT DEFAULT 0 NOT NULL,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    edit_time   TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL
);

COMMENT ON TABLE exam.question_bank IS '题库表';
COMMENT ON COLUMN exam.question_bank.id IS '主键ID';
COMMENT ON COLUMN exam.question_bank.tenant_id IS '所属机构ID';
COMMENT ON COLUMN exam.question_bank.name IS '题库名称';
COMMENT ON COLUMN exam.question_bank.description IS '题库描述';
COMMENT ON COLUMN exam.question_bank.visibility IS '可见性: PRIVATE(仅创建者) / ORG_PUBLIC(机构共享)';
COMMENT ON COLUMN exam.question_bank.created_by IS '创建人ID';
COMMENT ON COLUMN exam.question_bank.is_delete IS '是否删除';
COMMENT ON COLUMN exam.question_bank.create_time IS '创建时间';
COMMENT ON COLUMN exam.question_bank.update_time IS '更新时间';
COMMENT ON COLUMN exam.question_bank.edit_time IS '编辑时间';

-- ========== 题目表 ==========
CREATE TABLE exam.question (
    id              VARCHAR(64) PRIMARY KEY,
    tenant_id       VARCHAR(64) NOT NULL REFERENCES exam.tenant(id),
    bank_id         VARCHAR(64) NOT NULL REFERENCES exam.question_bank(id),
    type            VARCHAR(30) NOT NULL,
    content         TEXT NOT NULL,
    options         JSONB,
    answer          TEXT,
		analysis        TEXT, 
    score           INT DEFAULT 5,
    difficulty      INT DEFAULT 1 CHECK (difficulty BETWEEN 1 AND 5),
    ai_generated    BOOLEAN DEFAULT false,
    ai_model        VARCHAR(50),
    ai_prompt       TEXT,
    ai_raw_response TEXT,
    review_status   VARCHAR(20) DEFAULT 'PENDING',
    reviewer_id     VARCHAR(64) REFERENCES exam.user(id),
    reviewed_at     TIMESTAMP,
    is_delete       SMALLINT DEFAULT 0 NOT NULL,
    create_time     TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    update_time     TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    edit_time       TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL
);
COMMENT ON TABLE exam.question IS '题目表';
COMMENT ON COLUMN exam.question.id IS '主键ID';
COMMENT ON COLUMN exam.question.tenant_id IS '所属机构ID';
COMMENT ON COLUMN exam.question.bank_id IS '所属题库ID';
COMMENT ON COLUMN exam.question.type IS '题目类型: SINGLE_CHOICE / MULTIPLE_CHOICE / JUDGE / ESSAY / SHORT_ANSWER';
COMMENT ON COLUMN exam.question.content IS '题目内容';
COMMENT ON COLUMN exam.question.options IS '选项(JSON格式)';
COMMENT ON COLUMN exam.question.answer IS '标准答案';
COMMENT ON COLUMN exam.question.analysis IS '题目解析/参考答案';
COMMENT ON COLUMN exam.question.score IS '默认分值';
COMMENT ON COLUMN exam.question.difficulty IS '难度(1-5)';
COMMENT ON COLUMN exam.question.ai_generated IS '是否AI生成';
COMMENT ON COLUMN exam.question.ai_model IS '生成用的AI模型名称';
COMMENT ON COLUMN exam.question.ai_prompt IS '生成该题使用的提示词';
COMMENT ON COLUMN exam.question.ai_raw_response IS 'AI模型原始返回内容';
COMMENT ON COLUMN exam.question.review_status IS '审核状态: PENDING / APPROVED / REJECTED';
COMMENT ON COLUMN exam.question.reviewer_id IS '审核人ID';
COMMENT ON COLUMN exam.question.reviewed_at IS '审核时间';
COMMENT ON COLUMN exam.question.is_delete IS '是否删除';
COMMENT ON COLUMN exam.question.create_time IS '创建时间';
COMMENT ON COLUMN exam.question.update_time IS '更新时间';
COMMENT ON COLUMN exam.question.edit_time IS '编辑时间';

-- ========== 考试表 ==========
CREATE TABLE exam.exam (
    id              VARCHAR(64) PRIMARY KEY,
    tenant_id       VARCHAR(64) NOT NULL REFERENCES exam.tenant(id),
    title           VARCHAR(200) NOT NULL,
    description     TEXT,
    duration_min    INT NOT NULL,
    total_score     INT NOT NULL,
    start_time      TIMESTAMP NOT NULL,
    end_time        TIMESTAMP NOT NULL,
    status          VARCHAR(20) DEFAULT 'DRAFT',
    ai_compose_rule JSONB,
    compose_type    VARCHAR(20) DEFAULT 'MANUAL',
    class_id        VARCHAR(64) REFERENCES exam.class(id),
    created_by      VARCHAR(64) REFERENCES exam.user(id),
    is_delete       SMALLINT DEFAULT 0 NOT NULL,
    create_time     TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    update_time     TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    edit_time       TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL
);

COMMENT ON TABLE exam.exam IS '考试表';
COMMENT ON COLUMN exam.exam.id IS '主键ID';
COMMENT ON COLUMN exam.exam.tenant_id IS '所属机构ID';
COMMENT ON COLUMN exam.exam.title IS '考试标题';
COMMENT ON COLUMN exam.exam.description IS '考试描述';
COMMENT ON COLUMN exam.exam.duration_min IS '考试时长(分钟)';
COMMENT ON COLUMN exam.exam.total_score IS '总分';
COMMENT ON COLUMN exam.exam.start_time IS '开始时间';
COMMENT ON COLUMN exam.exam.end_time IS '结束时间';
COMMENT ON COLUMN exam.exam.status IS '状态: DRAFT / PUBLISHED / ONGOING / FINISHED';
COMMENT ON COLUMN exam.exam.ai_compose_rule IS 'AI组卷规则(JSON)';
COMMENT ON COLUMN exam.exam.compose_type IS '组卷方式: MANUAL(手动) / AI_AUTO(AI自动)';
COMMENT ON COLUMN exam.exam.class_id IS '分配给哪个班级';
COMMENT ON COLUMN exam.exam.created_by IS '创建人ID(班主任)';
COMMENT ON COLUMN exam.exam.is_delete IS '是否删除';
COMMENT ON COLUMN exam.exam.create_time IS '创建时间';
COMMENT ON COLUMN exam.exam.update_time IS '更新时间';
COMMENT ON COLUMN exam.exam.edit_time IS '编辑时间';

-- ========== 考试-题目关联表 ==========
CREATE TABLE exam.exam_question (
    id          VARCHAR(64) PRIMARY KEY,
    exam_id     VARCHAR(64) NOT NULL REFERENCES exam.exam(id) ON DELETE CASCADE,
    question_id VARCHAR(64) NOT NULL REFERENCES exam.question(id),
    sort_order  INT DEFAULT 0,
    score       INT DEFAULT 5,
    is_delete   SMALLINT DEFAULT 0 NOT NULL,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    edit_time   TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    UNIQUE (exam_id, question_id)
);

COMMENT ON TABLE exam.exam_question IS '考试题目关联表';
COMMENT ON COLUMN exam.exam_question.id IS '主键ID';
COMMENT ON COLUMN exam.exam_question.exam_id IS '考试ID';
COMMENT ON COLUMN exam.exam_question.question_id IS '题目ID';
COMMENT ON COLUMN exam.exam_question.sort_order IS '题目排序';
COMMENT ON COLUMN exam.exam_question.score IS '该题实际分值';
COMMENT ON COLUMN exam.exam_question.is_delete IS '是否删除';
COMMENT ON COLUMN exam.exam_question.create_time IS '创建时间';
COMMENT ON COLUMN exam.exam_question.update_time IS '更新时间';
COMMENT ON COLUMN exam.exam_question.edit_time IS '编辑时间';

-- ========== 考试记录表 ==========
CREATE TABLE exam.exam_record (
    id          VARCHAR(64) PRIMARY KEY,
    tenant_id   VARCHAR(64) NOT NULL REFERENCES exam.tenant(id),
    exam_id     VARCHAR(64) NOT NULL REFERENCES exam.exam(id),
    user_id     VARCHAR(64) NOT NULL REFERENCES exam.user(id),
    start_time  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    submit_time TIMESTAMP,
    score       DECIMAL(5,2),
    status      VARCHAR(20) DEFAULT 'IN_PROGRESS',
    is_delete   SMALLINT DEFAULT 0 NOT NULL,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    edit_time   TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    UNIQUE (exam_id, user_id)
);

COMMENT ON TABLE exam.exam_record IS '考试记录表';
COMMENT ON COLUMN exam.exam_record.id IS '主键ID';
COMMENT ON COLUMN exam.exam_record.tenant_id IS '所属机构ID';
COMMENT ON COLUMN exam.exam_record.exam_id IS '考试ID';
COMMENT ON COLUMN exam.exam_record.user_id IS '考生ID';
COMMENT ON COLUMN exam.exam_record.start_time IS '开始答题时间';
COMMENT ON COLUMN exam.exam_record.submit_time IS '提交时间';
COMMENT ON COLUMN exam.exam_record.score IS '总分';
COMMENT ON COLUMN exam.exam_record.status IS '状态: IN_PROGRESS / SUBMITTED / GRADED';
COMMENT ON COLUMN exam.exam_record.is_delete IS '是否删除';
COMMENT ON COLUMN exam.exam_record.create_time IS '创建时间';
COMMENT ON COLUMN exam.exam_record.update_time IS '更新时间';
COMMENT ON COLUMN exam.exam_record.edit_time IS '编辑时间';

-- ========== 答题详情表 ==========
CREATE TABLE exam.answer (
    id              VARCHAR(64) PRIMARY KEY,
    tenant_id       VARCHAR(64) NOT NULL REFERENCES exam.tenant(id),
    record_id       VARCHAR(64) NOT NULL REFERENCES exam.exam_record(id) ON DELETE CASCADE,
    question_id     VARCHAR(64) NOT NULL REFERENCES exam.question(id),
    user_answer     TEXT,
    score           DECIMAL(5,2),
    ai_score        DECIMAL(5,2),
    ai_feedback     TEXT,
    ai_confidence   DECIMAL(3,2),
    ai_model        VARCHAR(50),
    ai_raw_response TEXT,
    manual_score    DECIMAL(5,2),
    reviewed        BOOLEAN DEFAULT false,
    reviewer_id     VARCHAR(64) REFERENCES exam.user(id),
    reviewed_at     TIMESTAMP,
    is_delete       SMALLINT DEFAULT 0 NOT NULL,
    create_time     TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    update_time     TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    edit_time       TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    UNIQUE (record_id, question_id)
);

COMMENT ON TABLE exam.answer IS '答题详情表';
COMMENT ON COLUMN exam.answer.id IS '主键ID';
COMMENT ON COLUMN exam.answer.tenant_id IS '所属机构ID';
COMMENT ON COLUMN exam.answer.record_id IS '考试记录ID';
COMMENT ON COLUMN exam.answer.question_id IS '题目ID';
COMMENT ON COLUMN exam.answer.user_answer IS '学生答案';
COMMENT ON COLUMN exam.answer.score IS '最终得分';
COMMENT ON COLUMN exam.answer.ai_score IS 'AI评分';
COMMENT ON COLUMN exam.answer.ai_feedback IS 'AI评语/解析';
COMMENT ON COLUMN exam.answer.ai_confidence IS 'AI置信度(0.00-1.00)';
COMMENT ON COLUMN exam.answer.ai_model IS '阅卷使用的AI模型';
COMMENT ON COLUMN exam.answer.ai_raw_response IS '阅卷AI原始返回';
COMMENT ON COLUMN exam.answer.manual_score IS '人工调整分';
COMMENT ON COLUMN exam.answer.reviewed IS '是否已人工复核';
COMMENT ON COLUMN exam.answer.reviewer_id IS '复核人ID';
COMMENT ON COLUMN exam.answer.reviewed_at IS '复核时间';
COMMENT ON COLUMN exam.answer.is_delete IS '是否删除';
COMMENT ON COLUMN exam.answer.create_time IS '创建时间';
COMMENT ON COLUMN exam.answer.update_time IS '更新时间';
COMMENT ON COLUMN exam.answer.edit_time IS '编辑时间';

-- ========== AI 调用日志表 ==========
CREATE TABLE exam.ai_task_log (
    id            VARCHAR(64) PRIMARY KEY,
    tenant_id     VARCHAR(64) NOT NULL REFERENCES exam.tenant(id),
    task_type     VARCHAR(30) NOT NULL,
    model         VARCHAR(50) NOT NULL,
    prompt        TEXT,
    response      TEXT,
    tokens_input  INT,
    tokens_output INT,
    duration_ms   INT,
    status        VARCHAR(20),
    error_msg     TEXT,
    related_id    VARCHAR(64),
    created_by    VARCHAR(64) REFERENCES exam.user(id),
    create_time   TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL
);

COMMENT ON TABLE exam.ai_task_log IS 'AI调用日志表';
COMMENT ON COLUMN exam.ai_task_log.id IS '主键ID';
COMMENT ON COLUMN exam.ai_task_log.tenant_id IS '所属机构ID';
COMMENT ON COLUMN exam.ai_task_log.task_type IS '任务类型: GENERATE_QUESTION / COMPOSE_EXAM / GRADE_ANSWER';
COMMENT ON COLUMN exam.ai_task_log.model IS '模型名称';
COMMENT ON COLUMN exam.ai_task_log.prompt IS '完整提示词';
COMMENT ON COLUMN exam.ai_task_log.response IS '完整响应内容';
COMMENT ON COLUMN exam.ai_task_log.tokens_input IS '输入token数';
COMMENT ON COLUMN exam.ai_task_log.tokens_output IS '输出token数';
COMMENT ON COLUMN exam.ai_task_log.duration_ms IS '调用耗时(毫秒)';
COMMENT ON COLUMN exam.ai_task_log.status IS '状态: SUCCESS / FAILED';
COMMENT ON COLUMN exam.ai_task_log.error_msg IS '失败原因';
COMMENT ON COLUMN exam.ai_task_log.related_id IS '关联业务ID';
COMMENT ON COLUMN exam.ai_task_log.created_by IS '操作人ID';
COMMENT ON COLUMN exam.ai_task_log.create_time IS '创建时间';

-- ========== 索引 ==========
CREATE INDEX idx_user_tenant ON exam.user(tenant_id);
CREATE INDEX idx_question_bank_tenant ON exam.question_bank(tenant_id);
CREATE INDEX idx_question_bank_visibility ON exam.question_bank(visibility);
CREATE INDEX idx_question_bank_created_by ON exam.question_bank(created_by);
CREATE INDEX idx_question_bank_id ON exam.question(bank_id);
CREATE INDEX idx_question_review ON exam.question(review_status) WHERE review_status = 'PENDING';
CREATE INDEX idx_exam_status ON exam.exam(status);
CREATE INDEX idx_exam_class ON exam.exam(class_id);
CREATE INDEX idx_exam_record_user ON exam.exam_record(user_id);
CREATE INDEX idx_answer_record ON exam.answer(record_id);
CREATE INDEX idx_ai_log_task_type ON exam.ai_task_log(task_type);
CREATE INDEX idx_ai_log_created_at ON exam.ai_task_log(create_time);