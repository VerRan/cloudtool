package com.chinasoft.service;

import com.chinasoft.util.ApiUtil;
import com.google.gson.Gson;
import org.apache.http.HttpResponse;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * Created by VerRan.Liu on 2018/6/11.
 */
@Service
public class AosSericeImp implements AosSerice {
    ApiUtil apiUtil =new ApiUtil() ;
    Gson gson =new Gson();

    String serviceName ="" ;
    String region  ="cn-north-1";
    String ak = "4GKAHMGU7IGWDOYJELEY";
    String sk = "Oo6b2sF9WZKJ2ZFXStKAHIFVD7skj5v7ueeUKs0m";
    String projectId= "fdfee0c14ec04bf08ac7bc1a360dceca";

    @Override
    public String queryAOSTemplate(String templateId) {
        String url ="https://aos.cn-north-1.myhuaweicloud.com/v2/templates/"+templateId;
        String ret ="";
        try {
            HttpResponse response= apiUtil.get(serviceName,region,ak,sk,url);
            ret = ApiUtil.convertStreamToString(response.getEntity().getContent());
            System.out.println(ret);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        apiUtil.close();
        return ret;
    }
}
