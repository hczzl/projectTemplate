package com.glch.login.controller;


import com.glch.base.common.JsonResponse;
import com.glch.base.controller.BaseController;
import com.glch.base.util.StringUtil;
import com.glch.log.entity.LogSystem;
import com.glch.log.entity.LogUserOpt;
import com.glch.log.enums.LogLevelEnum;
import com.glch.log.enums.OptTypeEnum;
import com.glch.log.service.ILogSystemService;
import com.glch.log.service.ILogUserOptService;
import com.glch.login.entity.vo.LoginVo;
import com.glch.login.service.ILoginService;
import com.glch.login.service.ILoginTokenService;
import com.glch.system.user.entity.User;
import com.glch.system.user.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.Map;

/**
 * <p>
 * 登录相关接口
 * </p>
 *
 * @author wenchaochao
 * @since 2020-04-05
 */
@RestController
@RequestMapping("/login/")
@Api(value = "loginController", tags = "登录相关接口")
public class LoginController extends BaseController {

    @Autowired
    private IUserService userService;
    @Autowired
    private ILogUserOptService logUserOptService;
    @Autowired
    private ILogSystemService logSystemService;
    @Autowired
    private ILoginService loginService;
    @Autowired
    private ILoginTokenService loginTokenService;


    @RequestMapping(value = "/checkLogin", method = {RequestMethod.POST})
    @ApiOperation(value = "验证登录", response = LoginVo.class)
    public JsonResponse checkLogin(HttpServletRequest request, @RequestBody LoginVo loginVo) {
        String loginName = loginVo.getLoginName();
        String pwd = loginVo.getPwd();
        try {
            if (StringUtil.isEmpty(loginName) || StringUtil.isEmpty(pwd)) {
                return JsonResponse.error("用户名或密码不能为空!");
            }
           /* boolean success = loginService.validateAuthorization();
            if (success == false) {
                return JsonResponse.error("登录失败，授权过期，请找管理员重新授权");
            }*/
            User user = userService.getUserByLoginName(loginName, pwd);
            //目前没有设备，先不验证设备id
            /*if (null == user || (null != user && StringUtil.isEmpty(user.getDeviceId()))) {
            return JsonResponse.error("用户名或者密码错误");
            }*/
            if (null == user) {
                return JsonResponse.error("用户名或者密码错误");
            }
            Map<String, Object> userMap = loginService.checkLogin(user, request);
            LogUserOpt logUserOpt = logUserOptService.getLog(request, OptTypeEnum.LOGIN, "验证登录", null, loginVo.toString(), false);
            logUserOpt.setCreator(user.getId());
            logUserOpt.setCreateTime(new Date());
            logUserOptService.insertLog(logUserOpt);
            LogSystem logSystem = logSystemService.getLog(request, "验证登录", null, LogLevelEnum.INFO, null, loginVo.toString(), false);
            logSystem.setCreator(user.getId());
            logSystem.setCreateTime(new Date());
            logSystemService.insertLog(logSystem);
            return JsonResponse.ok("登录成功", userMap);
        } catch (Exception e) {
            e.printStackTrace();
            return JsonResponse.error("服务器异常");
        }
    }

    @RequestMapping(value = "/logout", method = {RequestMethod.POST})
    @ApiOperation("退出登录")
    public JsonResponse logout(HttpServletRequest request) {
        String token = request.getHeader("token");
        //String token = "20200515100510870f5e7a3b0514d4695813c970a1b5959ee";
        if (StringUtil.isEmpty(token)) {
            return JsonResponse.error("退出失败");
        }
        //删除token
        loginTokenService.deleteToken(token);
        //清空session
        HttpSession session = request.getSession();
        session.invalidate();
        return JsonResponse.ok("退出成功");
    }

    @RequestMapping(value = "/checkOnline", method = {RequestMethod.POST})
    @ApiOperation("验证登录用户的状态")
    public JsonResponse checkOnline(HttpServletRequest request) {
        Integer state = loginService.loginState();
        if (state == null) {
            return JsonResponse.error("查询失败");
        } else if (state == 1) {
            return JsonResponse.ok("退出时间过长，请重新登录", state);
        }
        return JsonResponse.ok("查询成功", state);
    }
}

