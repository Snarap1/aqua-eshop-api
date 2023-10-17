package com.alibou.security.enums;


public enum DeliveryMethods  {
    DELIVERY_WORK("Доставка + работа мастера в г Минске", 20.0),
    DELIVERY("Доставка по Беларуси", 0.0),
    PICKUP("Самовывоз Минск ул Ф Скорины 52", 0.0);

    private final String displayName;
    private final double cost;

    DeliveryMethods (String displayName, double cost) {
        this.displayName = displayName;
        this.cost = cost;
    }

    public String getDisplayName() {
        return displayName;
    }

    public double getCost() {
        return cost;
    }
}