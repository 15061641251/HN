package com.sbq.web.controller;

import com.sbq.annotation.RequestLog;
import com.sbq.entity.MenusTreeObject;
import com.sbq.entity.User;
import com.sbq.entity.dto.UserDto;
import com.sbq.service.IAuthorityService;
import com.sbq.service.IUserService;
import com.sbq.tools.Constant;
import com.sbq.tools.MenusTreeUtil;
import com.sbq.tools.NullUtil;
import com.sbq.tools.crypto.MD5Utils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.net.URLDecoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/user")
public class UserController extends BaseController {

    @Autowired
    private IUserService userService = null;

    @Autowired
    private IAuthorityService authService = null;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(HttpServletRequest request, HttpSession session, @RequestParam(value = "redirect_url", required = false) String redirect_url) {

        if (session.getAttribute(Constant.SESSION_ATTRIBUTE_USER) != null && StringUtils.isNoneBlank(redirect_url)) {
            return "redirect:" + URLDecoder.decode(redirect_url);
        }

        if (session.getAttribute(Constant.SESSION_ATTRIBUTE_USER) != null && StringUtils.isBlank(redirect_url)) {
            return "redirect:/czcg";
        }

        request.setAttribute("redirect_url", redirect_url);
        return "user/login";
    }

    @RequestLog(moduleName = "登出")
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:login";
    }

    @RequestMapping(value = "/main", method = RequestMethod.GET)
    public String main(HttpSession session) {
        if (session.getAttribute(Constant.SESSION_ATTRIBUTE_USER) == null) {
            return "redirect:/";
        }

        return "main";
    }

    @RequestLog(moduleName = "登陆请求")
    @RequestMapping(value = "/loginCheck")
    public String login(HttpServletRequest request, HttpServletResponse response, HttpSession session,
                        @RequestParam(value = "account", required = false) String account, @RequestParam(value = "password", required = false) String password,
                        @RequestParam(value = "redirect_url", required = false) String redirect_url) throws Exception {

        String view = "";
        if (StringUtils.isNotEmpty(account) && StringUtils.isNotEmpty(password)) {
            Map<String, Object> paramsMap = new HashMap<String, Object>();

            paramsMap.put("account", account);
            paramsMap.put("password", MD5Utils.encrypt(password));


            UserDto user = userService.getInfoByMap(paramsMap);
            if (user != null) {
                // 判断该用户所在单位是否被禁用
                if (user.getIs_enabled() == 1) {

                    User newUser = new User();
                    newUser.setInt_id(user.getInt_id());
                    newUser.setPc_log_last_time(new Date());
                    userService.updateUser(newUser);

                    session.setAttribute(Constant.SESSION_ATTRIBUTE_USER, user);

                    Map<String, Object> parammap = new HashMap<>();

                    String authority_id = user.getAuth_ids();

                    //获取菜单树
                    List<MenusTreeObject> menuTree = null;
                    if (!NullUtil.isNull(authority_id)) {
                        parammap.put("authority_id", authority_id);
                        parammap.put("type", 1);//菜单

                        List<MenusTreeObject> list = authService.getMenusByMap(parammap);

                        menuTree = MenusTreeUtil.getChildTreeObjects(list, 0);
                    }

                    // 因为我们约定了第一个是主菜单，其他都是下属的，所以这边只有1个元素(主菜单)，我们要其下属菜单
                    if (menuTree != null && menuTree.size() > 0 && menuTree.get(0).getChildren().size() > 0) {
                        List<MenusTreeObject> Menus = menuTree.get(0).getChildren();
                        session.setAttribute(Constant.MENU_IDS, jacksonObjectMapper.writeValueAsString(Menus));
                    } else {
                        session.setAttribute(Constant.MENU_IDS, null);
                    }

                    if (StringUtils.isBlank(redirect_url)) {
                        view = "redirect:/czcg";
                    } else {
                        //通过其他页面进入的登陆界面
                        return "redirect:" + URLDecoder.decode(redirect_url);
                    }


                } else {
                    request.setAttribute("message", "该用户被禁用，请联系管理员");
                    view = "user/login";
                }
            } else {
                // 用户名或密码错误
                request.setAttribute("message", "用户名或密码错误");
                view = "user/login";
            }
        } else {
            request.setAttribute("message", "用户名密码不能为空");
            view = "user/login";
        }
        return view;
    }
}
