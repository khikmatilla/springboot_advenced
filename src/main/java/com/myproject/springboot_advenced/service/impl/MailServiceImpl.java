package com.myproject.springboot_advenced.service.impl;

import com.myproject.springboot_advenced.service.CacheService;
import com.myproject.springboot_advenced.service.MailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
public class MailServiceImpl implements MailService {

    private boolean on = false;

    private final CacheService cacheService;

    public MailServiceImpl(CacheService cacheService) {
        this.cacheService = cacheService;
    }

    @Override
    public void sendVerificationEmail(Map<Object, Object> model) {
       if (on){
           log.info("Connecting SMTP server : ");
           log.info("Sending Mail : {}", model);
       }else {
           log.info("Cashing Mail model {}", model);
           cacheService.put(model);
       }
    }

    @Override
    public boolean turnOnOffSmtp() {
        this.on = !on;
        return this.on;
    }

    public Boolean isSmtpActive() {
        return this.on;
    }
}
