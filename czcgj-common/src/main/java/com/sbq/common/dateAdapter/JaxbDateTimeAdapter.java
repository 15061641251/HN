package com.sbq.common.dateAdapter;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 精确到时分秒
 * Created by zhangyuan on 2017/2/22.
 */
public class JaxbDateTimeAdapter extends XmlAdapter<String, Date> {


    private static final SimpleDateFormat dateFormat = new SimpleDateFormat(
            "yyyy-MM-dd HH:mm:ss");

    @Override
    public Date unmarshal(String v) throws Exception {
        if (v == null) {
            return null;
        }

        return dateFormat.parse(v);
    }

    @Override
    public String marshal(Date v) throws Exception {
        return dateFormat.format(v);
    }
}
