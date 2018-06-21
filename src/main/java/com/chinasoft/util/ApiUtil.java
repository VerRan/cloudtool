package com.chinasoft.util;

import com.cloud.sdk.http.HttpMethodName;
import org.apache.http.HttpResponse;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.URL;
import java.util.Map;

@Component
public class ApiUtil {
    public ApiUtil() {
    }

    //    //replace real region
//    private  String region = "regionName";
//    //replace real service name
//    private  String serviceName = "serviceName";
//
//    public void setServiceName(String serviceName) {
//        this.serviceName = serviceName;
//    }
//
//    public void put(String ak, String sk, String requestUrl,
//                    String putBody) {
//
//        AccessService accessService = null;
//        try {
//            accessService = new AccessServiceImpl(serviceName, region, ak, sk);
//            URL url = new URL(requestUrl);
//            HttpMethodName httpMethod = HttpMethodName.PUT;
//
//            InputStream content = new ByteArrayInputStream(putBody.getBytes());
//            HttpResponse response = accessService.access(url, content,
//                    (long) putBody.getBytes().length, httpMethod);
//
//            System.out.println(response.getStatusLine().getStatusCode());
//
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            accessService.close();
//        }
//
//    }
//
//    public   void patch(String ak, String sk, String requestUrl,
//                             String putBody) {
//
//        AccessService accessService = null;
//        try {
//            accessService = new AccessServiceImpl(serviceName, region, ak, sk);
//            URL url = new URL(requestUrl);
//            HttpMethodName httpMethod = HttpMethodName.PATCH;
//            InputStream content = new ByteArrayInputStream(putBody.getBytes());
//            HttpResponse response = accessService.access(url, content,
//                    (long) putBody.getBytes().length, httpMethod);
//
//            System.out.println(convertStreamToString(response.getEntity()
//                    .getContent()));
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            accessService.close();
//        }
//
//    }
//
//    public   void delete(String ak, String sk, String requestUrl) {
//
//        AccessService accessService = null;
//
//        try {
//            accessService = new AccessServiceImpl(serviceName, region, ak, sk);
//            URL url = new URL(requestUrl);
//            HttpMethodName httpMethod = HttpMethodName.DELETE;
//
//            HttpResponse response = accessService.access(url, httpMethod);
//            System.out.println(convertStreamToString(response.getEntity()
//                    .getContent()));
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            accessService.close();
//        }
//
//    }
//

    private AccessService accessService = null;


    /**
     * 华为API - get方法
     * @param ak
     * @param sk
     * @param requestUrl
     */
    public   HttpResponse get(String serviceName,String region,String ak, String sk, String requestUrl) {
        HttpResponse response=null;
        try {
            accessService = new AccessServiceImpl(serviceName, region, ak, sk);
            URL url = new URL(requestUrl);
            HttpMethodName httpMethod = HttpMethodName.GET;
            response = accessService.access(url, httpMethod);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }




    /**
     * 华为API - 短信发送post方法
     * @param ak
     * @param sk
     * @param requestUrl
     * @param postbody
     */
    public HttpResponse  post(String serviceName,String region,String ak, String sk, String requestUrl,
                              String postbody,Map header) throws Exception {
        accessService = new AccessServiceImpl(serviceName,
                region, ak, sk);
        URL  url = new URL(requestUrl);
        InputStream content = new ByteArrayInputStream(postbody.getBytes());
        HttpMethodName httpMethod = HttpMethodName.POST;
        HttpResponse  response = accessService.access(url,header, content,
                (long) postbody.getBytes().length, httpMethod);
        return response;
    }


//    public void post(String serviceName,String region,String ak, String sk, String requestUrl,
//                            String postbody) {
//          accessService = new AccessServiceImpl(serviceName,
//                region, ak, sk);
//        URL url = null;
//        try {
//            url = new URL(requestUrl);
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        }
//        InputStream content = new ByteArrayInputStream(postbody.getBytes());
//        HttpMethodName httpMethod = HttpMethodName.POST;
//        HttpResponse response;
//
//        try {
//            response = accessService.access(url, content,
//                    (long) postbody.getBytes().length,);
//            System.out.println(convertStreamToString(response.getEntity()
//                    .getContent()));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }


    /**
     * 关闭访问服务链接
     * **/

    public  void close(){
        accessService.close();
    }


    /**
     * @param file
     * @return string
     * */
    public  String translateImageToString(File file) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream((int) file.length());
        BufferedInputStream in = new BufferedInputStream(new FileInputStream(file));
        int buf_size = 1024;
        byte[] buffer = new byte[buf_size];
        int len = 0;
        while (-1 != (len = in.read(buffer, 0, buf_size))) {
            bos.write(buffer, 0, len);
        }
        byte[] fileData= bos.toByteArray();
        String baseStr=java.util.Base64.getEncoder().encodeToString(fileData);
        in.close();
        bos.close();
        return  baseStr;
    }

    public static String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return sb.toString();
    }

}
