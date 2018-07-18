package com.sbq.web.controller;

import com.sbq.service.ILyDeviceService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@RequestMapping("lydevice")
@Controller
public class LyDeviceController extends BaseController {

    @Autowired
    private ILyDeviceService lyDeviceService;

    @RequestMapping("getList")
    @ResponseBody
    public Object getLyDeviceList(@RequestParam(value = "pageIndex", required = false) Integer pageIndex,
                                  @RequestParam(value = "pageSize", required = false) Integer pageSize,
                                  @RequestParam(value = "mac", required = false) String mac) {

        Map parammap = new HashMap<>();

        parammap.put("pageIndex", pageIndex);
        parammap.put("pageSize", pageSize);

        if (StringUtils.isNoneBlank(mac)) {

            parammap.put("mac", mac);

        }

        return renderSuccess(lyDeviceService.getLyDeviceList(parammap));
    }
}
