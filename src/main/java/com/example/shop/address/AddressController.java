package com.example.shop.address;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // 이 클래스는 REST API 요청을 처리합니다.
@RequestMapping("/api/v1/addresses") // 이 컨트롤러의 모든 메서드는 /api/v1/addresses URL로 시작합니다.
@RequiredArgsConstructor
public class AddressController {

    private final AddressService addressService;

    // 특정 유저의 배송지 목록 조회 API
    @GetMapping
    public ResponseEntity<List<Address>> getMyAddresses() {
        // **나중에 수정**: 현재는 userId를 1로 고정했지만, 실제로는 로그인한 사용자의 ID를 가져와야 합니다.
        Long currentUserId = 1L;
        List<Address> addresses = addressService.findAddressesByUserId(currentUserId);
        return ResponseEntity.ok(addresses);
    }

    // 배송지 생성 API
    @PostMapping
    public ResponseEntity<Address> createAddress(@RequestBody Address address) {
        // **나중에 수정**: address 객체에 현재 로그인한 사용자의 ID를 설정해줘야 합니다.
        address.setUserId(1L);
        Address createdAddress = addressService.createAddress(address);
        return ResponseEntity.ok(createdAddress);
    }

    // 배송지 삭제 API
    @DeleteMapping("/{addressId}")
    public ResponseEntity<Void> deleteAddress(@PathVariable Long addressId) {
        addressService.deleteAddress(addressId);
        return ResponseEntity.noContent().build(); // 성공적으로 삭제되었지만 보여줄 내용은 없음을 의미
    }
}