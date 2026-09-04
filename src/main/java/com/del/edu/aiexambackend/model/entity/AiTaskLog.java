package com.del.edu.aiexambackend.model.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import lombok.Data;

/**
 * AI调用日志表
 * @TableName ai_task_log
 */
@TableName(value ="ai_task_log")
@Data
public class AiTaskLog {
    /**
     * 主键ID
     */
    @TableId
    private String id;

    /**
     * 所属机构ID
     */
    private String tenantId;

    /**
     * 任务类型: GENERATE_QUESTION / COMPOSE_EXAM / GRADE_ANSWER
     */
    private String taskType;

    /**
     * 模型名称
     */
    private String model;

    /**
     * 完整提示词
     */
    private String prompt;

    /**
     * 完整响应内容
     */
    private String response;

    /**
     * 输入token数
     */
    private Integer tokensInput;

    /**
     * 输出token数
     */
    private Integer tokensOutput;

    /**
     * 调用耗时(毫秒)
     */
    private Integer durationMs;

    /**
     * 状态: SUCCESS / FAILED
     */
    private String status;

    /**
     * 失败原因
     */
    private String errorMsg;

    /**
     * 关联业务ID
     */
    private String relatedId;

    /**
     * 操作人ID
     */
    private String createdBy;

    /**
     * 创建时间
     */
    private Date createTime;

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        AiTaskLog other = (AiTaskLog) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getTenantId() == null ? other.getTenantId() == null : this.getTenantId().equals(other.getTenantId()))
            && (this.getTaskType() == null ? other.getTaskType() == null : this.getTaskType().equals(other.getTaskType()))
            && (this.getModel() == null ? other.getModel() == null : this.getModel().equals(other.getModel()))
            && (this.getPrompt() == null ? other.getPrompt() == null : this.getPrompt().equals(other.getPrompt()))
            && (this.getResponse() == null ? other.getResponse() == null : this.getResponse().equals(other.getResponse()))
            && (this.getTokensInput() == null ? other.getTokensInput() == null : this.getTokensInput().equals(other.getTokensInput()))
            && (this.getTokensOutput() == null ? other.getTokensOutput() == null : this.getTokensOutput().equals(other.getTokensOutput()))
            && (this.getDurationMs() == null ? other.getDurationMs() == null : this.getDurationMs().equals(other.getDurationMs()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getErrorMsg() == null ? other.getErrorMsg() == null : this.getErrorMsg().equals(other.getErrorMsg()))
            && (this.getRelatedId() == null ? other.getRelatedId() == null : this.getRelatedId().equals(other.getRelatedId()))
            && (this.getCreatedBy() == null ? other.getCreatedBy() == null : this.getCreatedBy().equals(other.getCreatedBy()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getTenantId() == null) ? 0 : getTenantId().hashCode());
        result = prime * result + ((getTaskType() == null) ? 0 : getTaskType().hashCode());
        result = prime * result + ((getModel() == null) ? 0 : getModel().hashCode());
        result = prime * result + ((getPrompt() == null) ? 0 : getPrompt().hashCode());
        result = prime * result + ((getResponse() == null) ? 0 : getResponse().hashCode());
        result = prime * result + ((getTokensInput() == null) ? 0 : getTokensInput().hashCode());
        result = prime * result + ((getTokensOutput() == null) ? 0 : getTokensOutput().hashCode());
        result = prime * result + ((getDurationMs() == null) ? 0 : getDurationMs().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getErrorMsg() == null) ? 0 : getErrorMsg().hashCode());
        result = prime * result + ((getRelatedId() == null) ? 0 : getRelatedId().hashCode());
        result = prime * result + ((getCreatedBy() == null) ? 0 : getCreatedBy().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", tenantId=").append(tenantId);
        sb.append(", taskType=").append(taskType);
        sb.append(", model=").append(model);
        sb.append(", prompt=").append(prompt);
        sb.append(", response=").append(response);
        sb.append(", tokensInput=").append(tokensInput);
        sb.append(", tokensOutput=").append(tokensOutput);
        sb.append(", durationMs=").append(durationMs);
        sb.append(", status=").append(status);
        sb.append(", errorMsg=").append(errorMsg);
        sb.append(", relatedId=").append(relatedId);
        sb.append(", createdBy=").append(createdBy);
        sb.append(", createTime=").append(createTime);
        sb.append("]");
        return sb.toString();
    }
}