package com.zxyt.ocpp.publish.service.impl.sys;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zxyt.ocpp.publish.config.common.page.MybatisPage;
import com.zxyt.ocpp.publish.config.common.universal.AbstractService;
import com.zxyt.ocpp.publish.entity.sys.User;
import com.zxyt.ocpp.publish.mapper.sys.IUserMapper;
import com.zxyt.ocpp.publish.service.sys.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: XincanJiang
 * @Description:
 * @Date: Created in 16:29 2018-4-18
 * @Modified By:
 */
@Service("userService")
public class UserServiceImpl extends AbstractService<User> implements IUserService {

    @Autowired
    private IUserMapper userMapper;

    @Override
    public PageInfo<User> selectAll(Map<String, Object> map) {
        MybatisPage.getPageSize(map);
        PageHelper.startPage(MybatisPage.page, MybatisPage.limit);
        List<User> areaList = this.userMapper.findAll(map);
        return new PageInfo<>(areaList);
    }

    @Override
    public User selectById(String id){
        return this.userMapper.selectById(id);
    }


    @Override
    public int insertUser(Map<String, Object> map) {

        return this.userMapper.insertUser(map);
    }

    @Override
    public int insertUserJob(Map<String, Object> map) {

        return this.userMapper.insertUserJob(map);
    }

    @Override
    public int updateUser(Map<String, Object> map) {

        return this.userMapper.updateUser(map);
    }

    @Override
    public int deleteUserJobById(String id) {
        return this.userMapper.deleteUserJobById(id);
    }

    @Override
    public int deleteUserJobByIds(String id) {
        Map<String,Object> map=new HashMap<>();
        map.put("id",id);
        return this.userMapper.deleteUserJobByIds(map);
    }

    @Override
    public List<User> selectList(Map<String, Object> map) {
        return this.userMapper.selectList(map);
    }

    @Override
    public JSONObject userDetails(Map<String, Object> map) {
        JSONObject json=new JSONObject();
        List<User> list=this.userMapper.userDetails(map);
        json.put("list",list);
        return json;
    }
}
