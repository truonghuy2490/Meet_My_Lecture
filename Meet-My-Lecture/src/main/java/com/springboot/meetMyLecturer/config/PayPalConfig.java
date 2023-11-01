package com.springboot.meetMyLecturer.config;

import java.util.HashMap;
import java.util.Map;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.OAuthTokenCredential;
import com.paypal.base.rest.PayPalRESTException;

@Configuration
public class PayPalConfig {

    private static final String clientId = "ARddVOEuOrlTvnNwink8HwvG71YSw8bWhc5uPOZ-zYDMjXmU0RqmDU-NsRPSnT8iC0LfizLE6f5uIBhA";

    private static final String clientSecret = "EMq1GpKnCWVsVLyubPvZKCyc9NUSrX2wMF-vlOldaLcA0Uk9DfVLyyUoF8TXNhBx92MAIEinI3g3ifnR";

    private String mode ="sandbox";

    @Bean
    public Map<String, String> paypalSdkConfig(){
        Map<String, String> sdkConfig = new HashMap<>();
        sdkConfig.put("mode", mode);
        return sdkConfig;
    }

    @Bean
    public OAuthTokenCredential authTokenCredential(){
        return new OAuthTokenCredential(clientId, clientSecret, paypalSdkConfig());
    }

    @Bean
    public APIContext apiContext() throws PayPalRESTException{
        APIContext apiContext = new APIContext(authTokenCredential().getAccessToken());
        apiContext.setConfigurationMap(paypalSdkConfig());
        return apiContext;
    }

}
