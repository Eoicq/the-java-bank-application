package com.springueo.the_java_spring_bank.service.impl;

import com.springueo.the_java_spring_bank.DTO.EmailDetails;

public interface EmailService {

    void sendEmailAlert(EmailDetails emailDetails);
    void sendEmailWithAttachment(EmailDetails emailDetails);
}
