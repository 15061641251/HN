package com.sbq.web;

import com.sbq.entity.dto.CzcgLogDto;
import com.sbq.tools.CzcgUtil;
import com.sbq.tools.HttpUtil;
import com.xiaoleilu.hutool.json.JSONArray;
import com.xiaoleilu.hutool.json.JSONException;
import com.xiaoleilu.hutool.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = "classpath:spring/applicationContext.xml")
public class CZNewDeviceTest {

    @Test
    public void test() throws Exception {

        Map headers = new HashMap();
        headers.put("api-key", "918s09RIejHJ=obdR6PnAnuZuXk=");

        String get_url = "http://api.js.cmcconenet.com/devices/80336282" + "/datapoints?limit=1";//约定只取最新一条数据
        String json = HttpUtil.get(get_url, headers);

        JSONObject jsonObject = new JSONObject(json);

        int errno = (int) jsonObject.getObj("errno");

        if (errno == 0) {

            JSONArray datastreams = jsonObject.getJSONObject("data").getJSONArray("datastreams");

            JSONArray resultArray = new JSONArray();

            String data = "";

            String logtime = "";

            for (int i = 0; i < datastreams.size(); i++) {

                JSONObject datastream = datastreams.getJSONObject(i);

                String key = (String) datastream.getObj("id", "");

                JSONArray datapointArray = datastream.getJSONArray("datapoints");

                JSONObject datapoint = datapointArray.getJSONObject(0);//只取第一条数据


                JSONObject object = new JSONObject();

                object.put("id", key);

                object.put("value", datapoint.getObj("value", ""));

                object.put("at", datapoint.getObj("at", ""));

                if (StringUtils.isBlank(logtime)) {
                    logtime = (String) datapoint.getObj("at", "");
                }

                resultArray.add(object);

                data = resultArray.toString();//最终入库结果

            }

            if (StringUtils.isNoneBlank(data)) {

                System.out.println(data);

                JSONArray array = null;
                try {
                    array = new JSONArray(data);
                } catch (JSONException e) {
//                        e.printStackTrace();
                }

                CzcgLogDto czcgLogDto = CzcgUtil.returnCzcgLogDtoByLogAndDeviceType(array, "80336282", 2);

            }

        }

    }
}
