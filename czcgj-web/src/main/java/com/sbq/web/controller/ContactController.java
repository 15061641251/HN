package com.sbq.web.controller;

import com.sbq.annotation.RequestLog;
import com.sbq.service.IUserService;
import com.sbq.tools.NullUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 后台通讯录
 *
 * @author ZhuYanBing
 * @create 2017-04-24 9:48
 */

@Controller
@RequestMapping(value = "/contact")
public class ContactController extends BaseController {


    @Autowired
    private IUserService userService;
    private HttpServletRequest request;

    /**
     * 通讯录浏览界面
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(HttpServletRequest request) {
        return "contact/list";
    }

    @RequestLog(moduleName = "获取通讯录列表")
    @RequestMapping(value = "/getContacts", method = RequestMethod.GET)
    @ResponseBody
    public Object getContacts(HttpServletRequest request) {

        Object pageNum = request.getParameter("pageNum");
        String searchTitle = request.getParameter("searchTitle");
        String DeptId = request.getParameter("DeptId");

        int currentPage = 1;
        if (null != pageNum && !("".equals(pageNum.toString()))) {
            currentPage = new Integer(pageNum.toString());
        }
        Map map = new HashMap();

        map.put("pageIndex", currentPage);
        map.put("pageSize", 5);

        if (!NullUtil.isBlank(DeptId)) {
            map.put("dept_id", DeptId);
        }
        if (!NullUtil.isBlank(searchTitle)) {
            map.put("searchTitle", searchTitle);
        }
        return renderSuccess(userService.selectUserDtoByMap(map));
    }

    @RequestLog(moduleName = "查看通讯录详情")
    @RequestMapping(value = "/getContactDetail", method = RequestMethod.GET)
    @ResponseBody
    public Object getContactDetail(HttpServletRequest request) {

        String ContactId = request.getParameter("ContactId");

        if (NullUtil.isBlank(ContactId)) {
            return renderRequestError();
        } else {
            return renderSuccess(userService.selectByPrimaryKey(Long.parseLong(ContactId)));

        }
    }
}
