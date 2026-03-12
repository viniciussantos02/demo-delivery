package com.demoworks.demodelivery.delivery.tracking.domain.model;

import jakarta.persistence.Embeddable;
import lombok.*;

/** Object Value **/
@Embeddable
@Getter
@Builder
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class ContactPoint {
    private String zipCode;
    private String street;
    private String number;
    private String complement;
    private String name;
    private String phone;
}
