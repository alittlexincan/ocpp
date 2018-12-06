package com.zxyt.ocpp.client.controller.common;

import com.zxyt.ocpp.client.ueditor.ActionEnter;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * @Copyright (C), 2015-2018
 * @FileName: PageController
 * @Author:   JiangXincan
 * @Date:     2018-5-8 15:57
 * @Description: ${DESCRIPTION}
 * @History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名         修改时间          版本号             描述
 */
@Controller
@Component
public class PageController {


    /**
     * 进入框架界面
     * @return
     */
    @RequestMapping({"/","/index"})
    public ModelAndView index(@RequestParam Map<String, Object> map){
        return new ModelAndView("main/index",map);
    }

    /**
     * 进入主界面
     * @return
     */
    @RequestMapping("/home")
    public ModelAndView home(@RequestParam Map<String, Object> map){
        return new ModelAndView("main/home",map);
    }

    /**
     * 页面跳转通用方法
     * @param model 模块编码
     * @param name  模块名称
     * @return
     */
    @RequestMapping("/page/{model}/{name}")
    public ModelAndView page(@PathVariable("model") String model, @PathVariable("name") String name, @RequestParam Map<String, Object> map){
        return new ModelAndView(model + "/" + name, map);
    }

    /**
     *  页面跳转通用方法
     * @param model  模块目录
     * @param name   模块名称
     * @param option 传输数据
     * @return
     */
    @RequestMapping("/page/{model}/{name}/{option}")
    public ModelAndView page(@PathVariable("model") String model,@PathVariable("name") String name, @PathVariable("option") String option){
        Map<String, Object> map = new HashMap<>();
        map.put("id", option);
        return new ModelAndView(model + "/" + name, map);
    }

    @RequestMapping("/403")
    public String unauthorizedRole(){
        System.out.println("------没有权限-------");
        return "main/403";
    }

    /**
     * 员工登录信息
     * @param map
     * @return
     */
    @RequestMapping(value = "/login")
    String signIn(HttpServletRequest request, Map<String, Object> map){
        // 登录失败从request中获取shiro处理的异常信息。
        // shiroLoginFailure:就是shiro异常类的全类名.
        String exception = (String) request.getAttribute("shiroLoginFailure");
        String msg = "";
        if (exception != null) {
            if (UnknownAccountException.class.getName().equals(exception)) {
                msg = "账号不存在";
            }else if(AuthenticationException.class.getName().equals(exception)){
                msg = "用户名或密码错误";
            }else if (IncorrectCredentialsException.class.getName().equals(exception)) {
                msg = "密码不正确";
            } else if ("kaptchaValidateFailed".equals(exception)) {
                msg = "验证码错误";
            } else {
                msg = exception;
            }
        }
        map.put("msg", msg);
        // 此方法不处理登录成功,由shiro进行处理
        return "main/login";
    }

    @RequestMapping(value="/config")
    public void config(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("application/json");
        String rootPath = request.getSession().getServletContext().getRealPath("/");
        try {
            String exec = new ActionEnter(request, rootPath).exec();
            PrintWriter writer = response.getWriter();
            writer.write(exec);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
