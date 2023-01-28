package jpabook.jpashop.domain;

import jakarta.persistence.Embeddable;
import lombok.Getter;

@Embeddable
@Getter
public class Address {

    private String city;
    private String street;
    private String zipcode;

    // jpa 스펙상 만들어둔 기본 생성자, 실제로 호출될 일은 없음
    protected Address() {}

    // 생성할 때만 값을 집어넣을 수 있게, 값이 변하지 않도록 보존하기 위해 setter를 제공하지 않는 대신 생성자를 만들어줌
    public Address(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }
}
