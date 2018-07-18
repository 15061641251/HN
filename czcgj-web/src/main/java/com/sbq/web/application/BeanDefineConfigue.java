package com.sbq.web.application;

import com.sbq.entity.IntervalQuartz;
import com.sbq.quartz.QuartzIntervalManager;
import com.sbq.service.IIntervalQuartzService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Component
public class BeanDefineConfigue implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private QuartzIntervalManager quartzManager;

    @Autowired
    private IIntervalQuartzService intervalQuartzService;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

        if (event.getApplicationContext().getParent() == null) {
            //需要执行的逻辑代码，当spring容器初始化完成后就会执行该方法。

            Map param = new HashMap<>();
            param.put("status", 1);

            List<IntervalQuartz> intervalQuartzList = intervalQuartzService.getIntervalQuartzListByMap(param);

            if (intervalQuartzList != null && intervalQuartzList.size() > 0) {
                for (IntervalQuartz intervalQuartz : intervalQuartzList) {

                    String bean_class_str = intervalQuartz.getBean_class();

                    if (StringUtils.isNoneBlank(bean_class_str)) {

                        try {

                            Class bean_class = Class.forName(bean_class_str);

                            TimeUnit timeUnit;

                            switch (intervalQuartz.getTime_type()) {
                                case 1:
                                    timeUnit = TimeUnit.SECONDS;
                                    break;
                                case 2:
                                    timeUnit = TimeUnit.MINUTES;
                                    break;
                                case 3:
                                    timeUnit = TimeUnit.HOURS;
                                    break;
                                default:
                                    timeUnit = TimeUnit.MINUTES;
                            }

                            try {
                                quartzManager.addJob(intervalQuartz.getJob_name(), intervalQuartz.getJob_group_name(),
                                        intervalQuartz.getTrigger_name(), intervalQuartz.getTrigger_group_name(), bean_class, timeUnit, intervalQuartz.getValue());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }

                    }
                }
            }
        }
    }

}
