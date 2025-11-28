package com.demo.quickfix.initiator.order;

public record OrderDTO(String symbol, Double quantity, String side, String orderType) {
}
