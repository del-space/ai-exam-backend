package com.del.edu.aiexambackend.model.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import lombok.Data;

/**
 * 题目表
 * @TableName question
 */
@TableName(value ="question")
@Data
public class Question {
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
     * 所属题库ID
     */
    private String bankId;

    /**
     * 题目类型: SINGLE_CHOICE / MULTIPLE_CHOICE / JUDGE / ESSAY / SHORT_ANSWER
     */
    private String type;

    /**
     * 题目内容
     */
    private String content;

    /**
     * 选项(JSON格式)
     */
    private Object options;

    /**
     * 标准答案
     */
    private String answer;

    /**
     * 默认分值
     */
    private Integer score;

    /**
     * 难度(1-5)
     */
    private Integer difficulty;

    /**
     * 是否AI生成
     */
    private Boolean aiGenerated;

    /**
     * 生成用的AI模型名称
     */
    private String aiModel;

    /**
     * 生成该题使用的提示词
     */
    private String aiPrompt;

    /**
     * AI模型原始返回内容
     */
    private String aiRawResponse;

    /**
     * 审核状态: PENDING / APPROVED / REJECTED
     */
    private String reviewStatus;

    /**
     * 审核人ID
     */
    private String reviewerId;

    /**
     * 审核时间
     */
    private Date reviewedAt;

    /**
     * 是否删除
     */
    @TableLogic
    private Integer isDelete;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 编辑时间
     */
    private Date editTime;

    /**
     * 题目解析/参考答案
     */
    private String analysis;

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
        Question other = (Question) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getTenantId() == null ? other.getTenantId() == null : this.getTenantId().equals(other.getTenantId()))
            && (this.getBankId() == null ? other.getBankId() == null : this.getBankId().equals(other.getBankId()))
            && (this.getType() == null ? other.getType() == null : this.getType().equals(other.getType()))
            && (this.getContent() == null ? other.getContent() == null : this.getContent().equals(other.getContent()))
            && (this.getOptions() == null ? other.getOptions() == null : this.getOptions().equals(other.getOptions()))
            && (this.getAnswer() == null ? other.getAnswer() == null : this.getAnswer().equals(other.getAnswer()))
            && (this.getScore() == null ? other.getScore() == null : this.getScore().equals(other.getScore()))
            && (this.getDifficulty() == null ? other.getDifficulty() == null : this.getDifficulty().equals(other.getDifficulty()))
            && (this.getAiGenerated() == null ? other.getAiGenerated() == null : this.getAiGenerated().equals(other.getAiGenerated()))
            && (this.getAiModel() == null ? other.getAiModel() == null : this.getAiModel().equals(other.getAiModel()))
            && (this.getAiPrompt() == null ? other.getAiPrompt() == null : this.getAiPrompt().equals(other.getAiPrompt()))
            && (this.getAiRawResponse() == null ? other.getAiRawResponse() == null : this.getAiRawResponse().equals(other.getAiRawResponse()))
            && (this.getReviewStatus() == null ? other.getReviewStatus() == null : this.getReviewStatus().equals(other.getReviewStatus()))
            && (this.getReviewerId() == null ? other.getReviewerId() == null : this.getReviewerId().equals(other.getReviewerId()))
            && (this.getReviewedAt() == null ? other.getReviewedAt() == null : this.getReviewedAt().equals(other.getReviewedAt()))
            && (this.getIsDelete() == null ? other.getIsDelete() == null : this.getIsDelete().equals(other.getIsDelete()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
            && (this.getEditTime() == null ? other.getEditTime() == null : this.getEditTime().equals(other.getEditTime()))
            && (this.getAnalysis() == null ? other.getAnalysis() == null : this.getAnalysis().equals(other.getAnalysis()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getTenantId() == null) ? 0 : getTenantId().hashCode());
        result = prime * result + ((getBankId() == null) ? 0 : getBankId().hashCode());
        result = prime * result + ((getType() == null) ? 0 : getType().hashCode());
        result = prime * result + ((getContent() == null) ? 0 : getContent().hashCode());
        result = prime * result + ((getOptions() == null) ? 0 : getOptions().hashCode());
        result = prime * result + ((getAnswer() == null) ? 0 : getAnswer().hashCode());
        result = prime * result + ((getScore() == null) ? 0 : getScore().hashCode());
        result = prime * result + ((getDifficulty() == null) ? 0 : getDifficulty().hashCode());
        result = prime * result + ((getAiGenerated() == null) ? 0 : getAiGenerated().hashCode());
        result = prime * result + ((getAiModel() == null) ? 0 : getAiModel().hashCode());
        result = prime * result + ((getAiPrompt() == null) ? 0 : getAiPrompt().hashCode());
        result = prime * result + ((getAiRawResponse() == null) ? 0 : getAiRawResponse().hashCode());
        result = prime * result + ((getReviewStatus() == null) ? 0 : getReviewStatus().hashCode());
        result = prime * result + ((getReviewerId() == null) ? 0 : getReviewerId().hashCode());
        result = prime * result + ((getReviewedAt() == null) ? 0 : getReviewedAt().hashCode());
        result = prime * result + ((getIsDelete() == null) ? 0 : getIsDelete().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        result = prime * result + ((getEditTime() == null) ? 0 : getEditTime().hashCode());
        result = prime * result + ((getAnalysis() == null) ? 0 : getAnalysis().hashCode());
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
        sb.append(", bankId=").append(bankId);
        sb.append(", type=").append(type);
        sb.append(", content=").append(content);
        sb.append(", options=").append(options);
        sb.append(", answer=").append(answer);
        sb.append(", score=").append(score);
        sb.append(", difficulty=").append(difficulty);
        sb.append(", aiGenerated=").append(aiGenerated);
        sb.append(", aiModel=").append(aiModel);
        sb.append(", aiPrompt=").append(aiPrompt);
        sb.append(", aiRawResponse=").append(aiRawResponse);
        sb.append(", reviewStatus=").append(reviewStatus);
        sb.append(", reviewerId=").append(reviewerId);
        sb.append(", reviewedAt=").append(reviewedAt);
        sb.append(", isDelete=").append(isDelete);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", editTime=").append(editTime);
        sb.append(", analysis=").append(analysis);
        sb.append("]");
        return sb.toString();
    }
}