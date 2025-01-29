package com.springueo.the_java_spring_bank.DTO;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UserRequests {

    private String firstName;
    private String lastName;
    private String otherName;
    private String address;
    private String stateOfOrigin;
    private String accountNumber;
    private float accountBalance;
    private String email;
    private String password;
    private String phoneNumber;
    private String alternativePhoneNumber;
    private String status;

}
