package org.hrds.rducm.gitlab.domain.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.choerodon.mybatis.annotation.ModifyAudit;
import io.choerodon.mybatis.annotation.VersionAudit;
import io.choerodon.mybatis.domain.AuditDomain;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Gitlab成员表
 *
 * @author ying.xie@hand-china.com 2020-02-27 16:13:38
 */
@ApiModel("Gitlab成员表")
@VersionAudit
@ModifyAudit
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@Table(name = "rducm_gitlab_member")
public class RdmMember extends AuditDomain {

    public static final String FIELD_ID = "id";
    public static final String FIELD_PROJECT_ID = "projectId";
    public static final String FIELD_REPOSITORY_ID = "repositoryId";
    public static final String FIELD_USER_ID = "userId";
    public static final String FIELD_STATE = "state";
    public static final String FIELD_GL_PROJECT_ID = "glProjectId";
    public static final String FIELD_GL_USER_ID = "glUserId";
    public static final String FIELD_GL_ACCESS_LEVEL = "glAccessLevel";
    public static final String FIELD_GL_EXPIRES_AT = "glExpiresAt";
    public static final String FIELD_SYNC_GITLAB_FLAG = "syncGitlabFlag";
    public static final String FIELD_SYNC_DATE_GITLAB = "syncDateGitlab";

    //
    // 业务方法(按public protected private顺序排列)
    // ------------------------------------------------------------------------------

    /**
     * 设置是否过期标识
     */
    public boolean checkExpiredFlag() {
        // 当前时间 >= 过期时间
        if (this.getGlExpiresAt() != null && !new Date().before(this.getGlExpiresAt())) {
            return true;
        } else {
            return false;
        }
    }

    //
    // 数据库字段
    // ------------------------------------------------------------------------------


    @ApiModelProperty("")
    @Id
    @GeneratedValue
    private Long id;
    @ApiModelProperty(value = "项目层，项目id", required = true)
    @NotNull
    private Long projectId;
    @ApiModelProperty(value = "代码仓库id", required = true)
    @NotNull
    private Long repositoryId;
    @ApiModelProperty(value = "用户id", required = true)
    @NotNull
    private Long userId;
    @ApiModelProperty(value = "成员状态")
    private String state;
    @ApiModelProperty(value = "gitlab项目id")
    private Integer glProjectId;
    @ApiModelProperty(value = "gitlab用户id")
    private Integer glUserId;
    @ApiModelProperty(value = "gitlab成员权限级别")
    private Integer glAccessLevel;
    @ApiModelProperty(value = "gitlab成员过期时间")
    private Date glExpiresAt;
    @ApiModelProperty(value = "gitlab同步标识", required = true)
    @NotNull
    private Boolean syncGitlabFlag;
    @ApiModelProperty(value = "gitlab同步时间")
    private Date syncDateGitlab;

    //
    // 非数据库字段
    // ------------------------------------------------------------------------------
    /**
     * 是否到期
     */
    @Transient
    private Boolean expiredFlag;

    //
    // getter/setter
    // ------------------------------------------------------------------------------


    public Long getId() {
        return id;
    }

    public RdmMember setId(Long id) {
        this.id = id;
        return this;
    }

    public Long getProjectId() {
        return projectId;
    }

    public RdmMember setProjectId(Long projectId) {
        this.projectId = projectId;
        return this;
    }

    public Long getRepositoryId() {
        return repositoryId;
    }

    public RdmMember setRepositoryId(Long repositoryId) {
        this.repositoryId = repositoryId;
        return this;
    }

    public Long getUserId() {
        return userId;
    }

    public RdmMember setUserId(Long userId) {
        this.userId = userId;
        return this;
    }

    public String getState() {
        return state;
    }

    public RdmMember setState(String state) {
        this.state = state;
        return this;
    }

    public Integer getGlProjectId() {
        return glProjectId;
    }

    public RdmMember setGlProjectId(Integer glProjectId) {
        this.glProjectId = glProjectId;
        return this;
    }

    public Integer getGlUserId() {
        return glUserId;
    }

    public RdmMember setGlUserId(Integer glUserId) {
        this.glUserId = glUserId;
        return this;
    }

    public Integer getGlAccessLevel() {
        return glAccessLevel;
    }

    public RdmMember setGlAccessLevel(Integer glAccessLevel) {
        this.glAccessLevel = glAccessLevel;
        return this;
    }

    public Date getGlExpiresAt() {
        return glExpiresAt;
    }

    public RdmMember setGlExpiresAt(Date glExpiresAt) {
        this.glExpiresAt = glExpiresAt;
        return this;
    }

    public Boolean getSyncGitlabFlag() {
        return syncGitlabFlag;
    }

    public RdmMember setSyncGitlabFlag(Boolean syncGitlabFlag) {
        this.syncGitlabFlag = syncGitlabFlag;
        return this;
    }

    public Date getSyncDateGitlab() {
        return syncDateGitlab;
    }

    public RdmMember setSyncDateGitlab(Date syncDateGitlab) {
        this.syncDateGitlab = syncDateGitlab;
        return this;
    }

    public Boolean getExpiredFlag() {
        return expiredFlag;
    }

    public RdmMember setExpiredFlag(Boolean expiredFlag) {
        this.expiredFlag = expiredFlag;
        return this;
    }
}