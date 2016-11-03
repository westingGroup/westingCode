package com.infosys.westing.commerce.shiro.filter;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.web.filter.PathMatchingFilter;
import org.springframework.beans.factory.annotation.Autowired;

import com.infosys.westing.commerce.shiro.Constants;
import com.infosys.westing.commerce.shiro.service.UserService;

/**
 * 
 * @author Anne_Yan
 * 2016年10月31日
 */
public class SysUserFilter extends PathMatchingFilter {

    @Autowired
    private UserService userService;

    @Override
    protected boolean onPreHandle(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {

        String username = (String)SecurityUtils.getSubject().getPrincipal();
        request.setAttribute(Constants.CURRENT_USER, userService.findByUsername(username));
        return true;
    }
}
