package com.demo.quickfix.initiator;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import quickfix.field.OrdType;
import quickfix.field.Side;

@Service
@RequiredArgsConstructor
public class MapperService {

  public Side calculateSideType(String rawSide) {
    rawSide = rawSide.toUpperCase();
    return switch (rawSide) {
      case "BUY" -> new Side(Side.BUY);
      case "SELL" -> new Side(Side.SELL);
      default ->
          throw new IllegalArgumentException(
              String.format("[%s] is not a valid Side Type", rawSide));
    };
  }

  public OrdType calculateOrderType(String rawOrderType) {
    rawOrderType = rawOrderType.toUpperCase();
    return switch (rawOrderType) {
      case "MARKET" -> new OrdType(OrdType.MARKET);
      case "LIMIT" -> new OrdType(OrdType.LIMIT);
      default ->
          throw new IllegalArgumentException(
              String.format("[%s] is not a valid Order Type", rawOrderType));
    };
  }
}
