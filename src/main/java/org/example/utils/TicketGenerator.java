package org.example.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TicketGenerator {
    public static String generateTicketNumber(int tableId) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String timestamp = now.format(formatter);

        return timestamp + "-Table" + tableId;
    }
}
