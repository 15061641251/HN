package com.sbq.web.controller;

import com.sbq.entity.Company;
import com.sbq.entity.dto.RegisterDto;
import com.sbq.entity.dto.UserDto;
import com.sbq.service.ICompanyService;
import com.sbq.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("register")
public class RegisterController extends BaseController {

    @Autowired
    private ICompanyService companyService;

    @Autowired
    private IUserService userService;

    /**
     * 注册入口
     *
     * @return
     */
    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String register() {
        return "register/register";
    }

    /**
     * 提交注册
     *
     * @return
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public Object register_post(@Valid RegisterDto registerDto, BindingResult result) {

        if (result.hasErrors()) {
            return renderRequestError();
        }

        String company_name = registerDto.getCompany_name();

        Map param = new HashMap<>();
        param.put("company_name", company_name);

        Company company = companyService.getInfoByMap(param);
        if (company != null) {
            return renderRequestError("该机构已存在");
        }

        String account = registerDto.getAccount();
        param.clear();
        param.put("account", account);
        UserDto userDto = userService.getInfoByMap(param);
        if (userDto != null) {
            return renderRequestError("该账号已存在");
        }

        companyService.registerCompany(registerDto);

        return renderSuccess();
    }
}
