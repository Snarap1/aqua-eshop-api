package com.alibou.security.enums;

public enum OrderStatus {
    PROCESSING("В обработке"),
    SHIPPED("Доставляется"),
    DELIVERED("Доставлен");

    private final String displayName;

    OrderStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
