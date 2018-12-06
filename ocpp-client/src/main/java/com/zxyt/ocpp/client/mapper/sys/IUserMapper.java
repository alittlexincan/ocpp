package com.zxyt.ocpp.client.mapper.sys;

import com.zxyt.ocpp.client.config.common.universal.IBaseMapper;
import com.zxyt.ocpp.client.entity.sys.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


/**
 * @Author: JiangXincan
 * @Description:
 * @Date: Created in 16:30 2018-4-18
 * @Modified By:
 */
@Repository("userMapper")
public interface IUserMapper extends IBaseMapper<User> {

    /**
     * 分页查询地区信息
     * @param map
     * @return
     */
    List<User> findAll(Map<String, Object> map);

    /**
     * 根据地区id查询地区信息
     * @param id
     * @return
     */
    User selectById(@Param(value = "id") String id);

    /**
     * 插入用户受众表
     * @param map
     * @return
     */
    int insertUser(Map<String,Object> map);
    /**
     * 插入用户职务表
     * @param map
     * @return
     */
    int insertUserJob(Map<String,Object> map);

    int updateUser(Map<String,Object> map);

    int deleteUserJobById(String id);

    int deleteUserJobByIds(Map<String, Object> map);

    List<User> selectList(Map<String, Object> map);

    List<User> userDetails(Map<String,Object> map);
}
