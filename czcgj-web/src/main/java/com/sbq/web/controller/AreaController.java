package com.sbq.web.controller;

import com.github.pagehelper.PageInfo;
import com.sbq.annotation.RequestLog;
import com.sbq.entity.Area;
import com.sbq.entity.dto.AreaDto;
import com.sbq.entity.dto.UserDto;
import com.sbq.service.IAreaService;
import com.sbq.tools.Constant;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("area")
public class AreaController extends BaseController {

    @Autowired
    private IAreaService areaService;

    @RequestMapping("manager")
    public String manager() {
        return "area/areaManager";
    }

    @RequestMapping("chooseOneArea")
    public String chooseOneArea() {
        return "area/chooseOneArea";
    }

    @RequestLog(moduleName = "获取区域列表")
    @RequestMapping("getAreaListByPage")
    @ResponseBody
    public Object getAreaListByPage(@RequestParam("pageIndex") int pageIndex, @RequestParam("pageSize") int pageSize,
                                    @RequestParam(value = "company_name", required = false) String company_name,
                                    @RequestParam(value = "area_name", required = false) String area_name,
                                    @RequestParam(value = "leader_name", required = false) String leader_name,
                                    HttpSession session) throws UnsupportedEncodingException {

        UserDto userDto = (UserDto) session.getAttribute(Constant.SESSION_ATTRIBUTE_USER);

        Map<String, Object> parammap = new HashMap<String, Object>();

        parammap.put("pageIndex", pageIndex);
        parammap.put("pageSize", pageSize);

        if (StringUtils.isNotBlank(company_name)) {
            company_name = URLDecoder.decode(company_name, "UTF-8");
            parammap.put("company_name", company_name);
        }
        if (StringUtils.isNotBlank(area_name)) {
            area_name = URLDecoder.decode(area_name, "UTF-8");
            parammap.put("area_name", area_name);
        }
        if (StringUtils.isNotBlank(leader_name)) {
            leader_name = URLDecoder.decode(leader_name, "UTF-8");
            parammap.put("leader_name", leader_name);
        }
        if (userDto.getCompany_id() != null) {
            parammap.put("company_id", userDto.getCompany_id());
        }

        PageInfo<AreaDto> areaPageInfo = areaService.getAreaListByPage(parammap);

        return renderSuccess(areaPageInfo);
    }

    @RequestMapping(value = "/addArea")
    public String areaAdd() {
        return "/area/addArea";
    }

    @RequestLog(moduleName = "添加区域")
    @RequestMapping(value = "/addAreaPost")
    public
    @ResponseBody
    Object addAreaPost(Area area, HttpSession session) {

        UserDto userDto = (UserDto) session.getAttribute(Constant.SESSION_ATTRIBUTE_USER);

        if (area != null && StringUtils.isNoneBlank(area.getArea_name())) {

            area.setCompany_id(userDto.getCompany_id());
            areaService.addArea(area);

            return renderSuccess();
        } else {
            return renderRequestError();
        }

    }

    @RequestLog(moduleName = "删除区域")
    @RequestMapping(value = "/delArea", method = RequestMethod.POST)
    public
    @ResponseBody
    Object delArea(@RequestParam("areaId") Long areaId) {

        Map<String, String> resultMap = new HashMap<String, String>();

        if (areaId == null) {
            return renderRequestError();
        } else {
            // 删除成功
            areaService.delAreaById(areaId);
            return renderSuccess();
        }

    }

    @RequestMapping("areaDetail")
    public String areaDetail(HttpServletRequest request) {

        return "area/showArea";
    }

    @RequestLog(moduleName = "查看区域详细")
    @RequestMapping("getAreaDetail")
    @ResponseBody
    public Object getAreaDetail(@RequestParam("areaId") Long areaId) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("int_id", areaId);

        Area area = areaService.getInfoByMap(param);

        return renderSuccess(area);
    }


    @RequestMapping("/editArea")
    public String editArea(@RequestParam("areaId") String areaId) {
        return "/area/editArea";
    }

    @RequestLog(moduleName = "修改区域信息")
    @RequestMapping("editAreaById")
    @ResponseBody
    public Object editAreaById(Area area) {

        if (area == null || area.getInt_id() == null) {
            return renderRequestError();
        }
        areaService.updateArea(area);

        return renderSuccess();
    }

}
