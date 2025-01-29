package com.springueo.the_java_spring_bank.service.impl;

import com.springueo.the_java_spring_bank.DTO.*;

public interface UserService {
    BankResponse createAccount(UserRequests userRequests);

    BankResponse balanceEnquiry(EnquiryRequest request);

    String nameEnquiry(EnquiryRequest request);

    BankResponse creditAccount(CreditDebitRequest request);

    BankResponse debitAccount(CreditDebitRequest request);

    BankResponse transfer(TransferRequest request);

    BankResponse login(LoginDTO loginDTO);
}
