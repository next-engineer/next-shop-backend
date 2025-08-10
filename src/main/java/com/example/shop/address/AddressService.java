package com.example.shop.address;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AddressService {
    private final AddressRepository addressRepository;

    public List<Address> getAllAddresses() {
        return addressRepository.findAll();
    }

    public Address getAddress(Long id) {
        return addressRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Address not found"));
    }

    public Address createAddress(AddressRequestDto dto) {
        Address address = Address.builder()
                .recipientName(dto.getRecipientName())
                .streetAddress(dto.getStreetAddress())
                .detailAddress(dto.getDetailAddress())
                .postalCode(dto.getPostalCode())
                .phoneNumber(dto.getPhoneNumber())
                .build();
        return addressRepository.save(address);
    }

    public Address updateAddress(Long id, AddressRequestDto dto) {
        Address address = getAddress(id);
        address.setRecipientName(dto.getRecipientName());
        address.setStreetAddress(dto.getStreetAddress());
        address.setDetailAddress(dto.getDetailAddress());
        address.setPostalCode(dto.getPostalCode());
        address.setPhoneNumber(dto.getPhoneNumber());
        return addressRepository.save(address);
    }

    public void deleteAddress(Long id) {
        addressRepository.deleteById(id);
    }
}
