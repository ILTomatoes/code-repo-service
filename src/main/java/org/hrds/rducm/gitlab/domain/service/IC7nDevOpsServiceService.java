package org.hrds.rducm.gitlab.domain.service;

import com.github.pagehelper.PageInfo;
import io.choerodon.mybatis.pagehelper.domain.PageRequest;
import org.hrds.rducm.gitlab.infra.feign.vo.C7nAppServiceVO;

import java.util.Map;
import java.util.Set;

/**
 * @author ying.xie@hand-china.com
 * @date 2020/3/18
 */
public interface IC7nDevOpsServiceService {
    /**
     * 根据应用服务id获取Gitlab代码库id
     * todo 高频调用, 是否使用缓存
     *
     * @param repositoryId
     * @return
     */
    Integer repositoryIdToGlProjectId(Long repositoryId);

    /**
     * 查询应用服务信息
     *
     * @param repositoryId
     * @return
     */
    C7nAppServiceVO detailC7nAppService(Long repositoryId);

    /**
     * 查询一组应用服务信息, 并转换成Map
     *
     * @param repositoryIds
     * @return
     */
    Map<Long, C7nAppServiceVO> listC7nAppServiceToMap(Set<Long> repositoryIds);

    /**
     * 项目层
     * 查询应用服务ids, 根据应用服务名模糊查询
     *
     * @param projectId
     * @param appServiceName
     * @return
     */
    Set<Long> listC7nAppServiceIdsByNameOnProjectLevel(Long projectId, String appServiceName);

    /**
     * 组织层
     * 查询应用服务ids, 根据应用服务名模糊查询
     *
     * @param organizationId
     * @param appServiceName
     * @return
     */
    Set<Long> listC7nAppServiceIdsByNameOnOrgLevel(Long organizationId, String appServiceName);

    /**
     * 项目层
     * 分页查询应用服务信息
     *
     * @param projectId
     * @param pageRequest
     * @param repositoryIds
     * @return
     */
    PageInfo<C7nAppServiceVO> pageC7nAppServices(Long projectId, PageRequest pageRequest, Set<Long> repositoryIds);

    /**
     * 项目层
     * 获取项目的应用服务id对应Gitlab项目id的map
     *
     * @param projectId
     * @return
     */
    Map<Long, Long> listC7nAppServiceIdsMapOnProjectLevel(Long projectId);
}
