import React from 'react';
import moment from 'moment';
import { map } from 'lodash';
import Tips from '@/components/new-tips';

export default ((intlPrefix, formatMessage, organizationId, projectId, branchServiceDs, repositoryIds) => ({
  autoQuery: false,
  selection: false,
  pageSize: 10,
  transport: {
    read: () => ({
      url: `/rducm/v1/organizations/${organizationId}/projects/${projectId}/gitlab/repositories/members`,
      method: 'get',
      transformResponse: (resp) => {
        try {
          const data = JSON.parse(resp);
          if (data && data.failed) {
            return data;
          } else {
            const { list, content, ...others } = data;
            const tempList = map(data.list || data.content || [], item => ({
              ...item,
              glAccessLevel: item.glAccessLevel ? `L${item.glAccessLevel}` : '',
              glAccessLevelList: item.glAccessLevel ? `L${item.glAccessLevel}` : '',
              loginName: item.user ? item.user.loginName : undefined,
              realName: item.user ? item.user.realName : undefined,
            }));
            return {
              ...others,
              list: tempList,
              content: tempList,
            };
          }
        } catch (e) {
          return resp;
        }
      },
    }),
    update: ({ data: editData }) => {
      const params = {
        objectVersionNumber: editData[0].objectVersionNumber,
        glAccessLevel: editData[0].glAccessLevel.substring(1),
        glExpiresAt: editData[0].glExpiresAt ? moment(editData[0].glExpiresAt).format('YYYY-MM-DD 00:00:00') : null,
      };
      return {
        url: `/rducm/v1/organizations/${organizationId}/projects/${projectId}/gitlab/repositories/${editData[0].repositoryId}/members/${editData[0].id}`,
        method: 'put',
        transformRequest: (() => JSON.stringify(params)),
      };
    },
    destroy: ({ data: [data] }) => ({
      url: `/rducm/v1/organizations/${organizationId}/projects/${projectId}/gitlab/repositories/${data.repositoryId}/members/${data.id}`,
      method: 'delete',
    }),
  },
  fields: [
    {
      name: 'realName',
      type: 'string',
      label: formatMessage({ id: 'userName' }),
    }, // 用户名
    {
      name: 'syncGitlabFlag',
      type: 'boolean',
    }, // 是否已同步
    {
      name: 'loginName',
      type: 'string',
      label: formatMessage({ id: 'loginName' }),
    }, // 登录名
    {
      name: 'repositoryName',
      type: 'string',
      label: formatMessage({ id: `${intlPrefix}.service` }),
    }, // 服务名称
    {
      name: 'roleNames',
      type: 'string',
      label: formatMessage({ id: 'projectRole' }),
    }, // 项目角色
    {
      name: 'glAccessLevelList',
      type: 'string',
      required: true,
      // label: formatMessage({ id: `${intlPrefix}.permission` }),
      lookupCode: 'RDUCM.ACCESS_LEVEL',
      label: (
        <Tips
          title={formatMessage({ id: `${intlPrefix}.permission` })}
          helpText={
            <div>
              权限逻辑：<br />
              团队成员中<br />
              1. 组织管理员默认初始化为Owner权限<br />
              2. 项目管理员默认初始化为Owner权限<br />
              3. 项目成员默认不初始化权限<br />
            </div>
          }
        />
      ),
    },
    {
      name: 'glAccessLevel',
      type: 'string',
      required: true,
      label: formatMessage({ id: `${intlPrefix}.permission` }),
      lookupCode: 'RDUCM.ACCESS_LEVEL',
    },
    {
      name: 'glExpiresAt',
      type: 'date',
      label: formatMessage({ id: `${intlPrefix}.expiresAt` }),
    }, // 过期时间
    {
      name: 'createdByName',
      type: 'string',
      label: formatMessage({ id: `${intlPrefix}.creationBy` }),
    }, // 创建人
    {
      name: 'lastUpdateDate',
      type: 'dateTime',
      label: formatMessage({ id: 'updateDate' }),
    }, // 更新时间
  ],
  queryFields: [
    {
      name: 'realName',
      type: 'string',
      label: formatMessage({ id: 'userName' }),
    },
    {
      name: 'loginName',
      type: 'string',
      label: formatMessage({ id: 'loginName' }),
    },
    {
      name: 'repositoryIds',
      type: 'string',
      label: formatMessage({ id: `${intlPrefix}.service` }),
      textField: 'repositoryName',
      valueField: 'repositoryId',
      defaultValue: repositoryIds,
      options: branchServiceDs,
    }, // 服务名称
    // {
    //   name: 'employeeObject',
    //   label: '员工',
    //   type: 'object',
    //   lovCode: 'TODO.USER',
    //   textField: 'id',
    //   ignore: 'always',
    // },
    // {
    //   name: 'employeeId',
    //   label: '员工ID',
    //   type: 'number',
    //   bind: 'employeeObject.id',
    // },
    // {
    //   name: 'employeeName',
    //   label: '员工姓名',
    //   type: 'string',
    //   bind: 'employeeObject.employeeName',
    // },
  ],
}));
