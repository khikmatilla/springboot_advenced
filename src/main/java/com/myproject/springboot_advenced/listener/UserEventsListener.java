package com.myproject.springboot_advenced.listener;

import com.myproject.springboot_advenced.entity.Users;
import com.myproject.springboot_advenced.events.OtpGenerateEvent;
import com.myproject.springboot_advenced.events.SendMailEvent;
import com.myproject.springboot_advenced.repository.UsersRepository;
import com.myproject.springboot_advenced.service.MailService;
import com.myproject.springboot_advenced.service.OtpService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@Slf4j
@RequiredArgsConstructor
@Component
public class UserEventsListener {

    private final OtpService otpService;
    private final MailService mailService;


//    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT, condition = "#event.users.email ne null")
//    @Transactional(propagation = Propagation.REQUIRES_NEW)
//    @Order(2)
    @EventListener({OtpGenerateEvent.class})
    @Async
    public CompletableFuture<SendMailEvent> generateOtpEventListener(OtpGenerateEvent event) throws InterruptedException {
        Users users = event.getUsers();
        otpService.generateOtp(users);
        log.info("OTP Generate : {}", users);
        return CompletableFuture.completedFuture(new SendMailEvent(users.getId(), users.getEmail(), users.getOtp()));
    }

    @EventListener({SendMailEvent.class})
//    @Order(1)
    public void verificationMailSenderListner(SendMailEvent event) {
        Map<Object, Object> model = Map.of(
                "userId", event.getId(),
                "email", event.getEmail(),
                "otp", event.getOtp()
        );
        mailService.sendVerificationEmail(model);
        log.info("Mail send to : {}", event.getEmail());
    }
}
