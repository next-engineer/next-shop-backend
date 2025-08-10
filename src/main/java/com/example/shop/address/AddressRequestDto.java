package com.example.shop.address;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressRequestDto {
    private String recipientName;
    private String streetAddress;
    private String detailAddress;
    private String postalCode;
    private String phoneNumber;
}
