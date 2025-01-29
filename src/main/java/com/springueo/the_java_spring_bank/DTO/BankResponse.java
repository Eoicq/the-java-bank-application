package com.springueo.the_java_spring_bank.DTO;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class BankResponse {

    private String responseCode;

    private String responseMessage;

    private AccountInfo accountInfo;
}
