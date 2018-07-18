package com.sbq.web.controller;

import com.github.pagehelper.PageInfo;
import com.sbq.annotation.RequestLog;
import com.sbq.entity.AuthResources;
import com.sbq.entity.Authority;
import com.sbq.entity.Company;
import com.sbq.entity.dto.UserDto;
import com.sbq.service.ICompanyService;
import com.sbq.tools.Constant;
import com.sbq.tools.NullUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 注册公司管理
 */
@Controller
@RequestMapping("company")
public class CompanyController extends BaseController {

    @Autowired
    private ICompanyService companyService;


    @RequestMapping("manager")
    public String manager() {

        return "company/companyManager";
    }

    @RequestMapping("companyDetail")
    public String companyDetail(HttpServletRequest request) {

        return "company/showCompany";
    }

    @RequestMapping("chooseOneCompany")
    public String chooseOneCompany() {
        return "company/chooseOneCompany";
    }

    @RequestLog(moduleName = "获取注册机构信息")
    @RequestMapping("getCompanyDetail")
    @ResponseBody
    public Object getCompanyDetail(@RequestParam("companyId") Long companyId) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("int_id", companyId);

        Company company = companyService.getInfoByMap(param);

        return renderSuccess(company);
    }

    @RequestLog(moduleName = "获取注册结构列表")
    @RequestMapping("/getCompanyListByPage")
    @ResponseBody
    public Object getCompanyListByPage(@RequestParam("pageIndex") int pageIndex, @RequestParam("pageSize") int pageSize, @RequestParam("name") String name, HttpSession session) throws UnsupportedEncodingException {


        UserDto userDto = (UserDto) session.getAttribute(Constant.SESSION_ATTRIBUTE_USER);

        Map<String, Object> parammap = new HashMap<String, Object>();

        parammap.put("pageIndex", pageIndex);
        parammap.put("pageSize", pageSize);

        if (StringUtils.isNotBlank(name)) {
            name = URLDecoder.decode(name, "UTF-8");
            parammap.put("name", name);
        }
        if (userDto.getCompany_id() != null) {
            parammap.put("company_id", userDto.getCompany_id());
        }

        PageInfo<Company> pageInfo = companyService.getCompanyListByPage(parammap);
        return renderSuccess(pageInfo);

    }

    @RequestMapping("/editCompany")
    public String editCompany(@RequestParam("companyId") String companyId) {
        return "/company/editCompany";
    }

    @RequestLog(moduleName = "修改注册机构信息")
    @RequestMapping("editCompanyById")
    @ResponseBody
    public Object editCompanyById(Company company) {

        if (company == null || company.getInt_id() == null) {
            return renderRequestError();
        }
        companyService.updateCompany(company);

        return renderSuccess();
    }


    @RequestLog(moduleName = "删除注册机构信息")
    @RequestMapping(value = "/delCompany", method = RequestMethod.POST)
    public
    @ResponseBody
    Object delCompany(@RequestParam("companyId") Long companyId) {

        Map<String, String> resultMap = new HashMap<String, String>();

        if (companyId == null) {
            return renderRequestError();
        } else {
            // 删除成功
            companyService.delCompanyById(companyId);
            return renderSuccess();
        }

    }

    @RequestMapping(value = "/addCompany")
    public String companyAdd() {
        return "/company/addCompany";
    }

    @RequestLog(moduleName = "添加注册机构信息")
    @RequestMapping(value = "/addCompanyPost")
    public
    @ResponseBody
    Object addCompanyPost(Company company, HttpSession session) {

        if (company != null && StringUtils.isNoneBlank(company.getCompany_name(), company.getAddress(), company.getPerson_name(), company.getPerson_phone())) {

            companyService.addCompany(company);

            return renderSuccess();
        } else {
            return renderRequestError();
        }

    }

}
