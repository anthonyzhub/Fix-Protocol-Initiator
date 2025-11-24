package com.demo.quickfix.initiator;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import quickfix.*;
import quickfix.fix42.MessageCracker;

@Slf4j
@Service
public class InitiatorService extends MessageCracker implements Application {

    @Override
    public void onCreate(SessionID sessionID) {
        log.info("Session created {}", sessionID);
    }

    @Override
    public void onLogon(SessionID sessionID) {
        log.info("Logon successful {}", sessionID);
    }

    @Override
    public void onLogout(SessionID sessionID) {
        log.info("Logout successful {}", sessionID);
    }

    @Override
    public void toAdmin(Message message, SessionID sessionID) {
        // Objective: Handles administrative messages sent to the counter party
        log.info("To admin {}", sessionID);
    }

    @Override
    public void fromAdmin(Message message, SessionID sessionID) throws FieldNotFound, IncorrectDataFormat, IncorrectTagValue, RejectLogon {
        log.info("From admin {}", sessionID);
    }

    @Override
    public void toApp(Message message, SessionID sessionID) throws DoNotSend {
        // Objective: Callback for application-level messages sent to the counterparty
        log.info("Sending Message {}", message);
    }

    @Override
    public void fromApp(Message message, SessionID sessionID) throws FieldNotFound, IncorrectDataFormat, IncorrectTagValue, UnsupportedMessageType {
        // Core entry point for application-level messages. Every request from the counterparty will be received here
        crack(message, sessionID);
    }
}
