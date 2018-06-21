package com.chinasofti.hwapi;

import com.chinasoft.util.ApiUtil;
import com.google.gson.Gson;
import org.apache.http.HttpResponse;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by VerRan.Liu on 2018/6/11.
 */
public class AOSTest {
    ApiUtil apiUtil =new ApiUtil() ;
    Gson gson =new Gson();

    String serviceName ="" ;
    String region  ="cn-north-1";
    String ak = "4GKAHMGU7IGWDOYJELEY";
    String sk = "Oo6b2sF9WZKJ2ZFXStKAHIFVD7skj5v7ueeUKs0m";
    String projectId= "fdfee0c14ec04bf08ac7bc1a360dceca";


    @Test
    public void testqueryTemplates(){
        String url ="https://aos.cn-north-1.myhuaweicloud.com/v2/templates/96444594-2344-6280-aaf6-bafae9bdf885";
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



    @Test
    public void testCreateStack(){
        String url ="https://aos.cn-north-1.myhuaweicloud.com/v2/stacks";
        String postbody = FileUtil.getStringFromFile("aos_input.json");
        Map<String,String> header =new HashMap<String ,String>();
        try {
            HttpResponse response= apiUtil.post(serviceName,region,ak,sk,url,postbody,  header);
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
