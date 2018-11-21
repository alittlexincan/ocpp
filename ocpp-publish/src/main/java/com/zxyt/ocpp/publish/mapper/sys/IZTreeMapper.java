package com.zxyt.ocpp.publish.mapper.sys;


import com.zxyt.ocpp.publish.config.common.universal.IBaseMapper;
import com.zxyt.ocpp.publish.entity.sys.ZTree;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


/**
 * ZTree树数据访问层
 * @Author: JiangXincan
 * @Description:
 * @Date: Created in 16:30 2018-4-18
 * @Modified By:
 */
@Repository("zTreeMapper")
public interface IZTreeMapper extends IBaseMapper<ZTree> {

    /**
     * 查询菜单数据
     * @param map
     * @return
     */
    List<ZTree> getMenuTree(Map<String, Object> map);

    /**
     * 查询地区数据
     * @param map
     * @return
     */
    List<ZTree> getAreaTree(Map<String, Object> map);

    /**
     * 查询机构树
     * @param map
     * @return
     */
    List<ZTree> getOrganizationTree(Map<String, Object> map);

    /**
     * 查询灾种数据
     * @param map
     * @return
     */
    List<ZTree> getDisasterTree(Map<String, Object> map);

    /**
     * 查询灾种级别树
     * @param map
     * @return
     */
    List<ZTree> getDisasterLevelTree(Map<String, Object> map);

    /**
     * 查询用户组树
     * @param map
     * @return
     */
    List<ZTree> getUserGroupTree(Map<String, Object> map);

    /**
     * 查询用户组对用受众个数树
     * @param map
     * @return
     */
    List<ZTree> getUserGroupCountTree(Map<String, Object> map);

    /**
     * 查询机构对应群组树
     * @param map
     * @return
     */
    List<ZTree> getOrganizationUserGroupTree(Map<String, Object> map);

}
