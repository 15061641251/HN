package com.sbq.web.filter;

import com.sbq.dao.IUserDao;
import com.sbq.entity.dto.UserDto;
import com.sbq.service.IAuthorityService;
import com.sbq.tools.Constant;
import com.sbq.tools.SpringContext;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SecurityFilter implements Filter {

    private FilterConfig config = null;

    private IAuthorityService authorityService;

    private IUserDao userDao = null;

    public IUserDao getUserDao() {
        if (userDao == null) {
            userDao = (IUserDao) SpringContext.getBean("IUserDao");
        }
        return userDao;
    }

    private IAuthorityService getAuthorityService() {

        if (authorityService == null) {
            authorityService = (IAuthorityService) SpringContext.getBean("authorityServiceImpl");
        }
        return authorityService;
    }

    public void setAuthorityService(IAuthorityService authorityService) {
        this.authorityService = authorityService;
    }

    public void setFilterConfig(FilterConfig config) {
        this.config = config;
    }

    public FilterConfig getFilterConfig() {
        return config;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

        this.config = filterConfig;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        String sessionOutPage = config.getInitParameter("sessionOutPage");
        String except[] = config.getInitParameter("except").split(",");

        boolean needSession = true;
        String reqServletPath = req.getServletPath();

        for (String anExcept : except) {
            Pattern pattern = Pattern.compile(anExcept);
            Matcher m = pattern.matcher(reqServletPath);
            if (m.find()) {
                needSession = false;
                break;
            }
        }

        boolean isajax = false;

        if (req.getHeader("x-requested-with") != null && req.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest")) {
            isajax = true;
        }

        if (!needSession) {

            chain.doFilter(request, response);

        } else if (needSession && req.getSession().getAttribute(Constant.SESSION_ATTRIBUTE_USER) != null) {
            UserDto user = (UserDto) req.getSession().getAttribute(Constant.SESSION_ATTRIBUTE_USER);

            Map<String, Object> parammap = new HashMap<String, Object>();

            // 获取权限
            List<Map<String, String>> list = this.getAuthorityService().getALLEnabledAuthorityListByMap(parammap);

            // 判断权限
            if (list != null && list.size() > 0) {

                boolean access = false;

                for (Map<String, String> map : list) {
                    String authority_id_str = String.valueOf((map.get("authority_id") == null ? 0 : map.get("authority_id")));
                    String[] auths = user.getAuth_ids().split(",");
                    for (String auth : auths) {
                        if (auth.equals(authority_id_str)) {
                            // 获取可用路径
                            String path = (String) map.get("path");
                            if (StringUtils.isEmpty(path)) {
                                continue;
                            }
                            // 已结尾
                            Pattern pattern = Pattern.compile(path);
                            Matcher m = pattern.matcher(req.getServletPath());

                            if (m.find() == true) {
                                access = true;
                                break;
                            }
                        }
                    }
                }

                if (access) {
                    chain.doFilter(request, response);
                } else {
                    // 权限不足
                    if (isajax) {
                        res.setStatus(Constant.CODE_SECURITY_ERROR);
                    } else {
                        res.setStatus(Constant.CODE_SECURITY_ERROR);
                        res.sendRedirect(req.getContextPath() + "/denied.jsp");
                    }
                }

            } else {
                // 权限不足
                if (isajax) {
                    res.setStatus(Constant.CODE_SECURITY_ERROR);
                } else {
                    res.setStatus(Constant.CODE_SECURITY_ERROR);
                    res.sendRedirect(req.getContextPath() + "/denied.jsp");
                }
            }

        } else {
            // session 过期
            if (isajax) {
                res.setStatus(Constant.CODE_SESSION_TIMEOUT);
            } else {
                res.setStatus(Constant.CODE_SESSION_TIMEOUT);
                res.sendRedirect(req.getContextPath() + "/user/login?redirect_url=" + URLEncoder.encode(reqServletPath));
            }
        }

    }

    @Override
    public void destroy() {
        // TODO Auto-generated method stub

    }

}
