package com.myproject.springboot_advenced.service;

import java.util.Map;

public interface MailService {

    void sendVerificationEmail(Map<Object, Object>model);

    boolean turnOnOffSmtp();

    Boolean isSmtpActive();
}
