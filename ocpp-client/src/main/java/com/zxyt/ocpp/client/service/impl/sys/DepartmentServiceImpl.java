package com.zxyt.ocpp.client.service.impl.sys;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zxyt.ocpp.client.config.common.page.MybatisPage;
import com.zxyt.ocpp.client.config.common.universal.AbstractService;
import com.zxyt.ocpp.client.entity.sys.Department;
import com.zxyt.ocpp.client.mapper.sys.IDepartmentMapper;
import com.zxyt.ocpp.client.service.sys.IDepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 机构管理业务实现层
 * @Author: Liweidong
 * @Description:
 * @Date: Created in 16:29 2018-4-18
 * @Modified By:
 */
@Service("departmentService")
public class DepartmentServiceImpl extends AbstractService<Department> implements IDepartmentService {

    @Autowired
    private IDepartmentMapper departmentMapper;

    /**
     * 分页查询机构信息
     * @param map
     * @return
     */
    @Override
    public PageInfo<Department> selectAll(Map<String, Object> map) {
        MybatisPage.getPageSize(map);
        PageHelper.startPage(MybatisPage.page, MybatisPage.limit);
        List<Department> departmentList = this.departmentMapper.findAll(map);
        return new PageInfo<>(departmentList);
    }

    @Override
    public Department selectById(String id){
        return this.departmentMapper.selectById(id);
    }
}
