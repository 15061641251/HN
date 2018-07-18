package com.sbq.web.controller;


import com.sbq.service.IDeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/map")
public class MapController extends BaseController {

    @Autowired
    private IDeviceService deviceService;

    @RequestMapping("manager")
    public String manager() {

        return "map/mapManager";
    }

}
