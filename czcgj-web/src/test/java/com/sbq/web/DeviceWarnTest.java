package com.sbq.web;

import com.github.pagehelper.PageInfo;
import com.sbq.entity.dto.CzcgDeviceLastLogDto;
import com.sbq.entity.dto.CzcgLogDto;
import com.sbq.entity.dto.DeviceDto;
import com.sbq.service.ICzcgService;
import com.sbq.service.IDeviceService;
import com.sbq.tools.CzcgUtil;
import com.xiaoleilu.hutool.json.JSONArray;
import com.xiaoleilu.hutool.json.JSONException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/applicationContext.xml")
public class DeviceWarnTest {

    @Autowired
    private IDeviceService deviceService;


    @Autowired
    private ICzcgService czcgService;

    /**
     * 将所有的设备都模拟告警一次
     */
    @Test
    public void warn() {

        Map map = new HashMap();

        PageInfo<CzcgDeviceLastLogDto> pageInfo = czcgService.getDeviceListAndLastLog(map);

        List<CzcgDeviceLastLogDto> list = pageInfo.getList();

        for (CzcgDeviceLastLogDto czcgDeviceLastLogDto : list) {

            String data = czcgDeviceLastLogDto.getData();

            JSONArray array = null;
            try {
                array = new JSONArray(data);
            } catch (JSONException e) {
//                        e.printStackTrace();
            }

            if (array == null) {
                continue;
            }

            CzcgLogDto czcgLogDto = CzcgUtil.returnCzcgLogDtoByLogAndDeviceType(array, czcgDeviceLastLogDto.getDevice_id(),czcgDeviceLastLogDto.getDevice_type());

            Map param = new HashMap<>();
            param.put("device_id", czcgDeviceLastLogDto.getDevice_id());

            DeviceDto device = deviceService.getInfoByMap(param);


            System.out.println(czcgDeviceLastLogDto.getDevice_id());

            czcgService.pushWarn(czcgDeviceLastLogDto.getDevice_id(), 4, czcgLogDto, device);
        }

    }
}
