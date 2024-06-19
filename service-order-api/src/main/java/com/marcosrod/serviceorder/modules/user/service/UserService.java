package com.marcosrod.serviceorder.modules.user.service;

import java.util.List;

public interface UserService {
    Boolean existsByIds(List<Long> userIds);
}
