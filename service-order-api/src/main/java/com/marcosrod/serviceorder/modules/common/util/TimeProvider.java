package com.marcosrod.serviceorder.modules.common.util;

import java.time.LocalDateTime;

public interface TimeProvider {
    LocalDateTime getLocalDateTimeNow();
}
