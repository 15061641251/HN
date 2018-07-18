package com.sbq.web.controller;

import com.github.pagehelper.PageInfo;
import com.sbq.entity.Log;
import com.sbq.service.ILogService;
import com.sbq.tools.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "/log")
public class LogController extends BaseController {

    @Autowired
    private ILogService logService;

    @RequestMapping("manager")
    public String manager() {

        return "log/logManager";
    }


    @RequestMapping("/getLogListByPage")
    @ResponseBody
    public Object getLogListByPage(@RequestParam("pageIndex") int pageIndex, @RequestParam("pageSize") int pageSize,
                                   @RequestParam(value = "ip", required = false) String ip,
                                   @RequestParam(value = "module_name", required = false) String module_name) {

        Map<String, Object> parammap = new HashMap<String, Object>();

        parammap.put("pageIndex", pageIndex);
        parammap.put("pageSize", pageSize);

        if (StringUtils.isNotBlank(ip)) {
            parammap.put("ip", ip);
        }

        if (StringUtils.isNotBlank(module_name)) {
            parammap.put("module_name", module_name);
        }

        PageInfo<Log> pageInfo = logService.getLogListByPage(parammap);
        return renderSuccess(pageInfo);

    }


    @RequestMapping("getModuleLog")
    @ResponseBody
    public Object getModuleLog(String moduleName) {

        if (StringUtils.isBlank(moduleName)) {
            return renderRequestError();
        }


        Map param = new HashMap();
        param.put("module_name", moduleName);

        PageInfo<Log> totalLogPageIno = logService.getLogListByPage(param);

        param.put("day", DateUtil.getCurrentDateString("yyyy-MM-dd"));

        PageInfo<Log> todayLogPageIno = logService.getLogListByPage(param);

        long totalVisited = 0;
        if (totalLogPageIno != null) {
            totalVisited = totalLogPageIno.getTotal();
        }

        long todayVisited = 0;
        if (todayLogPageIno != null) {
            todayVisited = todayLogPageIno.getTotal();
        }

        Map result = new HashMap();
        result.put("totalVisited", totalVisited);
        result.put("todayVisited", todayVisited);

        return renderSuccess(result);
    }
}
