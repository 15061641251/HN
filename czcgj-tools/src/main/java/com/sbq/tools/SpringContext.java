package com.sbq.tools;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class SpringContext implements ApplicationContextAware {
	protected static ApplicationContext context;

	public void setApplicationContext(ApplicationContext context) throws BeansException {
		SpringContext.context = context;
	}

	public static ApplicationContext getContext() {
		return context;
	}

	public static Object getBean(String beanId) {
		return context.getBean(beanId);
	}
}
