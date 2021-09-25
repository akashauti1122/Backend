package com.app.service.serviceInterface;

import com.app.entity.dto.LoginRequest;
import com.app.entity.modal.User;

public interface IAuthService {
    User loginUser(LoginRequest request);
}
