package com.chinasofti.hwapi;

import com.obs.services.ObsClient;
import com.obs.services.ObsConfiguration;
import com.obs.services.exception.ObsException;
import com.obs.services.model.V4PostSignatureRequest;
import com.obs.services.model.V4PostSignatureResponse;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

/**
 * This sample demonstrates how to post object under specified bucket from Huawei
 * OBS using the OBS SDK for Java.
 *
 * obs help center http://support.huaweicloud.com/usermanual-obs/zh-cn_topic_0045829106.html
 *
 */
public class OBSTest
{
    private static final String endPoint = "obs.cn-north-1.myhwclouds.com";

    private static String ak = "4GKAHMGU7IGWDOYJELEY";
    private static String sk = "Oo6b2sF9WZKJ2ZFXStKAHIFVD7skj5v7ueeUKs0m";
    
    private static ObsClient obsClient;
    
    private static String bucketName = "lht-cloudmanager-test";
    
    private static String objectKey = "lht-obs-object-key-demo";
    
    public static void main(String[] args)
        throws IOException
    {
        ObsConfiguration config = new ObsConfiguration();
        config.setSocketTimeout(30000);
        config.setConnectionTimeout(10000);
        config.setEndPoint(endPoint);
        config.setHttpsOnly(true);
        config.setDisableDnsBucket(true);
        config.setSignatString("v4");
        config.setDefaultBucketLocation("CHINA");
        try
        {
            /*
             * Constructs a obs client instance with your account for accessing OBS
             */
            obsClient = new ObsClient(ak, sk, config);
            
            /*
             * Create bucket 
             */
            System.out.println("Create a new bucket for demo\n");
            obsClient.createBucket(bucketName);
            
            /*
             * Create sample file
             */
            File sampleFile = createSampleFile();
            
            /*
             * Claim a post object request
             */
            V4PostSignatureRequest request = new V4PostSignatureRequest();
            request.setExpires(3600);
            
            Map<String, Object> formParams = new HashMap<String, Object>();
            
            String contentType = "text/plain";
            formParams.put("acl", "public-read");
            formParams.put("content-type", contentType);
            formParams.put("x-amz-meta-meta1", "value1");
            formParams.put("x-amz-meta-meta2", "value2");
            
            request.setFormParams(formParams);
            
            V4PostSignatureResponse response = obsClient.createV4PostSignature(request);
            
            
            formParams.put("key", objectKey);
            formParams.put("policy", response.getPolicy());
            formParams.put("x-amz-algorithm", response.getAlgorithm());
            formParams.put("x-amz-credential", response.getCredential());
            formParams.put("x-amz-date", response.getDate());
            formParams.put("x-amz-signature", response.getSignature());
            
            String postUrl = "http://" + endPoint + "/" + bucketName;
            System.out.println("Creating object in v4 post way");
            System.out.println("\tpost url:" + postUrl);
            
           String res =  formUpload(postUrl, formParams, sampleFile, contentType);
           System.out.println("\tresponse:"+ res);
        }
        catch (Exception ex)
        {
            if (ex instanceof ObsException)
            {
                ObsException e = (ObsException)ex;
                System.out.println("Response Code: " + e.getResponseCode());
                System.out.println("Error Message: " + e.getErrorMessage());
                System.out.println("Error Code:       " + e.getErrorCode());
                System.out.println("Request ID:      " + e.getErrorRequestId());
                System.out.println("Host ID:           " + e.getErrorHostId());
            }
            else
            {
                ex.printStackTrace();
            }
        }
        finally
        {
            if (obsClient != null)
            {
                try
                {
                    /*
                     * Close obs client 
                     */
                    obsClient.close();
                }
                catch (IOException e)
                {
                }
            }
        }
    }
    
    private static String formUpload(String postUrl, Map<String, Object> formFields, File sampleFile, String contentType)
    {
        String res = "";
        HttpURLConnection conn = null;
        String boundary = "9431149156168";
        BufferedReader reader = null;
        DataInputStream in = null;
        OutputStream out = null;
        try
        {
            URL url = new URL(postUrl);
            conn = (HttpURLConnection)url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(30000);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("User-Agent", "OBS/Test");
            conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
            out = new DataOutputStream(conn.getOutputStream());
            
            // text
            if (formFields != null)
            {
                StringBuffer strBuf = new StringBuffer();
                Iterator<Entry<String, Object>> iter = formFields.entrySet().iterator();
                int i = 0;
                
                while (iter.hasNext())
                {
                    Entry<String, Object> entry = iter.next();
                    String inputName = entry.getKey();
                    Object inputValue = entry.getValue();
                    
                    if (inputValue == null)
                    {
                        continue;
                    }
                    
                    if (i == 0)
                    {
                        strBuf.append("--").append(boundary).append("\r\n");
                        strBuf.append("Content-Disposition: form-data; name=\"" + inputName + "\"\r\n\r\n");
                        strBuf.append(inputValue);
                    }
                    else
                    {
                        strBuf.append("\r\n").append("--").append(boundary).append("\r\n");
                        strBuf.append("Content-Disposition: form-data; name=\"" + inputName + "\"\r\n\r\n");
                        strBuf.append(inputValue);
                    }
                    
                    i++;
                }
                out.write(strBuf.toString().getBytes());
            }
            
            // file
            String filename = sampleFile.getName();
            if (contentType == null || contentType.equals(""))
            {
                contentType = "application/octet-stream";
            }
            
            StringBuffer strBuf = new StringBuffer();
            strBuf.append("\r\n").append("--").append(boundary).append("\r\n");
            strBuf.append("Content-Disposition: form-data; name=\"file\"; " + "filename=\"" + filename + "\"\r\n");
            strBuf.append("Content-Type: " + contentType + "\r\n\r\n");
            
            out.write(strBuf.toString().getBytes());
            
            in = new DataInputStream(new FileInputStream(sampleFile));
            int bytes = 0;
            byte[] bufferOut = new byte[1024];
            while ((bytes = in.read(bufferOut)) != -1)
            {
                out.write(bufferOut, 0, bytes);
            }
            
            byte[] endData = ("\r\n--" + boundary + "--\r\n").getBytes();
            out.write(endData);
            out.flush();
            
            // 读取返回数据
            strBuf = new StringBuffer();
            reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = null;
            while ((line = reader.readLine()) != null)
            {
                strBuf.append(line).append("\n");
            }
            res = strBuf.toString();
        }
        catch (Exception e)
        {
            System.out.println("Send post request exception: " + e);
            e.printStackTrace();
        }
        finally
        {
            if(out != null){
                try
                {
                    out.close();
                }
                catch (IOException e)
                {
                }
            }
            
            if(in != null){
                try
                {
                    in.close();
                }
                catch (IOException e)
                {
                }
            }
            if(reader != null){
                try
                {
                    reader.close();
                }
                catch (IOException e)
                {
                }
            }
            if (conn != null)
            {
                conn.disconnect();
                conn = null;
            }
        }
        
        return res;
    }
    
    private static File createSampleFile()
        throws IOException
    {
        File file = File.createTempFile("obs-java-sdk-", ".txt");
        file.deleteOnExit();
        Writer writer = new OutputStreamWriter(new FileOutputStream(file));
        writer.write("abcdefghijklmnopqrstuvwxyz\n");
        writer.write("0123456789011234567890\n");
        writer.close();
        
        return file;
    }
    
}
