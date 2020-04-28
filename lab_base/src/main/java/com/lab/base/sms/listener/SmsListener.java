package com.lab.base.sms.listener;


import com.lab.base.sms.util.SmsUtil;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RabbitListener(queues = "sms")
public class SmsListener {
    @Autowired
    private SmsUtil smsUtil;

    @Value("${aliyun.sms.signName}")
    private  String signName;

    @Value("${aliyun.sms.TemplateCode}")
    private  String templateCode;

    @RabbitHandler
    public void executeSms(Map<String,String> map){
        String mobile= map.get("mobile");
        String checkcode= map.get("checkcode");
        try {
            smsUtil.sendSms(mobile,templateCode,signName,"{\"checkcode\":\""+checkcode+"\"}");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
