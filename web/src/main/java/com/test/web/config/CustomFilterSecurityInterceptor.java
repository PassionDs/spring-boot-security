package com.test.web.config;

import org.apache.log4j.Logger;
import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.access.intercept.AbstractSecurityInterceptor;
import org.springframework.security.access.intercept.InterceptorStatusToken;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;

import javax.servlet.*;

public class CustomFilterSecurityInterceptor extends AbstractSecurityInterceptor implements Filter {
    private static final Logger logger = Logger.getLogger(CustomFilterSecurityInterceptor.class);
    private FilterInvocationSecurityMetadataSource securityMetadataSource;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) {
        FilterInvocation fi = new FilterInvocation(request, response, chain);
        logger.debug("====" + fi.getRequestUrl());
        invoke(fi);
    }

    public void invoke(FilterInvocation fi) {
        InterceptorStatusToken token = super.beforeInvocation(fi);
        try {
            fi.getChain().doFilter(fi.getRequest(), fi.getResponse());
        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally {
            super.afterInvocation(token, null);
        }
    }

    public FilterInvocationSecurityMetadataSource getSecurityMetadataSource() {
        return this.securityMetadataSource;
    }

    @Override
    public Class<? extends Object> getSecureObjectClass() {
        return FilterInvocation.class;
    }

    @Override
    public SecurityMetadataSource obtainSecurityMetadataSource() {
        return this.securityMetadataSource;
    }

    public void setSecurityMetadataSource(
            FilterInvocationSecurityMetadataSource smSource) {
        this.securityMetadataSource = smSource;
    }

    @Override
    public void destroy() {
        // TODO Auto-generated method stub

    }

    @Override
    public void init(FilterConfig arg0) {
        // TODO Auto-generated method stub

    }

}
