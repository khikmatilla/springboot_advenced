package com.myproject.springboot_advenced;

import com.myproject.springboot_advenced.service.CacheService;
import com.myproject.springboot_advenced.service.MailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@Slf4j
@EnableAsync
@EnableScheduling
@RequiredArgsConstructor
@SpringBootApplication
public class SpringbootAdvencedApplication {

    private final CacheService cacheService;
    private final MailService mailService;

    public static void main(String[] args) {
        SpringApplication.run(SpringbootAdvencedApplication.class, args);
    }

    @Scheduled(initialDelay = 5, fixedDelay = 60, timeUnit = TimeUnit.SECONDS)
    public void senCachedVerificationMails (){
        if (mailService.isSmtpActive()) {
            ConcurrentHashMap<Object, Map<Object, Object>> cache = cacheService.getCache();
            cache.forEach((k, value) -> {
                 mailService.sendVerificationEmail(value);
                 cache.remove(k);
            });
        }else {
            log.info("SMTP service isn't active");
        }
    }

}
