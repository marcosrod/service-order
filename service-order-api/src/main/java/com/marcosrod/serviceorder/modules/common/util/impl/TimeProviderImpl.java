package com.marcosrod.serviceorder.modules.common.util.impl;

import com.marcosrod.serviceorder.modules.common.util.TimeProvider;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class TimeProviderImpl implements TimeProvider {

    @Override
    public LocalDateTime getLocalDateTimeNow() {
        return LocalDateTime.now();
    }
}
