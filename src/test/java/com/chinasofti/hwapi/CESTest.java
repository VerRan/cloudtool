package com.chinasofti.hwapi;

import com.chinasoft.util.ApiUtil;
import com.google.gson.Gson;
import org.apache.http.HttpResponse;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by VerRan.Liu on 2018/6/8.
 */
public class CESTest {
    ApiUtil apiUtil =new ApiUtil() ;
    Gson gson =new Gson();

    String serviceName ="" ;
    String region  ="";
    String ak = "4GKAHMGU7IGWDOYJELEY";
    String sk = "Oo6b2sF9WZKJ2ZFXStKAHIFVD7skj5v7ueeUKs0m";
    @Test
    public void testQueryCESData(){
        long mills_20_min=60*60*1000;//20分钟的毫秒数
        long to = System.currentTimeMillis();
        long from=to-mills_20_min;

        String url ="https://ces.cn-north-1.myhuaweicloud.com/V1.0/fdfee0c14ec04bf08ac7bc1a360dceca" +
                "/metric-data?namespace=SYS.ECS&metric_name=cpu_util" +
                "&dim.0=instance_id,bfb5f833-f722-43ca-8391-8477e6dc119b&period=1200&filter=min"
                +"&from="+from+"&to="+to;
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
