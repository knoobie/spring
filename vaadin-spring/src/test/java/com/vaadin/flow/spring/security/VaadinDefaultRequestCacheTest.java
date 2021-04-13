package com.vaadin.flow.spring.security;

import java.util.Collections;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.vaadin.flow.server.HandlerHelper.RequestType;
import com.vaadin.flow.server.connect.EndpointUtil;
import com.vaadin.flow.server.connect.VaadinEndpointProperties;
import com.vaadin.flow.spring.VaadinConfigurationProperties;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { VaadinDefaultRequestCache.class, RequestUtil.class,
        EndpointUtil.class, VaadinEndpointProperties.class,
        VaadinConfigurationProperties.class })
public class VaadinDefaultRequestCacheTest {

    @Autowired
    VaadinDefaultRequestCache cache;
    @Autowired
    RequestUtil requestUtil;

    @Test
    public void normalRouteRequestSaved() {
        HttpServletRequest request = RequestUtilTest
                .createRequest("/hello-world", null);
        HttpServletResponse response = createResponse();

        Assert.assertNull(cache.getRequest(request, response));
        cache.saveRequest(request, response);
        Assert.assertNotNull(cache.getRequest(request, response));
    }

    @Test
    public void internalRequestsNotSaved() {
        HttpServletRequest request = RequestUtilTest.createRequest(null,
                RequestType.INIT);
        HttpServletResponse response = createResponse();
        Assert.assertTrue(requestUtil.isFrameworkInternalRequest(request));
        cache.saveRequest(request, response);
        Assert.assertNull(cache.getRequest(request, response));
    }

    @Test
    public void serviceWorkerRequestNotSaved() {
        HttpServletRequest request = RequestUtilTest.createRequest("", null,
                Collections.singletonMap("Referer",
                        "https://labs.vaadin.com/business/sw.js"));
        HttpServletResponse response = createResponse();
        Assert.assertFalse(requestUtil.isFrameworkInternalRequest(request));
        cache.saveRequest(request, response);
        Assert.assertNull(cache.getRequest(request, response));
    }

    @Test
    public void endpointRequestNotSaved() {
        HttpServletRequest request = RequestUtilTest
                .createRequest("/connect/MyClass/MyEndpoint", null);
        HttpServletResponse response = createResponse();
        cache.saveRequest(request, response);
        Assert.assertNull(cache.getRequest(request, response));
    }

    private HttpServletResponse createResponse() {
        return Mockito.mock(HttpServletResponse.class);
    }

}