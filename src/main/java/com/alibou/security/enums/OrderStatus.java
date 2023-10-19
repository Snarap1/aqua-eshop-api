package com.alibou.security.enums;

import lombok.Getter;

@Getter
public enum OrderStatus {
    PROCESSING("В обработке"),
    SHIPPED("Доставляется"),
    DELIVERED("Доставлен");

    private final String displayName;

    OrderStatus(String displayName) {
        this.displayName = displayName;
    }

}
