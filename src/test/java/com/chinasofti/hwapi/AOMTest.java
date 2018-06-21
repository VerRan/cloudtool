package com.chinasofti.hwapi;

import com.chinasoft.util.ApiUtil;
import com.google.gson.Gson;
import org.apache.http.HttpResponse;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by VerRan.Liu on 2018/6/6.
 * 测试AOM 接口
 */
public class AOMTest {
    ApiUtil apiUtil =new ApiUtil() ;
    Gson gson =new Gson();

    String serviceName ="" ;
    String region  ="";
    String ak = "4GKAHMGU7IGWDOYJELEY";
    String sk = "Oo6b2sF9WZKJ2ZFXStKAHIFVD7skj5v7ueeUKs0m";
    String projectId= "fdfee0c14ec04bf08ac7bc1a360dceca";

    @Test
    public void testqueryStatusUrl(){
        String url ="https://aom.cn-north-1.myhuaweicloud.com/v1/"+projectId+"/ams/alarms";
        try {
            HttpResponse response= apiUtil.get(serviceName,region,ak,sk,url);
            String retJson = ApiUtil.convertStreamToString(response.getEntity().getContent());
            System.out.println(retJson);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        apiUtil.close();
    }






}
