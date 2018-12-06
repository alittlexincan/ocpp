package com.zxyt.ocpp.client.config.shiro;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zxyt.ocpp.client.service.sys.IEmployeeService;
import com.zxyt.ocpp.client.service.sys.IRoleService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

public class ShiroRealm extends AuthorizingRealm {

    @Resource
    private IEmployeeService employeeService;

    @Resource
    private IRoleService roleService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        JSONObject userInfo  = (JSONObject)principals.getPrimaryPrincipal();
        JSONArray roles = userInfo.getJSONArray("roles");
        JSONArray permissions = userInfo.getJSONArray("permissions");
        // 角色注入shiro
        roles.forEach(r -> {
            JSONObject role = (JSONObject) r;
            authorizationInfo.addRole(role.getString("role"));
        });
        // 权限注入shiro
        permissions.forEach(p -> {
            JSONObject permission = (JSONObject) p;
            authorizationInfo.addStringPermission(permission.getString("permission"));
        });
        return authorizationInfo;
    }

    /*主要是用来进行身份认证的，也就是说验证用户输入的账号和密码是否正确。*/
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

        String loginName = (String) token.getPrincipal();

        if(loginName == null) return null;
        //实际项目中，这里可以根据实际情况做缓存，如果不做，Shiro自己也是有时间间隔机制，2分钟内不会重复执行该方法
        Map<String, Object> param = new HashMap<>();
        param.put("loginName", loginName);
        JSONObject employee = this.employeeService.login(param);
        if(employee == null) return null;
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                employee, //用户名
                employee.getString("loginPassword"), //密码
                ByteSource.Util.bytes(employee.getString("loginName")),//salt=username+salt
                getName()  //realm name
        );
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        session.setAttribute("employee", employee);
        employee.remove("loginPassword");

        JSONArray roles = employee.getJSONArray("roles");
        StringBuilder sb = new StringBuilder();
        if(roles.size() > 0){
            roles.forEach(r -> {
                JSONObject role = (JSONObject) r;
                sb.append("," + role.getString("id"));
            });
            Map<String, Object> map = new HashMap<>();
            map.put("roleId",sb.toString().substring(1));
            JSONArray menus = this.roleService.selectRoleInMenu(map);
            session.setAttribute("menus", menus);
        }else {
            session.setAttribute("menus", null);
        }

        return authenticationInfo;
    }

}