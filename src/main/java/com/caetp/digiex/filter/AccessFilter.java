package com.caetp.digiex.filter;

import com.caetp.digiex.dto.cms.SysUserDTO;
import com.caetp.digiex.response.ResponseEnum;
import com.caetp.digiex.utli.common.TJson;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

@WebFilter(filterName = "accessFilter", urlPatterns = "/cms/*")
public class AccessFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse res = (HttpServletResponse) servletResponse;

        //处理跨域问题
        res.addHeader("Access-Control-Allow-Origin", req.getHeader("Origin"));
        res.setHeader("Access-Control-Allow-Methods", "*");
        res.setHeader("Access-Control-Allow-Headers", "Content-Type,Authorization,device");
        res.setHeader("Access-Control-Max-Age", "3600");
        // res.setHeader("Access-Control-Allow-Credentials", "true");
        // res.setHeader("XDomainRequestAllowed","1");

        String reqMethod = req.getMethod();
        if ("OPTIONS".equals(reqMethod)) {
            filterChain.doFilter(req, res);
            return;
        }

        String requestUrl = req.getRequestURL().toString();

        // 登录相关接口不验证token
        if (requestUrl.contains("/login")) {
            filterChain.doFilter(req, res);
            return;
        }
        HttpSession session = req.getSession();
        SysUserDTO user = (SysUserDTO) session.getAttribute("sysUser");

        if (user == null) {
            res.setCharacterEncoding("UTF-8");
            res.setContentType("application/json;charset=UTF-8");
            try {
                String resStr = TJson.objToStr(ResponseEnum.TOKEN_ERROR);
                res.getWriter().write(resStr);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            filterChain.doFilter(req, res);
        }
    }

    @Override
    public void destroy() {

    }

    public static void main(String[] args) {
    }

}
