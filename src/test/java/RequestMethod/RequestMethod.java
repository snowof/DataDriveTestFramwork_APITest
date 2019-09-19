package RequestMethod;

//import GoodsTest.GoodsApiTest;
import Tools.HttpDeleteWithBody;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.*;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author alexyou
 * @<version >1.0</version>
 */
public class RequestMethod {


    /**
     * Get 方法
     * @param url 传入Url
     *//*
    public HashMap get(String url) {
        ArrayList list = new ArrayList();
        HashMap hashMap = new HashMap();
        int responseStatusCode = 0;
        String responseString = null;
        try {
            //创建一个可关闭的对象
            CloseableHttpClient httpClient = HttpClients.createDefault();
            //创建一个HttpGet请求对象
            HttpGet httpGet = new HttpGet(url);
            //执行请求,相当于postman上点击发送按钮，然后赋值给HttpResponse对象接收
            CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
            //获取状态码，用于比较，例如200、404、500
            responseStatusCode = httpResponse.getStatusLine().getStatusCode();
            //打印状态码
            logger.info("Response Status Code :" + responseStatusCode);
            //把响应内容存储在字符串对象
            responseString = EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
            //创建Json对象，把上面字符串序列化成Json对象
            JSONObject responseJson = JSON.parseObject(responseString);
            logger.info("response json from API ：" + responseJson);

            //获取响应头，返回是个数组
            Header[] headerArray = httpResponse.getAllHeaders();

            HashMap<String, String> hm = new HashMap<String, String>();

            for (Header header : headerArray) {
                hm.put(header.getName(), header.getValue());
            }
            logger.info("response headers :" + hm);
        } catch (IOException e) {
            e.printStackTrace();
        }
        list.add(responseStatusCode);
        list.add(responseString);
        hashMap.put(responseStatusCode, responseStatusCode);
        hashMap.put(responseString, responseString);
        return hashMap;

    }*/

    private static final Logger logger = LogManager.getLogger(RequestMethod.class);

    /**
     * Get请求方法
     *
     * @param url
     * @return
     * @throws ClientProtocolException
     * @throws IOException
     */
    public CloseableHttpResponse get(String url) {
        CloseableHttpResponse httpResponse = null;
        try {
            //创建一个可关闭的HttpClient对象
            CloseableHttpClient httpClient = HttpClients.createDefault();
            //穿件一个HttpGet请求方法
            logger.info("请求地址：" + url);
            HttpGet httpGet = new HttpGet(url);
            logger.info("开始发送Get Request...");
            //发送get请求
            httpResponse = httpClient.execute(httpGet);
            logger.info("发送请求成功，得到Request...");
        } catch (IOException e) {
            logger.error("发送Get Request失败..." + e);
        }
        return httpResponse;
    }

    /**
     * Get 请求方法，带请求头
     *
     * @param url
     * @return
     * @throws ClientProtocolException
     * @throws IOException
     */
    public CloseableHttpResponse getAndHeaders(String url, HashMap<String, String> headerMap) {
        CloseableHttpResponse closeableHttpResponse = null;
        try {
            //创建一个可关闭的HttpClient对象
            CloseableHttpClient httpClient = HttpClients.createDefault();
            logger.info("请求地址：" + url);
            //创建一个HttpGet对象
            HttpGet httpGet = new HttpGet(url);
            logger.info("请求头："+ headerMap);
            //加载请求到httpGet对象
            for (Map.Entry<String, String> entry : headerMap.entrySet()) {
                httpGet.addHeader(entry.getKey(), entry.getValue());
            }
            logger.info("开始发送Get Request...");
            //发送get请求
            closeableHttpResponse = httpClient.execute(httpGet);
            logger.info("发送请求成功，得到Request...");
        } catch (IOException e) {
            logger.error("发送Get Request失败..." + e);
        }
        return closeableHttpResponse;
    }


    /**
     * Post请求方法
     * @param url 请求定制
     * @param entityString
     * @param headerMap 请求头
     * @return
     * @throws ClientProtocolException
     * @throws IOException
     */
    public CloseableHttpResponse post(String url, String entityString, HashMap<String, String> headerMap) {
        //创建一个可关闭HttpClient对象
        CloseableHttpResponse closeableHttpResponse = null;
        try {
            CloseableHttpClient httpClient = HttpClients.createDefault();
            logger.info("请求地址：\n" + url);
            //创建一个HttpPost的请求对象
            HttpPost httpPost = new HttpPost(url);
            RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(3000).build();
            StringEntity stringEntity = new StringEntity(entityString, "UTF-8");
            httpPost.setConfig(requestConfig);
            logger.info("请求Json包体：\n" + entityString);
            //设置Payload
            httpPost.setEntity(new StringEntity(entityString));
            httpPost.setEntity(stringEntity);
            logger.info("请求头："+ headerMap);
            //加载请求头到httpPost对象
            for(Map.Entry<String, String> entry : headerMap.entrySet()) {
                httpPost.addHeader(entry.getKey(), entry.getValue());
            }
            logger.info("开始发送Post Request...");
            //发送post请求
            closeableHttpResponse = httpClient.execute(httpPost);
            logger.info("发送Post请求成功，得到Response...");
        } catch (IOException e) {
            logger.error("!!!发送Post Request失败..." + e);
        }
        return closeableHttpResponse;
    }

    /**
     * Post请求方法
     * @param url 请求定制
     * @param entityString
     * @param headerMap 请求头
     * @return
     * @throws ClientProtocolException
     * @throws IOException
     */
    public CloseableHttpResponse postUploadFile(String url, String entityString, HashMap<String, String> headerMap, String localFileUrl) {
        CloseableHttpResponse closeableHttpResponse = null;
        try {
            //创建一个可关闭HttpClient对象
            CloseableHttpClient httpClient = HttpClients.createDefault();
            logger.info("请求地址：" + url);

            //创建一个HttpPost的请求对象
            HttpPost httpPost = new HttpPost(url);
//            FileBody fileBody = new FileBody(new File(localFileUrl));
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.addBinaryBody("file", new File(localFileUrl), ContentType.APPLICATION_OCTET_STREAM, "file.ext");
            HttpEntity multipart = builder.build();
            httpPost.setEntity(multipart);
//            StringBody comment = new StringBody("This is comment", ContentType.TEXT_PLAIN);
//            HttpEntity httpEntity = MultipartEntityBuilder.create().addPart("file", fileBody).addPart("comment", comment).build();
//            httpPost.setEntity(httpEntity);
            logger.info("请求Json包体：" + entityString);
            //设置Payload
            httpPost.setEntity(new StringEntity(entityString));
            logger.info("请求头："+ headerMap);
            //加载请求头到httpPost对象
            for(Map.Entry<String, String> entry : headerMap.entrySet()) {
                httpPost.addHeader(entry.getKey(), entry.getValue());
            }
            logger.info("开始发送Post Request，并且上传文件...");
            //发送post请求
            closeableHttpResponse = httpClient.execute(httpPost);
            logger.info("发送Post请求成功，得到Response...");
        } catch (IOException e) {
            logger.error("发送Post Request 失败..."+ e);
        } /*finally {
            try {
                closeableHttpResponse.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }*/
        return closeableHttpResponse;
    }

    public CloseableHttpResponse put(String url, String entityString, HashMap<String, String> headerMap) {
        CloseableHttpResponse httpResponse = null;
        try {
            CloseableHttpClient httpClient = HttpClients.createDefault();
            logger.info("请求地址：" + url);
            HttpPut httpPut = new HttpPut(url);
            logger.info("请求Json包体：" + entityString);
            httpPut.setEntity(new StringEntity(entityString));
            logger.info("请求头："+ headerMap);
            for (Map.Entry<String, String> entry : headerMap.entrySet()) {
                httpPut.addHeader(entry.getKey(), entry.getValue());
            }
            logger.info("开始发送Put Request...");
            httpResponse = httpClient.execute(httpPut);
            logger.info("发送Put请求成功，得到Response...");
        } catch (IOException e) {
            logger.error("发送Put Request失败..." + e);
        }
        return httpResponse;
    }

    public CloseableHttpResponse delete(String url, HashMap<String, String> headerMap) {
        CloseableHttpResponse httpResponse = null;
        try {
            CloseableHttpClient httpClient = HttpClients.createDefault();
            logger.info("请求地址：" + url);
            HttpDelete httpDelete = new HttpDelete(url);
            logger.info("请求头："+ headerMap);
//            httpDelete.setEntity(new StringEntity(entityString));
            for (Map.Entry<String, String> entry : headerMap.entrySet()) {
                httpDelete.addHeader(entry.getKey(), entry.getValue());
            }
            logger.info("开始发送Delete Request...");
            httpResponse = httpClient.execute(httpDelete);
            logger.info("发送Delete请求成功，得到Response...");
        } catch (IOException e) {
            logger.error("发送Delete Request失败..." + e);
        }
        return httpResponse;
    }

    public CloseableHttpResponse deleteWithJsonBody(String url, String entityString, HashMap<String, String> headerMap) {
        CloseableHttpResponse httpResponse = null;
        try {
            CloseableHttpClient httpClient = HttpClients.createDefault();
            logger.info("请求地址：" + url);
            HttpDeleteWithBody httpDelete = new HttpDeleteWithBody(url);
            logger.info("请求头："+ headerMap);
            httpDelete.setEntity(new StringEntity(entityString));
            for (Map.Entry<String, String> entry : headerMap.entrySet()) {
                httpDelete.addHeader(entry.getKey(), entry.getValue());
            }
            logger.info("开始发送Delete Request...");
            httpResponse = httpClient.execute(httpDelete);
            logger.info("发送Delete请求成功，得到Response...");
        } catch (IOException e) {
            logger.error("发送Delete Request失败..." + e);
        }
        return httpResponse;
    }

    public CloseableHttpResponse patch(String url, String entityString, HashMap<String, String> headerMap) {
        CloseableHttpResponse httpResponse = null;
        try {
            CloseableHttpClient httpClient = HttpClients.createDefault();
            logger.info("请求地址：" + url);
            HttpPatch httpPatch = new HttpPatch(url);
            RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(3000).build();
            StringEntity stringEntity = new StringEntity(entityString, "UTF-8");
            httpPatch.setConfig(requestConfig);
            logger.info("请求Json包体：" + entityString);
            //设置Payload
            httpPatch.setEntity(new StringEntity(entityString));
            httpPatch.setEntity(stringEntity);
            logger.info("请求头："+ headerMap);
            for (Map.Entry<String, String> entry : headerMap.entrySet()) {
                httpPatch.addHeader(entry.getKey(), entry.getValue());
            }
            logger.info("开始发送Patch Request...");
            httpResponse = httpClient.execute(httpPatch);
            logger.info("发送Patch请求成功，得到Response...");
        } catch (IOException e) {
            logger.error("发送Patch Request 失败..." + e);
        }
        return httpResponse;
    }
}
