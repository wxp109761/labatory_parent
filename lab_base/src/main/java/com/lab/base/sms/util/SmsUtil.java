package com.lab.base.sms.util;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.profile.DefaultProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/*
pom.xml
<dependency>
  <groupId>com.aliyun</groupId>
  <artifactId>aliyun-java-sdk-core</artifactId>
  <version>4.0.3</version>
</dependency>
*/
@Component
public class SmsUtil {
    static final String product="Dysmsapi";
    static final String domain="dysmsapi.aliyuncs.com";
    @Autowired
    private Environment env;
    public SendSmsResponse sendSms(String mobile,String template_code,String sign_name,String param) throws Exception{
        String accessKeyId=env.getProperty("aliyun.sms.accessKeyId");
        String accessKeySecret=env.getProperty("aliyun.sms.accessKeysecret");
        //可自助调整超时时间
        System.setProperty("sun.net.client.defaultConnectTimeout","10000");
        System.setProperty("sun.net.client.defaultReadTimeout","10000");
        //初始化acsClient,暂不支持region化
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId,accessKeySecret);
        DefaultProfile.addEndpoint("cn-hangzhou","cn-hangzhou",product,domain);
        IAcsClient client = new DefaultAcsClient(profile);
        SendSmsRequest request=new SendSmsRequest();
        request.setPhoneNumbers(mobile);
        request.setSignName(sign_name);
        request.setTemplateCode(template_code);
        request.setTemplateParam(param);
        request.setOutId("yourOutId");
        SendSmsResponse sendSmsResponse=client.getAcsResponse(request);
        return  sendSmsResponse;


    }

}