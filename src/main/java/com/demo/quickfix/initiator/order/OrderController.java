package com.demo.quickfix.initiator.order;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OrderController {

  private final OrderService orderService;

  @PostMapping("/create-order")
  public void createOrder(@NonNull @RequestBody OrderDTO orderDTO) {
    orderService.createOrder(orderDTO);
  }
}
