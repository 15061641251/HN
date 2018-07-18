package com.sbq.web.controller;

import com.github.pagehelper.PageInfo;
import com.sbq.annotation.RequestLog;
import com.sbq.dao.IAuthResDao;
import com.sbq.dao.IAuthorityDao;
import com.sbq.dao.IResourcesDao;
import com.sbq.entity.AuthResources;
import com.sbq.entity.Authority;
import com.sbq.entity.Resources;
import com.sbq.entity.User;
import com.sbq.entity.dto.UserDto;
import com.sbq.service.IAuthorityService;
import com.sbq.service.IResourcesService;
import com.sbq.tools.Constant;
import com.sbq.tools.NullUtil;
import com.sbq.tools.ResourcesTreeUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/auth")
public class AuthorityController extends BaseController {

    @Autowired
    private IAuthorityService authorityService = null;

    @Autowired
    private IAuthorityDao authorityDao = null;

    @Autowired
    private IResourcesService resourcesService = null;

    @Autowired
    private IResourcesDao resourcesDao = null;

    @Autowired
    private IAuthResDao authResDao = null;

    @RequestMapping("/authManager")
    public String authManager() {
        return "auth/authManager";
    }

    @RequestLog(moduleName = "获取权限列表")
    @RequestMapping("/getAuthListByPage")
    @ResponseBody
    public Object getAuthListByPage(@Param("pageIndex") int pageIndex, @Param("pageSize") int pageSize, @Param("name") String name, HttpSession session) throws UnsupportedEncodingException {
        // 获取当前用户所在的公司主键
        UserDto user = (UserDto) session.getAttribute(Constant.SESSION_ATTRIBUTE_USER);

        Map<String, Object> parammap = new HashMap<String, Object>();

        parammap.put("pageIndex", pageIndex);
        parammap.put("pageSize", pageSize);

        if (StringUtils.isNotEmpty(name)) {
            name = URLDecoder.decode(name, "UTF-8");
            parammap.put("name", name);
        }

        PageInfo<Authority> pageInfo = authorityService.getAuthListByPage(parammap);
        return renderSuccess(pageInfo);

    }

    @RequestLog(moduleName = "权限详情")
    @RequestMapping("/authDetail")
    public String authDetail(@Param("authId") String authId, HttpServletRequest request) {

        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("authId", authId);

        // 该权限具有的资源
        List<Resources> list = resourcesService.getResourcesDetailByMap(paramMap);

        // 所有资源
        Example example = new Example(Resources.class);
        example.orderBy("ordernum").asc();
        example.createCriteria().andEqualTo("is_enabled", 1);

        List<Resources> resList = resourcesDao.selectByExample(example);

        // 以上两者进行对比，目前是设置check属性
        for (int i = 0; i < list.size(); i++) {
            Resources resources1 = list.get(i);

            for (int j = 0; j < resList.size(); j++) {
                Resources resources2 = resList.get(j);
                if (resources1.getInt_id() == resources2.getInt_id()) {
                    // 相等证明该权限中具有此资源，需设置checked属性
                    resources2.setIs_checked(true);
                    break;
                }
            }
        }

        List<Resources> resTree = ResourcesTreeUtil.getChildTreeObjects(resList, 0);

        // 因为我们约定了第一个是主菜单，其他都是下属的，所以这边只有1个元素(主菜单)，我们要其下属菜单
        if (resTree != null && resTree.size() > 0 && resTree.get(0).getChildren().size() > 0) {
            request.setAttribute("res_detail", resTree.get(0).getChildren());
            request.setAttribute("authority_name", list.get(0).getAuthority_name());
            request.setAttribute("authority_description", list.get(0).getAuthority_description());
        } else {
            request.setAttribute("res_detail", null);

        }

        return "/auth/authDetail";
    }

    @RequestMapping("/getAllAuthInfo")
    public
    @ResponseBody
    Map<String, Object> getAllInfo(@Param("authId") String authId) {

        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("authId", authId);

        // 该权限具有的资源
        List<Resources> list = resourcesService.getResourcesDetailByMap(paramMap);

        // 所有资源
        Example example = new Example(Resources.class);
        example.orderBy("ordernum").asc();
        example.createCriteria().andEqualTo("is_enabled", 1);

        List<Resources> resList = resourcesDao.selectByExample(example);

        // 以上两者进行对比，目前是设置check属性
        for (int i = 0; i < list.size(); i++) {
            Resources resources1 = list.get(i);

            for (int j = 0; j < resList.size(); j++) {
                Resources resources2 = resList.get(j);
                if (resources1.getInt_id() == resources2.getInt_id()) {
                    // 相等证明该权限中具有此资源，需设置checked属性
                    resources2.setIs_checked(true);
                    break;
                }
            }
        }

        List<Resources> resTree = ResourcesTreeUtil.getChildTreeObjects(resList, 0);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        // 因为我们约定了第一个是主菜单，其他都是下属的，所以这边只有1个元素(主菜单)，我们要其下属菜单
        if (resTree != null && resTree.size() > 0 && resTree.get(0).getChildren().size() > 0) {
            resultMap.put("res_detail", resTree.get(0).getChildren());
            if (list.size() > 0) {
                resultMap.put("authority_name", list.get(0).getAuthority_name());
                resultMap.put("authority_description", list.get(0).getAuthority_description());
            }
        } else {
            resultMap.put("res_detail", null);
        }

        return resultMap;
    }

    @RequestMapping("/editAuth")
    public String editAuth(@Param("authId") String authId) {
        return "/auth/editAuth";
    }

    @RequestLog(moduleName = "删除权限")
    @RequestMapping(value = "/delAuth")
    public
    @ResponseBody
    Map<String, String> delAuth(@Param("authId") String authId) {

        Map<String, String> resultMap = new HashMap<String, String>();

        if (StringUtils.isEmpty(authId)) {
            resultMap.put("success", "0");
            resultMap.put("description", "请求参数有误");
        } else {
            // 删除成功
            authorityService.delAuthById(authId);
            resultMap.put("success", "1");
        }

        return resultMap;
    }

    @RequestMapping(value = "/addAuth")
    public String authAdd() {
        return "/auth/addAuth";
    }

    @RequestMapping(value = "/getAllAuthReources")
    public
    @ResponseBody
    List<Resources> getAllAuthReources() {

        // 所有资源
        Example example = new Example(Resources.class);
        example.orderBy("ordernum").asc();
        example.createCriteria().andEqualTo("is_enabled", 1);

        List<Resources> resList = resourcesDao.selectByExample(example);

        List<Resources> resTree = ResourcesTreeUtil.getChildTreeObjects(resList, 0);

        // 手动排除约定好的主菜单
        if (resTree != null && resTree.size() > 0 && resTree.get(0).getChildren().size() > 0) {
            return resTree.get(0).getChildren();
        }
        return null;

    }

    @RequestLog(moduleName = "添加权限")
    @RequestMapping(value = "/addAuthPost")
    @Transactional
    public
    @ResponseBody
    Map<String, String> addAuthPost(@Param("authority") Authority authority, HttpSession session) {

        Map<String, String> resultMap = new HashMap<String, String>();

        if (NullUtil.isBlank(authority.getAuth_ids(), authority.getName())) {
            resultMap.put("success", "0");
            resultMap.put("description", "请求参数有误");

        } else {

            authorityService.addAuth(authority);

            resultMap.put("success", "1");
        }

        return resultMap;

    }

    @RequestLog(moduleName = "修改权限")
    @RequestMapping(value = "/editAuthPost", method = RequestMethod.POST)
    public
    @ResponseBody
    Map<String, String> editAuthPost(@Param("authId") String authId, HttpServletRequest request,
                                     HttpSession session, @ModelAttribute("Authority") Authority authority) {
        Map<String, String> resultMap = new HashMap<String, String>();

        String name = request.getParameter("name");
        String int_ids = request.getParameter("int_ids");
        if (StringUtils.isEmpty(int_ids) || StringUtils.isEmpty(name) || StringUtils.isEmpty(authId)) {
            resultMap.put("success", "0");
            resultMap.put("description", "请求参数有误");
        } else {

            authority.setInt_id(Integer.valueOf(authId));

            authority.setAuth_ids(int_ids);
            authorityService.updateAuth(authority);


            resultMap.put("success", "1");
        }

        return resultMap;
    }
}
