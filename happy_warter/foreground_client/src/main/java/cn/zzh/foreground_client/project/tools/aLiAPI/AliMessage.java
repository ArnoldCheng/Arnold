package cn.zzh.foreground_client.project.tools.aLiAPI;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsResponse;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
@ConfigurationProperties("message")
public class AliMessage {
    private static final Logger logger=LoggerFactory.getLogger(AliMessage.class);
    private static final String PRODUCT = "Dysmsapi";
    private static final String DOMAIN = "dysmsapi.aliyuncs.com";
    private  String accessKeyId;
    private  String accessKeySecret;
    private  String signame;
    private  String templateCode;
    private  String endpointName;
    private  String regionId;

    public String getAccessKeyId() {
        return accessKeyId;
    }
    public void setAccessKeyId(String accessKeyId) {
        this.accessKeyId = accessKeyId;
    }
    public String getAccessKeySecret() {
        return accessKeySecret;
    }
    public void setAccessKeySecret(String accessKeySecret) {
        this.accessKeySecret = accessKeySecret;
    }
    public String getSigname() {
        return signame;
    }
    public void setSigname(String signame) {
        this.signame = signame;
    }
    public String getTemplateCode() {
        return templateCode;
    }
    public void setTemplateCode(String templateCode) {
        this.templateCode = templateCode;
    }
    public String getEndpointName() {
        return endpointName;
    }
    public void setEndpointName(String endpointName) {
        this.endpointName = endpointName;
    }
    public String getRegionId() {
        return regionId;
    }
    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }

    public SendSmsResponse sendSms(String phoneNumber, String code) throws ClientException {
        logger.info("发送短信服务的入参是"+"phoneNumber = "+phoneNumber+"   ；code = "+code);
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
        DefaultProfile.addEndpoint(endpointName, regionId, PRODUCT, DOMAIN);
        IAcsClient acsClient = new DefaultAcsClient(profile);
        SendSmsRequest request = new SendSmsRequest();
        request.setPhoneNumbers(phoneNumber);
        request.setSignName(signame);
        request.setTemplateCode(templateCode);
        request.setTemplateParam("{\"code\":\"" + code + "\"}");
        return acsClient.getAcsResponse(request);
    }


    private QuerySendDetailsResponse querySendDetails(String bizId, String phoneNumber) throws ClientException {
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", PRODUCT, DOMAIN);
        IAcsClient acsClient = new DefaultAcsClient(profile);
        QuerySendDetailsRequest request = new QuerySendDetailsRequest();
        request.setPhoneNumber(phoneNumber);
        request.setBizId(bizId);
        SimpleDateFormat ft = new SimpleDateFormat("yyyyMMdd");
        request.setSendDate(ft.format(new Date()));
        request.setPageSize(10L);
        request.setCurrentPage(1L);
        return acsClient.getAcsResponse(request);
    }
}

