package com.demo.quickfix.initiator;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import quickfix.*;
import quickfix.fix42.ExecutionReport;
import quickfix.fix42.MessageCracker;

@Slf4j
@Service
public class InitiatorService extends MessageCracker implements Application {

  @Override
  public void onCreate(SessionID sessionID) {
    // Executed when QuickFIX/J creates a new session
    log.info("Session created {}", sessionID);
  }

  @Override
  public void onLogon(SessionID sessionID) {
    // Func gets executed when counterparty successfully login
    log.info("Logon successful {}", sessionID);
  }

  @Override
  public void onLogout(SessionID sessionID) {
    // Notifies when a FIX session goes offline
    log.info("Logout successful {}", sessionID);
  }

  @Override
  public void toAdmin(Message message, SessionID sessionID) {
    // Handles admin messages sent to counterparty
    log.info("To admin {}", sessionID);
  }

  @Override
  public void fromAdmin(Message message, SessionID sessionID)
      throws FieldNotFound, IncorrectDataFormat, IncorrectTagValue, RejectLogon {
    // Handles admin message sent from counterparty
    log.info("From admin {}", sessionID);
  }

  @Override
  public void toApp(Message message, SessionID sessionID) throws DoNotSend {
    // Callback func that executes right before sending an app-level message to counterparty
    // IMPORTANT: App-level and admin-level messages are different
    log.info("Sending Message {}", message);
  }

  @Override
  public void fromApp(Message message, SessionID sessionID)
      throws FieldNotFound, IncorrectDataFormat, IncorrectTagValue, UnsupportedMessageType {
    // Accepts app-level messages from counterparty
    crack(message, sessionID);
  }

  @quickfix.MessageCracker.Handler
  public void onMessage(ExecutionReport executionReport, SessionID sessionID) {
    log.info("Received ExecutionReport {}", executionReport);
  }
}
