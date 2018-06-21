package com.chinasoft.service;

import com.chinasoft.util.ApiConfiguration;
import com.chinasoft.util.ApiUtil;
import com.google.gson.Gson;
import org.apache.http.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by VerRan.Liu on 2017/9/30.
 */
@Service
public class HuaweiApiService {

    @Autowired
    ApiUtil apiUtil;
    private Gson gson = new Gson();
    @Autowired
    private ApiConfiguration apiConfiguration;

    private static final Logger logger = LoggerFactory.getLogger(HuaweiApiService.class);

    private Map buildHeader() {//http://support.huaweicloud.com/api-smn/zh-cn_topic_0036017327.html 参数说明地址
        Map<String,String> header =new HashMap();
        header.put("x-sdk-date","20150907T101459Z");//(YYYYMMDD'T'HHMMSS'Z')
        header.put("Authorization","");
        header.put("Host","");
        header.put("Content-type","application/json");
        header.put("Content-Length","3495");//POST/PUT请求必填。 GET不能包含。
        header.put("X-Project-Id","3495");//POST/PUT请求必填。 GET不能包含。
        header.put("X-Auth-Token","MIIN6gYJKoZIhvcNAQcCoIIN2zCCDdcCAQExDTALBglghkgBZQMEAgEwggw4BgkqhkiG9w0BBwGgggwpBIIMJXsidG9rZW4iOnsiaXNzdWVkX2F0IjoiMjAxNy0wOS0zMFQwOTowODo0Ni4yODEwMDBaIiwiZXhwaXJlc19hdCI6IjIwMTctMTAtMDFUMDk6MDg6NDYuMjgxMDAwWiIsIm1ldGhvZHMiOlsicGFzc3dvcmQiXSwicHJvamVjdCI6eyJuYW1lIjoiY24tbm9ydGgtMSIsImlkIjoiMjdkZDNmOGU3NDg5NGRjZGJkM2ZhZWQ3MWI2MzIwY2MiLCJkb21haW4iOnsibmFtZSI6ImNoaW5hc29mdGluYyIsImlkIjoiZTRiNWUxOWJhNGNmNDA0OWFjOTZiZDI2OGM0OGJmNWUifX0sInVzZXIiOnsiZG9tYWluIjp7Im5hbWUiOiJjaGluYXNvZnRpbmMiLCJpZCI6ImU0YjVlMTliYTRjZjQwNDlhYzk2YmQyNjhjNDhiZjVlIn0sImlkIjoiODAyOGM3MjRjZjM1NDEzNTgyMWMzM2RmZmZlOTRiNzEiLCJuYW1lIjoiY2hpbmFzb2Z0aW5jIn0sImNhdGFsb2ciOltdLCJyb2xlcyI6W3sibmFtZSI6InRlX2FnZW5jeSIsImlkIjoiNmYwYzhjMTZlYzllNDgxY2E4M2M4YjkwYmYyNzgxODcifSx7Im5hbWUiOiJ0ZV9hZG1pbiIsImlkIjoiZTJkNzU0Mjk1NDkwNDRkMmIzMTNmYmRkMTE1MjBmN2QifSx7ImlkIjoiMCIsIm5hbWUiOiJvcF9nYXRlZF9DTi1TT1VUSC0zIn0seyJpZCI6IjAiLCJuYW1lIjoib3BfZ2F0ZWRfbGl3ZWl5ZXRlc3QifSx7ImlkIjoiMCIsIm5hbWUiOiJvcF9nYXRlZF9haXNfZGVmb2cifSx7ImlkIjoiMCIsIm5hbWUiOiJvcF9nYXRlZF9tb2xhcCJ9LHsiaWQiOiIwIiwibmFtZSI6Im9wX2dhdGVkX3NjY193YWYifSx7ImlkIjoiMCIsIm5hbWUiOiJvcF9nYXRlZF9haXNfc3VwZXJfcmVzb2x1dGlvbiJ9LHsiaWQiOiIwIiwibmFtZSI6Im9wX2dhdGVkX2hpZCJ9LHsiaWQiOiIwIiwibmFtZSI6Im9wX2dhdGVkX2Fpc19wYXRoX3Byb2dyYW0ifSx7ImlkIjoiMCIsIm5hbWUiOiJvcF9nYXRlZF9kZG0ifSx7ImlkIjoiMCIsIm5hbWUiOiJvcF9nYXRlZF9zY2NfcHRzIn0seyJpZCI6IjAiLCJuYW1lIjoib3BfZ2F0ZWRfZHBzIn0seyJpZCI6IjAiLCJuYW1lIjoib3BfZ2F0ZWRfc2NjX2FycyJ9LHsiaWQiOiIwIiwibmFtZSI6Im9wX2dhdGVkX3NjY19zYXMifSx7ImlkIjoiMCIsIm5hbWUiOiJvcF9nYXRlZF91cXVlcnkifSx7ImlkIjoiMCIsIm5hbWUiOiJvcF9nYXRlZF9jbG91ZGNjIn0seyJpZCI6IjAiLCJuYW1lIjoib3BfZ2F0ZWRfZWNzcXVpY2tkZXBsb3kifSx7ImlkIjoiMCIsIm5hbWUiOiJvcF9nYXRlZF9kd3NfZWNyYSJ9LHsiaWQiOiIwIiwibmFtZSI6Im9wX2dhdGVkX2NzYnMifSx7ImlkIjoiMCIsIm5hbWUiOiJvcF9nYXRlZF9jbG91ZHRhYmxlIn0seyJpZCI6IjAiLCJuYW1lIjoib3BfZ2F0ZWRfc2NjX3d0cCJ9LHsiaWQiOiIwIiwibmFtZSI6Im9wX2dhdGVkX3NjY19kYnNzIn0seyJpZCI6IjAiLCJuYW1lIjoib3BfZ2F0ZWRfY3JzIn0seyJpZCI6IjAiLCJuYW1lIjoib3BfZ2F0ZWRfZWNzX2ZwZ2EifSx7ImlkIjoiMCIsIm5hbWUiOiJvcF9nYXRlZF9JTSJ9LHsiaWQiOiIwIiwibmFtZSI6Im9wX2dhdGVkX2Fpc19iaW5fcGFja2luZyJ9LHsiaWQiOiIwIiwibmFtZSI6Im9wX2dhdGVkX2FpcyJ9LHsiaWQiOiIwIiwibmFtZSI6Im9wX2dhdGVkX0xUUyJ9LHsiaWQiOiIwIiwibmFtZSI6Im9wX2dhdGVkX2Vjc19ncHVfY2EifSx7ImlkIjoiMCIsIm5hbWUiOiJvcF9nYXRlZF9zYXBoYW5hIn0seyJpZCI6IjAiLCJuYW1lIjoib3BfZ2F0ZWRfY2FkIn0seyJpZCI6IjAiLCJuYW1lIjoib3BfZ2F0ZWRfY2FlIn0seyJpZCI6IjAiLCJuYW1lIjoib3BfZ2F0ZWRfaHNzIn0seyJpZCI6IjAiLCJuYW1lIjoib3BfZ2F0ZWRfYWlzX3RpbWVfYW5vbWFseSJ9LHsiaWQiOiIwIiwibmFtZSI6Im9wX2dhdGVkX2RjcyJ9LHsiaWQiOiIwIiwibmFtZSI6Im9wX2dhdGVkX29tcyJ9LHsiaWQiOiIwIiwibmFtZSI6Im9wX2dhdGVkX2Z1bmN0aW9uc3RhZ2UifSx7ImlkIjoiMCIsIm5hbWUiOiJvcF9nYXRlZF9kdGEifSx7ImlkIjoiMCIsIm5hbWUiOiJvcF9nYXRlZF9lY3NfaGlnaGJhbmQifSx7ImlkIjoiMCIsIm5hbWUiOiJvcF9nYXRlZF9zY2NfaHNzIn0seyJpZCI6IjAiLCJuYW1lIjoib3BfZ2F0ZWRfYV9DTi1TT1VUSC0zIn0seyJpZCI6IjAiLCJuYW1lIjoib3BfZ2F0ZWRfZHdzIn0seyJpZCI6IjAiLCJuYW1lIjoib3BfZ2F0ZWRfcmRzX3NxbHNlcnZlciJ9LHsiaWQiOiIwIiwibmFtZSI6Im9wX2dhdGVkX2NkbSJ9LHsiaWQiOiIwIiwibmFtZSI6Im9wX2dhdGVkX2dwdSJ9LHsiaWQiOiIwIiwibmFtZSI6Im9wX2dhdGVkX2RucyJ9LHsiaWQiOiIwIiwibmFtZSI6Im9wX2dhdGVkX2Vjc19ocGMifSx7ImlkIjoiMCIsIm5hbWUiOiJvcF9nYXRlZF9pbXNtcyJ9LHsiaWQiOiIwIiwibmFtZSI6Im9wX2dhdGVkX3NjY19zY3MifSx7ImlkIjoiMCIsIm5hbWUiOiJvcF9nYXRlZF9tbHMifSx7ImlkIjoiMCIsIm5hbWUiOiJvcF9nYXRlZF9zZXJ2aWNlc3RhZ2UifSx7ImlkIjoiMCIsIm5hbWUiOiJvcF9nYXRlZF9zY2Nfc3NhIn0seyJpZCI6IjAiLCJuYW1lIjoib3BfZ2F0ZWRfYWlzX2RhcmtfZW5oYW5jZSJ9LHsiaWQiOiIwIiwibmFtZSI6Im9wX2dhdGVkX3ZvaWNlY2FsbCJ9LHsiaWQiOiIwIiwibmFtZSI6Im9wX2dhdGVkX2Fpc19pbWFnZV9hbnRpcG9ybiJ9LHsiaWQiOiIwIiwibmFtZSI6Im9wX2dhdGVkX3dlYnNjYW4ifSx7ImlkIjoiMCIsIm5hbWUiOiJvcF9nYXRlZF93ZWl5ZTIifSx7ImlkIjoiMCIsIm5hbWUiOiJvcF9nYXRlZF93ZWl5ZTEifSx7ImlkIjoiMCIsIm5hbWUiOiJvcF9nYXRlZF9lY3BjIn0seyJpZCI6IjAiLCJuYW1lIjoib3BfZ2F0ZWRfZWxhc3RpY3NlYXJjaCJ9LHsiaWQiOiIwIiwibmFtZSI6Im9wX2dhdGVkX2NzIn0seyJpZCI6IjAiLCJuYW1lIjoib3BfZ2F0ZWRfZWNzX2Rpc2tpbnRlbnNpdmUifSx7ImlkIjoiMCIsIm5hbWUiOiJvcF9nYXRlZF9yZHNfY3VzdG9tZXJjbG91ZCJ9LHsiaWQiOiIwIiwibmFtZSI6Im9wX2dhdGVkX2JtcyJ9LHsiaWQiOiIwIiwibmFtZSI6Im9wX2dhdGVkX3Jkc19wZyJ9LHsiaWQiOiIwIiwibmFtZSI6Im9wX2dhdGVkX2Vjc19sYXJnZXJhbSJ9LHsiaWQiOiIwIiwibmFtZSI6Im9wX2dhdGVkX2NjcyJ9LHsiaWQiOiIwIiwibmFtZSI6Im9wX3BjX3ZlbmRvcl9zdWJ1c2VyIn0seyJpZCI6IjAiLCJuYW1lIjoib3BfbGVnYWN5In1dfX0xggGFMIIBgQIBATBcMFcxCzAJBgNVBAYTAlVTMQ4wDAYDVQQIDAVVbnNldDEOMAwGA1UEBwwFVW5zZXQxDjAMBgNVBAoMBVVuc2V0MRgwFgYDVQQDDA93d3cuZXhhbXBsZS5jb20CAQEwCwYJYIZIAWUDBAIBMA0GCSqGSIb3DQEBAQUABIIBAGpdqMYzLgmFbEAdXu6hJFVxSDSiZNSLxuE4nSRqMOmtXUBDwCemwh1yWZyRpogw8DTKFQww8rZRJTA5SPe8aPmuHeEX3i09");//POST/PUT请求必填。 GET不能包含。
        return header;
    }

    /**
     * 获取CES数据服务
     * @return
     */
    public String fetchCESData() {
        long mills_20_min=60*60*1000;//20分钟的毫秒数
        long to = System.currentTimeMillis();
        long from=to-mills_20_min;
        String serviceName="";
        String region="";
        String url ="https://ces.cn-north-1.myhuaweicloud.com/V1.0/fdfee0c14ec04bf08ac7bc1a360dceca" +
                "/metric-data?namespace=SYS.ECS&metric_name=cpu_util" +
                "&dim.0=instance_id,bfb5f833-f722-43ca-8391-8477e6dc119b&period=1200&filter=min"
                +"&from="+from+"&to="+to;
        String retJson ="";
        try {
            HttpResponse response= apiUtil.get(serviceName,region,apiConfiguration.getAk(),apiConfiguration.getSk(),url);
            retJson= ApiUtil.convertStreamToString(response.getEntity().getContent());
            System.out.println(retJson);
        } catch (IOException e) {
            logger.error(e.getMessage());
            return e.getMessage();
        } catch (Exception e) {
            logger.error(e.getMessage());
            return e.getMessage();
        }
        apiUtil.close();
        return retJson;
    }
}
