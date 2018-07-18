package com.sbq.web.controller;

import com.sbq.service.IAppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/app")
public class AppController extends BaseController{

    @Autowired
    private IAppService appService = null;

    @RequestMapping(value = "/getAppLatestVersion", method = RequestMethod.GET)
    public Object getAppLatestVersion(@RequestParam("appid") int appid) {

        return renderSuccess(appService.getAppLatestVersion(appid));
    }
}
