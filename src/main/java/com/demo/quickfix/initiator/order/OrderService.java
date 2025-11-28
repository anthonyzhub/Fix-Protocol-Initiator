package com.demo.quickfix.initiator.order;

import io.allune.quickfixj.spring.boot.starter.template.QuickFixJTemplate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import quickfix.Initiator;
import quickfix.SessionID;
import quickfix.field.*;
import quickfix.fix42.NewOrderSingle;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

    private final Initiator initiator;
    private final QuickFixJTemplate quickFixJTemplate;

    public void createOrder(OrderDTO orderDTO) {
        NewOrderSingle newOrder = new NewOrderSingle();

        log.info("Constructing order");
        newOrder.set(new ClOrdID(UUID.randomUUID().toString())); // CLOrdID = Client Order ID
        newOrder.set(new HandlInst(HandlInst.AUTOMATED_EXECUTION_ORDER_PUBLIC_BROKER_INTERVENTION_OK));
        newOrder.set(new Symbol(orderDTO.symbol()));
        newOrder.set(calculateSideType(orderDTO.side()));
        newOrder.set(new TransactTime(LocalDateTime.now()));
        newOrder.set(calculateOrderType(orderDTO.orderType()));
        newOrder.set(new OrderQty(orderDTO.quantity()));

        // IMPORTANT: Only 1 session is defined in this project.
        //  If there were multiple, we would need to fetch the appropriate one.
        log.info("Sending order");
        SessionID sessionID = initiator.getSessions().get(0);
        quickFixJTemplate.send(newOrder, sessionID);
    }

    private Side calculateSideType(String rawSide) {
        rawSide = rawSide.toUpperCase();
        return switch (rawSide) {
            case "BUY" -> new Side(Side.BUY);
            case "SELL" -> new Side(Side.SELL);
            default -> throw new IllegalArgumentException(String.format("[%s] is not a valid Side Type", rawSide));
        };
    }

    private OrdType calculateOrderType(String rawOrderType) {
        rawOrderType = rawOrderType.toUpperCase();
        return switch (rawOrderType) {
            case "MARKET" -> new OrdType(OrdType.MARKET);
            case "LIMIT" -> new OrdType(OrdType.LIMIT);
            default -> throw new IllegalArgumentException(String.format("[%s] is not a valid Order Type", rawOrderType));
        };
    }
}
