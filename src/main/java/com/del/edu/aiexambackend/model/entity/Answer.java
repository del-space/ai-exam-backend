package com.del.edu.aiexambackend.model.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import lombok.Data;

/**
 * 答题详情表
 * @TableName answer
 */
@TableName(value ="answer")
@Data
public class Answer {
    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.INPUT)
    private String id;

    /**
     * 所属机构ID
     */
    private String tenantId;

    /**
     * 考试记录ID
     */
    private String recordId;

    /**
     * 题目ID
     */
    private String questionId;

    /**
     * 学生答案
     */
    private String userAnswer;

    /**
     * 最终得分
     */
    private BigDecimal score;

    /**
     * AI评分
     */
    private BigDecimal aiScore;

    /**
     * AI评语/解析
     */
    private String aiFeedback;

    /**
     * AI置信度(0.00-1.00)
     */
    private BigDecimal aiConfidence;

    /**
     * 阅卷使用的AI模型
     */
    private String aiModel;

    /**
     * 阅卷AI原始返回
     */
    private String aiRawResponse;

    /**
     * 人工调整分
     */
    private BigDecimal manualScore;

    /**
     * 是否已人工复核
     */
    private Boolean reviewed;

    /**
     * 复核人ID
     */
    private String reviewerId;

    /**
     * 复核时间
     */
    private Date reviewedAt;

    /**
     * 是否删除
     */
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private Integer isDelete;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    /**
     * 编辑时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime editTime;

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
        Answer other = (Answer) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getTenantId() == null ? other.getTenantId() == null : this.getTenantId().equals(other.getTenantId()))
            && (this.getRecordId() == null ? other.getRecordId() == null : this.getRecordId().equals(other.getRecordId()))
            && (this.getQuestionId() == null ? other.getQuestionId() == null : this.getQuestionId().equals(other.getQuestionId()))
            && (this.getUserAnswer() == null ? other.getUserAnswer() == null : this.getUserAnswer().equals(other.getUserAnswer()))
            && (this.getScore() == null ? other.getScore() == null : this.getScore().equals(other.getScore()))
            && (this.getAiScore() == null ? other.getAiScore() == null : this.getAiScore().equals(other.getAiScore()))
            && (this.getAiFeedback() == null ? other.getAiFeedback() == null : this.getAiFeedback().equals(other.getAiFeedback()))
            && (this.getAiConfidence() == null ? other.getAiConfidence() == null : this.getAiConfidence().equals(other.getAiConfidence()))
            && (this.getAiModel() == null ? other.getAiModel() == null : this.getAiModel().equals(other.getAiModel()))
            && (this.getAiRawResponse() == null ? other.getAiRawResponse() == null : this.getAiRawResponse().equals(other.getAiRawResponse()))
            && (this.getManualScore() == null ? other.getManualScore() == null : this.getManualScore().equals(other.getManualScore()))
            && (this.getReviewed() == null ? other.getReviewed() == null : this.getReviewed().equals(other.getReviewed()))
            && (this.getReviewerId() == null ? other.getReviewerId() == null : this.getReviewerId().equals(other.getReviewerId()))
            && (this.getReviewedAt() == null ? other.getReviewedAt() == null : this.getReviewedAt().equals(other.getReviewedAt()))
            && (this.getIsDelete() == null ? other.getIsDelete() == null : this.getIsDelete().equals(other.getIsDelete()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
            && (this.getEditTime() == null ? other.getEditTime() == null : this.getEditTime().equals(other.getEditTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getTenantId() == null) ? 0 : getTenantId().hashCode());
        result = prime * result + ((getRecordId() == null) ? 0 : getRecordId().hashCode());
        result = prime * result + ((getQuestionId() == null) ? 0 : getQuestionId().hashCode());
        result = prime * result + ((getUserAnswer() == null) ? 0 : getUserAnswer().hashCode());
        result = prime * result + ((getScore() == null) ? 0 : getScore().hashCode());
        result = prime * result + ((getAiScore() == null) ? 0 : getAiScore().hashCode());
        result = prime * result + ((getAiFeedback() == null) ? 0 : getAiFeedback().hashCode());
        result = prime * result + ((getAiConfidence() == null) ? 0 : getAiConfidence().hashCode());
        result = prime * result + ((getAiModel() == null) ? 0 : getAiModel().hashCode());
        result = prime * result + ((getAiRawResponse() == null) ? 0 : getAiRawResponse().hashCode());
        result = prime * result + ((getManualScore() == null) ? 0 : getManualScore().hashCode());
        result = prime * result + ((getReviewed() == null) ? 0 : getReviewed().hashCode());
        result = prime * result + ((getReviewerId() == null) ? 0 : getReviewerId().hashCode());
        result = prime * result + ((getReviewedAt() == null) ? 0 : getReviewedAt().hashCode());
        result = prime * result + ((getIsDelete() == null) ? 0 : getIsDelete().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        result = prime * result + ((getEditTime() == null) ? 0 : getEditTime().hashCode());
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
        sb.append(", recordId=").append(recordId);
        sb.append(", questionId=").append(questionId);
        sb.append(", userAnswer=").append(userAnswer);
        sb.append(", score=").append(score);
        sb.append(", aiScore=").append(aiScore);
        sb.append(", aiFeedback=").append(aiFeedback);
        sb.append(", aiConfidence=").append(aiConfidence);
        sb.append(", aiModel=").append(aiModel);
        sb.append(", aiRawResponse=").append(aiRawResponse);
        sb.append(", manualScore=").append(manualScore);
        sb.append(", reviewed=").append(reviewed);
        sb.append(", reviewerId=").append(reviewerId);
        sb.append(", reviewedAt=").append(reviewedAt);
        sb.append(", isDelete=").append(isDelete);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", editTime=").append(editTime);
        sb.append("]");
        return sb.toString();
    }
}