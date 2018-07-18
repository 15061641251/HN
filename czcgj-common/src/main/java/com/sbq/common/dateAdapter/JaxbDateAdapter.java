package com.sbq.common.dateAdapter;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 精确到日
 * Created by zhangyuan on 2017/2/22.
 */
public class JaxbDateAdapter extends XmlAdapter<String, Date> {

    static final String STANDARM_DATE_FORMAT = "yyyy-MM-dd";

    @Override
    public Date unmarshal(String v) throws Exception {
        if (v == null) {
            return null;
        }

        DateFormat format = new SimpleDateFormat(STANDARM_DATE_FORMAT);
        return format.parse(v);
    }

    @Override
    public String marshal(Date v) throws Exception {
        DateFormat format = new SimpleDateFormat(STANDARM_DATE_FORMAT);
        return format.format(v);
    }
}
