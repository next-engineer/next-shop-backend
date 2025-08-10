package com.example.shop.address;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity // 이 클래스는 데이터베이스 테이블과 매칭됩니다.
@Getter @Setter
@NoArgsConstructor // 기본 생성자를 만들어줍니다.
public class Address {

    @Id // 이 필드가 테이블의 Primary Key 입니다.
    @GeneratedValue(strategy = GenerationType.IDENTITY) // DB가 알아서 ID를 생성해줍니다.
    private Long id;

    private Long userId; // **나중에 추가**: 어떤 회원의 배송지인지 식별하기 위한 ID

    private String recipientName;   // 수령인 이름
    private String streetAddress;   // 도로명 주소
    private String detailAddress;   // 상세 주소
    private String postalCode;      // 우편번호
    private String phoneNumber;     // 연락처
    private boolean isDefault;      // 기본 배송지 여부

    @Builder // 빌더 패턴을 사용해 객체를 생성할 수 있게 합니다.
    public Address(Long userId, String recipientName, String streetAddress, String detailAddress, String postalCode, String phoneNumber, boolean isDefault) {
        this.userId = userId;
        this.recipientName = recipientName;
        this.streetAddress = streetAddress;
        this.detailAddress = detailAddress;
        this.postalCode = postalCode;
        this.phoneNumber = phoneNumber;
        this.isDefault = isDefault;
    }
}