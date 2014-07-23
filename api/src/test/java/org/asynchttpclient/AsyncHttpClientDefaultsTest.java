package org.asynchttpclient;

import org.asynchttpclient.util.AsyncPropertiesHelper;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.lang.reflect.Method;

@Test
public class AsyncHttpClientDefaultsTest {
    public static final String ASYNC_CLIENT = AsyncHttpClientConfig.class.getName() + ".";
    
    public void testDefaultMaxTotalConnections() {
        Assert.assertEquals(AsyncHttpClientConfigDefaults.defaultMaxConnections(),-1);
        testIntegerSystemProperty("maxConnections", "defaultMaxConnections", "100");
    }

    public void testDefaultMaxConnectionPerHost() {
        Assert.assertEquals(AsyncHttpClientConfigDefaults.defaultMaxConnectionsPerHost(), -1);
        testIntegerSystemProperty("maxConnectionsPerHost", "defaultMaxConnectionsPerHost", "100");
    }

    public void testDefaultConnectionTimeOutInMs() {
        Assert.assertEquals(AsyncHttpClientConfigDefaults.defaultConnectionTimeout(), 60 * 1000);
        testIntegerSystemProperty("connectionTimeout", "defaultConnectionTimeout", "100");
    }

    public void testDefaultIdleConnectionInPoolTimeoutInMs() {
        Assert.assertEquals(AsyncHttpClientConfigDefaults.defaultPooledConnectionIdleTimeout(), 60 * 1000);
        testIntegerSystemProperty("pooledConnectionIdleTimeout", "defaultPooledConnectionIdleTimeout", "100");
    }

    public void testDefaultIdleConnectionTimeoutInMs() {
        Assert.assertEquals(AsyncHttpClientConfigDefaults.defaultReadTimeout(), 60 * 1000);
        testIntegerSystemProperty("readTimeout", "defaultReadTimeout", "100");
    }

    public void testDefaultRequestTimeoutInMs() {
        Assert.assertEquals(AsyncHttpClientConfigDefaults.defaultRequestTimeout(), 60 * 1000);
        testIntegerSystemProperty("requestTimeout", "defaultRequestTimeout", "100");
    }

    public void testDefaultWebSocketIdleTimeoutInMs() {
        Assert.assertEquals(AsyncHttpClientConfigDefaults.defaultWebSocketTimeout(), 15 * 60 * 1000);
        testIntegerSystemProperty("webSocketTimeout", "defaultWebSocketTimeout", "100");
    }

    public void testDefaultMaxConnectionLifeTimeInMs() {
        Assert.assertEquals(AsyncHttpClientConfigDefaults.defaultConnectionTTL(), -1);
        testIntegerSystemProperty("connectionTTL", "defaultConnectionTTL", "100");
    }

    public void testDefaultFollowRedirect() {
        Assert.assertFalse(AsyncHttpClientConfigDefaults.defaultFollowRedirect());
        testBooleanSystemProperty("followRedirect", "defaultFollowRedirect", "true");
    }

    public void testDefaultMaxRedirects() {
        Assert.assertEquals(AsyncHttpClientConfigDefaults.defaultMaxRedirects(), 5);
        testIntegerSystemProperty("maxRedirects", "defaultMaxRedirects", "100");
    }

    public void testDefaultCompressionEnabled() {
        Assert.assertFalse(AsyncHttpClientConfigDefaults.defaultCompressionEnabled());
        testBooleanSystemProperty("compressionEnabled", "defaultCompressionEnabled", "true");
    }

    public void testDefaultUserAgent() {
        Assert.assertEquals(AsyncHttpClientConfigDefaults.defaultUserAgent(),"NING/1.0");
        testStringSystemProperty("userAgent", "defaultUserAgent", "MyAHC");
    }

    public void testDefaultIoThreadMultiplier() {
        Assert.assertEquals(AsyncHttpClientConfigDefaults.defaultIoThreadMultiplier(), 2);
        testIntegerSystemProperty("ioThreadMultiplier", "defaultIoThreadMultiplier", "100");
    }

    public void testDefaultUseProxySelector() {
        Assert.assertFalse(AsyncHttpClientConfigDefaults.defaultUseProxySelector());
        testBooleanSystemProperty("useProxySelector", "defaultUseProxySelector", "true");
    }

    public void testDefaultUseProxyProperties() {
        Assert.assertFalse(AsyncHttpClientConfigDefaults.defaultUseProxyProperties());
        testBooleanSystemProperty("useProxyProperties", "defaultUseProxyProperties", "true");
    }

    public void testDefaultStrict302Handling() {
        Assert.assertFalse(AsyncHttpClientConfigDefaults.defaultStrict302Handling());
        testBooleanSystemProperty("strict302Handling", "defaultStrict302Handling", "true");
    }

    public void testDefaultAllowPoolingConnection() {
       Assert.assertTrue(AsyncHttpClientConfigDefaults.defaultAllowPoolingConnections());
       testBooleanSystemProperty("allowPoolingConnections", "defaultAllowPoolingConnections", "false");
    }

    public void testDefaultUseRelativeURIsWithSSLProxies() {
       Assert.assertTrue(AsyncHttpClientConfigDefaults.defaultUseRelativeURIsWithSSLProxies());
       testBooleanSystemProperty("useRelativeURIsWithSSLProxies", "defaultUseRelativeURIsWithSSLProxies", "false");
    }

    public void testDefaultMaxRequestRetry() {
        Assert.assertEquals(AsyncHttpClientConfigDefaults.defaultMaxRequestRetry(), 5);
        testIntegerSystemProperty("maxRequestRetry", "defaultMaxRequestRetry", "100");
    }

    public void testDefaultAllowSslConnectionPool() {
       Assert.assertTrue(AsyncHttpClientConfigDefaults.defaultAllowPoolingSslConnections());
       testBooleanSystemProperty("allowPoolingSslConnections", "defaultAllowPoolingSslConnections", "false");
    }

    public void testDefaultDisableUrlEncodingForBoundRequests() {
        Assert.assertFalse(AsyncHttpClientConfigDefaults.defaultDisableUrlEncodingForBoundRequests());
        testBooleanSystemProperty("disableUrlEncodingForBoundRequests", "defaultDisableUrlEncodingForBoundRequests", "true");
    }

    public void testDefaultRemoveQueryParamOnRedirect() {
       Assert.assertTrue(AsyncHttpClientConfigDefaults.defaultRemoveQueryParamOnRedirect());
       testBooleanSystemProperty("removeQueryParamOnRedirect", "defaultRemoveQueryParamOnRedirect", "false");
    }

    public void testDefaultSpdyEnabled() {
        Assert.assertFalse(AsyncHttpClientConfigDefaults.defaultSpdyEnabled());
        testBooleanSystemProperty("spdyEnabled", "defaultSpdyEnabled", "true");
    }

    public void testDefaultSpdyInitialWindowSize() {
        Assert.assertEquals(AsyncHttpClientConfigDefaults.defaultSpdyInitialWindowSize(), 10 * 1024 * 1024);
        testIntegerSystemProperty("spdyInitialWindowSize", "defaultSpdyInitialWindowSize", "100");
    }

    public void testDefaultSpdyMaxConcurrentStreams() {
        Assert.assertEquals(AsyncHttpClientConfigDefaults.defaultSpdyMaxConcurrentStreams(), 100);
        testIntegerSystemProperty("spdyMaxConcurrentStreams", "defaultSpdyMaxConcurrentStreams", "100");
    }
    
    public void testDefaultAcceptAnyCertificate() {
       Assert.assertFalse(AsyncHttpClientConfigDefaults.defaultAcceptAnyCertificate());
       testBooleanSystemProperty("acceptAnyCertificate", "defaultAcceptAnyCertificate", "true");
    }
    
    private void testIntegerSystemProperty(String propertyName,String methodName,String value){
        String previous = System.getProperty(ASYNC_CLIENT + propertyName);
        System.setProperty(ASYNC_CLIENT + propertyName, value);
        AsyncPropertiesHelper.reloadProperties();
        try {
            Method method = AsyncHttpClientConfigDefaults.class.getMethod(methodName, new Class[]{});
            Assert.assertEquals(method.invoke(null, new Object[]{}),Integer.parseInt(value));
        } catch (Exception e) {
            Assert.fail("Couldn't find or execute method : " + methodName,e);
        } 
        if(previous != null)
            System.setProperty(ASYNC_CLIENT + propertyName, previous);
        else
            System.clearProperty(ASYNC_CLIENT + propertyName);
    }
    
    private void testBooleanSystemProperty(String propertyName,String methodName,String value){
        String previous = System.getProperty(ASYNC_CLIENT + propertyName);
        System.setProperty(ASYNC_CLIENT + propertyName, value);
        AsyncPropertiesHelper.reloadProperties();
        try {
            Method method = AsyncHttpClientConfigDefaults.class.getMethod(methodName, new Class[]{});
            Assert.assertEquals(method.invoke(null, new Object[]{}),Boolean.parseBoolean(value));
        } catch (Exception e) {
            Assert.fail("Couldn't find or execute method : " + methodName,e);
        } 
        if(previous != null)
            System.setProperty(ASYNC_CLIENT + propertyName, previous);
        else
            System.clearProperty(ASYNC_CLIENT + propertyName);
    }
    
    private void testStringSystemProperty(String propertyName,String methodName,String value){
        String previous = System.getProperty(ASYNC_CLIENT + propertyName);
        System.setProperty(ASYNC_CLIENT + propertyName, value);
        AsyncPropertiesHelper.reloadProperties();
        try {
            Method method = AsyncHttpClientConfigDefaults.class.getMethod(methodName, new Class[]{});
            Assert.assertEquals(method.invoke(null, new Object[]{}),value);
        } catch (Exception e) {
            Assert.fail("Couldn't find or execute method : " + methodName,e);
        } 
        if(previous != null)
            System.setProperty(ASYNC_CLIENT + propertyName, previous);
        else
            System.clearProperty(ASYNC_CLIENT + propertyName);
    }
}
