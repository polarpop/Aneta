package com.aneta.services.gateway.filter.pre;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.apache.http.HttpStatus;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class SecureRequestFilter extends ZuulFilter {
  @Override
  public String filterType() {
    return "pre";
  }

  @Override
  public int filterOrder() {
    return FilterConstants.SEND_FORWARD_FILTER_ORDER;
  }

  @Override
  public boolean shouldFilter() {
    return true;
  }

  @Override
  public Object run() {
    RequestContext ctx = RequestContext.getCurrentContext();
    HttpServletRequest request = ctx.getRequest();
    if (!(request.isSecure())) {
      String url = request.getRequestURL().toString();
      String redirectUrl = url.replaceFirst("^http://", "https://");
      try {
        ctx.setSendZuulResponse(false);
        ctx.put(FilterConstants.FORWARD_TO_KEY, redirectUrl);
        ctx.getResponse().sendRedirect(redirectUrl);
        ctx.setResponseStatusCode(HttpStatus.SC_MOVED_PERMANENTLY);
      } catch (IOException e) {}
    }
    return null;
  }
}
