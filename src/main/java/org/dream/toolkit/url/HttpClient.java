package org.dream.toolkit.url;

import org.dream.toolkit.bean.BeanUtils;
import org.dream.toolkit.enums.HttpMethod;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author tobiasy
 * @date 2018/10/23
 */
public class HttpClient {
    public static HttpURLConnection getConnection(String httpUrl, HttpMethod method) {
        HttpURLConnection connection = null;
        try {
            URL url = new URL(httpUrl);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(method.name());
            connection.setConnectTimeout(15000);
            connection.setReadTimeout(60000);
            connection.connect();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return connection;
    }

    /**
     * get请求发送
     *
     * @param httpUrl
     * @return
     */
    public static String doGet(String httpUrl) {
        HttpURLConnection connection = null;
        String result = null;
        try {
            connection = getConnection(httpUrl, HttpMethod.GET);
            result = getResponse(connection);
        } catch (RuntimeException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        return result;
    }

    /**
     * post请求发送
     *
     * @param httpUrl
     * @param param
     * @return
     */
    public static String doPost(String httpUrl, String param) {
        HttpURLConnection connection = null;
        OutputStream os = null;
        String result = null;
        try {
            URL url = new URL(httpUrl);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(HttpMethod.POST.name());
            connection.setConnectTimeout(15000);
            connection.setReadTimeout(60000);
            // 默认值为：false，当向远程服务器传送数据/写数据时，需要设置为true
            connection.setDoOutput(true);
            // 默认值为：true，当前向远程服务读取数据时，设置为true，该参数可有可无
            connection.setDoInput(true);
            // 设置传入参数的格式:请求参数应该是 name1=value1&name2=value2 的形式。
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            // 设置鉴权信息：Authorization: Bearer da3efcbf-0845-4fe3-8aba-ee040be542c0
            connection.setRequestProperty("Authorization", "Bearer da3efcbf-0845-4fe3-8aba-ee040be542c0");
            os = connection.getOutputStream();
            // 通过输出流对象将参数写出去/传输出去,它是通过字节数组写出的
            os.write(param.getBytes());
            connection.connect();
            result = getResponse(connection);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            BeanUtils.close(os);
            if (connection != null) {
                connection.disconnect();
            }
        }
        return result;
    }

    /**
     * 获取响应结果
     *
     * @param connection
     * @return
     */
    public static String getResponse(HttpURLConnection connection) {
        InputStream is = null;
        BufferedReader br = null;
        StringBuffer sbf = new StringBuffer();
        try {
            if (connection.getResponseCode() == 200) {
                is = connection.getInputStream();
                // 封装输入流is，并指定字符集
                br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                // 存放数据
                String temp;
                while ((temp = br.readLine()) != null) {
                    sbf.append(temp);
                    sbf.append("\r\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            BeanUtils.close(br);
            BeanUtils.close(is);
        }
        return sbf.toString();
    }
}