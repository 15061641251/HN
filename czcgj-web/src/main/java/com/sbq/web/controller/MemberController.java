package com.sbq.web.controller;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.sbq.annotation.RequestLog;
import com.sbq.entity.Authority;
import com.sbq.entity.User;
import com.sbq.entity.UserAuth;
import com.sbq.entity.dto.UserDto;
import com.sbq.service.IAuthorityService;
import com.sbq.service.IUserAuthService;
import com.sbq.service.IUserService;
import com.sbq.tools.Constant;
import com.sbq.tools.EasemobIMUsers;
import com.sbq.tools.NullUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.net.URLDecoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/member")
public class MemberController extends BaseController {
    @Autowired
    private IUserService userService;

    @Autowired
    private IAuthorityService authorityService;

    @Autowired
    private IUserAuthService userAuthService;

    @RequestMapping(value = "manager")
    public String deptManager() {
        return "member/memberManager";
    }

    @RequestMapping(value = "/editMember")
    public String editMember(@Param("userId") String userId) {
        return "member/editMember";
    }

    @RequestMapping(value = "/editPwdJsp")
    public String editPwdFtl(HttpServletRequest request) {
        return "member/editPwd";
    }

    @RequestLog(moduleName = "获取用户列表")
    @RequestMapping(value = "/getUserListByPage")
    public
    @ResponseBody
    Object getUserListByPage(@Param("pageIndex") int pageIndex, @Param("pageSize") int pageSize,
                             @Param("user_name") String user_name, @Param("company_name") String company_name, @Param("area_name") String area_name,
                             @Param("dept_id") String dept_id, HttpSession session) throws Exception {


        UserDto userDto = (UserDto) session.getAttribute(Constant.SESSION_ATTRIBUTE_USER);

        Map map = new HashMap();

        map.put("pageIndex", pageIndex);
        map.put("pageSize", pageSize);

        if (userDto.getCompany_id() != null) {
            map.put("company_id", userDto.getCompany_id());
        }
        if (StringUtils.isNotBlank(dept_id)) {
            map.put("dept_id", dept_id);
        }
        if (StringUtils.isNotBlank(user_name)) {
            user_name = URLDecoder.decode(user_name, "utf-8");
            map.put("username", user_name);
        }
        if (StringUtils.isNotBlank(company_name)) {
            company_name = URLDecoder.decode(company_name, "utf-8");
            map.put("company_name", company_name);
        }
        if (StringUtils.isNotBlank(area_name)) {
            area_name = URLDecoder.decode(area_name, "utf-8");
            map.put("area_name", area_name);
        }
        return renderSuccess(userService.selectUserDtoByMap(map));
    }

    @RequestLog(moduleName = "查看用户详细")
    @RequestMapping(value = "/showMember")
    public String showMember(@Param("int_id") String int_id, HttpServletRequest request) {
        Map<String, Object> parammap = new HashMap<String, Object>();
        parammap.put("int_id", Long.parseLong(int_id));
        UserDto user = userService.getInfoByMap(parammap);

        Map<String, Object> param = new HashMap<String, Object>();

        // 角色
        param.clear();
        param.put("user_id", int_id);
        List<UserAuth> uaList = this.userAuthService.getUserAuthInfo(param);
        if (uaList.size() > 0) {
            String auth_names = "";
            for (UserAuth ua : uaList) {
                auth_names += ua.getAuth_name() + ",";
            }
            user.setAuth_names(auth_names.substring(0, auth_names.length() - 1));
        }
        request.setAttribute("user", user);
        return "member/showMember";
    }

    @RequestMapping("getMyInfo")
    @ResponseBody
    public Object getMyInfo(HttpSession session) {

        UserDto userDto = (UserDto) session.getAttribute(Constant.SESSION_ATTRIBUTE_USER);

        return renderSuccess(userDto);
    }

    @RequestMapping(value = "/getMemberByUserId")
    public
    @ResponseBody
    Map<String, Object> getMemberByUserId(@Param("userId") String userId) {
        Map<String, Object> map = new HashMap<String, Object>();

        Map<String, Object> parammap = new HashMap<String, Object>();
        parammap.put("int_id", Long.parseLong(userId));
        UserDto user = userService.getInfoByMap(parammap);
        map.put("password", user.getPassword());
        map.put("username", user.getUsername());
        map.put("account", user.getAccount());
        map.put("dept_id", user.getDept_id());
        map.put("dept_name", user.getDept_name());
        map.put("sex", user.getSex());
        map.put("mobile", user.getMobile());
        map.put("mobile2", user.getMobile2());
        map.put("tel", user.getTel());
        map.put("is_enabled", user.getIs_enabled());
        map.put("is_admin", user.getIs_admin());
        map.put("company_id", user.getCompany_id());
        map.put("company_name", user.getCompany_name());
        map.put("area_id", user.getArea_id());
        map.put("area_name", user.getArea_name());

        Map<String, Object> param = new HashMap<String, Object>();

        // 角色
        param.clear();
        param.put("user_id", user.getInt_id());
        List<UserAuth> uaList = this.userAuthService.getUserAuthInfo(param);
        if (uaList.size() > 0) {
            StringBuilder auth_ids = new StringBuilder();
            for (UserAuth ua : uaList) {
                auth_ids.append(ua.getAuth_id()).append(",");
            }
            map.put("auth_ids", auth_ids.substring(0, auth_ids.length() - 1));
        } else {
            map.put("auth_ids", "");
        }
        return map;
    }

    @RequestLog(moduleName = "修改用户信息")
    @RequestMapping(value = "/editMemberById")
    public
    @ResponseBody
    Map<String, String> editMemberById(@Param("userId") String userId, @RequestBody UserDto newUser) {
        Map<String, String> resultMap = new HashMap<>();

        long dept_id = newUser.getDept_id();
        String username = newUser.getUsername();

        String account = newUser.getAccount();
        String password = newUser.getPassword();
        String auth_ids = newUser.getAuth_ids();
        int is_enabled = newUser.getIs_enabled();

        if (NullUtil.isBlank(username, account, password, auth_ids) || NullUtil.isNull(dept_id) || NullUtil.isNull(is_enabled)) {
            resultMap.put("success", "0");
            resultMap.put("description", "请填写完整");
        } else {
            // 修改用户信息
            User user = userService.selectByPrimaryKey(Long.parseLong(userId));

            user.setTime_stamp(new Date());
            user.setDept_id(dept_id);
            user.setAccount(account);
            user.setUsername(username);
            user.setPassword(password);
            user.setEmail(newUser.getEmail());
            user.setSex(newUser.getSex());
            user.setMobile(newUser.getMobile());
            user.setMobile2(newUser.getMobile2());
            user.setTel(newUser.getTel());
            user.setIs_enabled(is_enabled);
            user.setIs_admin(newUser.getIs_admin());
            user.setCompany_id(newUser.getCompany_id());

            user.setArea_id(newUser.getArea_id());

            this.userService.updateUser(user);

            // 修改或新增数据权限
            Map<String, Object> param = new HashMap<String, Object>();

            // 修改角色
            String[] auths = auth_ids.split(",");
            if (auths.length > 0) {
                // 删除原有角色
                Example example = new Example(UserAuth.class);
                example.createCriteria().andEqualTo("user_id", userId);
                this.userAuthService.deleteByExample(example);

                // 加入新角色
                this.userAuthService.insertSelectiveByAuthsAndUser_id(auths, Long.parseLong(userId));

            }

            // 删除旧环信用户，创建新的
            EasemobIMUsers.deleteIMUserByuserName(user.getAccount());
            ObjectNode datanode = JsonNodeFactory.instance.objectNode();
            datanode.put("username", user.getAccount());
            datanode.put("password", Constant.DEFAULT_PASSWORD);
            EasemobIMUsers.createNewIMUserSingle(datanode);

            resultMap.put("success", "1");
        }

        return resultMap;
    }

    @RequestLog(moduleName = "删除用户")
    @RequestMapping(value = "/delUser")
    public
    @ResponseBody
    Map<String, String> delUser(@Param("userId") String userId) {

        Map<String, String> resultMap = new HashMap<String, String>();

        if (StringUtils.isEmpty(userId)) {
            resultMap.put("success", "0");
            resultMap.put("description", "请求参数有误");
        } else {
            User user = this.userService.selectByPrimaryKey(Long.parseLong(userId));
            if (!NullUtil.isNull(user)) {
                // 删除成功
                this.userService.deleteByPrimaryKey(Long.parseLong(userId));
                // 删除环信中的该用户
                EasemobIMUsers.deleteIMUserByuserName(user.getAccount());

                resultMap.put("success", "1");
            } else {
                resultMap.put("success", "0");
            }
        }

        return resultMap;
    }

    @RequestMapping(value = "/addMember")
    public String addMember(@Param("dept_id") String dept_id) {
        return "member/addMember";
    }

    @RequestLog(moduleName = "添加用户")
    @RequestMapping(value = "/addMemberPost", method = RequestMethod.POST)
    public
    @ResponseBody
    Map<String, String> addMemberPost(@ModelAttribute("UserDto") UserDto user, HttpSession session) {

        UserDto userDto = (UserDto) session.getAttribute(Constant.SESSION_ATTRIBUTE_USER);

        Map<String, String> resultMap = new HashMap<String, String>();

        if (StringUtils.isEmpty(user.getAccount())) {
            resultMap.put("success", "0");
            resultMap.put("description", "参数有误");
        } else {
            User newUser = new User();
            newUser.setCreate_time(new Date());
            newUser.setTime_stamp(new Date());

            newUser.setDept_id(user.getDept_id());
            newUser.setAccount(user.getAccount());
            newUser.setPassword(user.getPassword());
            newUser.setUsername(user.getUsername());
            newUser.setSex(user.getSex());
            newUser.setTel(user.getTel());
            newUser.setMobile(user.getMobile());
            newUser.setMobile2(user.getMobile2());
            newUser.setIs_enabled(user.getIs_enabled());
            newUser.setEmail(user.getEmail());
            newUser.setIs_admin(user.getIs_admin());

            newUser.setCompany_id(userDto.getCompany_id());

            newUser.setArea_id(userDto.getArea_id());

            int count = 0;
            try {
                // 防止插入已存在用户名，程序报错
                count = this.userService.insertSelective(newUser);
            } catch (Exception e) {
                resultMap.put("success", "0");
                resultMap.put("description", "用户账户已被使用");
                return resultMap;
            }

            if (count == 1) {

                // 插入成功后添加用户角色
                String[] auths = user.getAuth_ids().split(",");
                if (auths.length > 0) {
                    Long a = newUser.getInt_id();
                    this.userAuthService.insertSelectiveByAuthsAndUser_id(auths, a);
                }

                /**
                 * 注册IM用户[单个]
                 //				 */
                ObjectNode datanode = JsonNodeFactory.instance.objectNode();
                datanode.put("username", user.getAccount());
                datanode.put("password", Constant.DEFAULT_PASSWORD);
                EasemobIMUsers.createNewIMUserSingle(datanode);

                resultMap.put("success", "1");
            }
        }

        return resultMap;
    }

    @RequestMapping(value = "/changestatus", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, String> changestatus(@Param("userId") String userId, @Param("is_enabled") String is_enabled) {
        Map<String, String> resultMap = new HashMap<String, String>();
        if (NullUtil.isNull(userId) || NullUtil.isNull(is_enabled)) {
            resultMap.put("success", "0");
            resultMap.put("description", "请求参数有误");
        } else {
            User user = userService.selectByPrimaryKey(Long.parseLong(userId));
            user.setTime_stamp(new Date());
            if (!NullUtil.isBlank(is_enabled)) {
                if (Integer.parseInt(is_enabled) == 0) {
                    user.setIs_enabled(0);
                } else if (Integer.parseInt(is_enabled) == 1) {
                    user.setIs_enabled(1);
                }
            }
            this.userService.updateUser(user);
            resultMap.put("success", "1");
        }
        return resultMap;
    }

    @RequestLog(moduleName = "获取权限列表")
    @RequestMapping(value = "/getAuthList")
    @ResponseBody
    public Map<String, Object> getAuthList() {
        Map<String, Object> map = new HashMap<String, Object>();

        List<Authority> list = authorityService.selectAll();
        map.put("success", "1");
        map.put("list", list);
        return map;
    }

    @RequestLog(moduleName = "修改密码")
    @RequestMapping(value = "/editPassWord")
    public
    @ResponseBody
    String editPassWord(@Param("oldPwd") String oldPwd, @Param("newPwd") String newPwd, HttpSession session) {

        String msg = "";

        if (!NullUtil.isBlank(oldPwd, newPwd)) {
            UserDto user = (UserDto) session.getAttribute(Constant.SESSION_ATTRIBUTE_USER);

            if (oldPwd.equals(user.getPassword())) {
                User newUser = new User();
                newUser.setInt_id(user.getInt_id());
                newUser.setTime_stamp(new Date());
                newUser.setPassword(newPwd);
                userService.updateUser(newUser);
                msg = "{success}";
            }
        } else {
            msg = "{failure}";
        }
        return msg;
    }
}
