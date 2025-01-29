package com.springueo.the_java_spring_bank.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class AccountInfo {

    @Schema(
            name = "User Account Name"
    )
    private String accountName;

    @Schema(
            name = "User Account Balance"
    )
    private BigDecimal accountBalance;

    @Schema(
            name = "User Account Number"
    )
    private String accountNumber;
}
