package com.chinasofti.hwapi;

//import com.chinasoft.huaweiapi.vo.SrcImage;
import com.chinasoft.util.ApiUtil;
import com.google.gson.Gson;
import org.apache.http.HttpResponse;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

/**
 * Created by VerRan.Liu on 2017/11/6.
 */

@SpringBootTest
public class AITest {
      ApiUtil apiUtil =new ApiUtil() ;
      Gson gson =new Gson();

    String serviceName ="" ;
    String region  ="";
    String ak = "UUPMKIIZY8YKZURDNBNJ";
    String sk = "UGaA3SrUuaeqqk5RmXKDmpzS0gvzHqO9Xo0iIBRt";

    @Test
    public void testqueryStatusUrl(){
        String url ="https://ais.cn-north-1.myhwclouds.com/v1.0/ocr/subscribe?service_type=ocr_form";
        HttpResponse response= apiUtil.get(serviceName,region,ak,sk,url);
        try {
            String retJson =ApiUtil.convertStreamToString(response.getEntity().getContent());
            System.out.println(retJson);
        } catch (IOException e) {
            e.printStackTrace();
        }
        apiUtil.close();
    }


    @Test
    public void testcallTime(){
        String url = "https://ais.cn-north-1.myhwclouds.com/v1.0/ocr/call_times";
        HttpResponse response= apiUtil.get(serviceName,region,ak,sk,url);
        try {
            String retJson =ApiUtil.convertStreamToString(response.getEntity().getContent());
            System.out.println(retJson);
        } catch (IOException e) {
            e.printStackTrace();
        }
        apiUtil.close();
    }

//    @Test
//    public void testImageAI(){
//        String url ="https://ais.cn-north-1.myhwclouds.com/v1.0/ocr/action/ocr_form";
//        SrcImage srcImage =new SrcImage();
//        try {
//            String imageString = apiUtil.translateImageToString(new File("e://a.png"));
//            srcImage.setImage(imageString);
//            Map<String, String> header =new HashMap<>();
//            header.put("Content-Type:","application/json");
//            header.put("Host:","ais.cn-north-1.myhwclouds.com");
//            HttpResponse response= apiUtil.post(serviceName,region,ak,sk,url,gson.toJson(srcImage),header);
//            String retJson =ApiUtil.convertStreamToString(response.getEntity().getContent());
//            System.out.println(retJson);
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        apiUtil.close();
//    }

}
