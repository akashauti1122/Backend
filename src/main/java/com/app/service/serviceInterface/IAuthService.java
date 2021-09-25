package com.app.service.serviceInterface;

import com.app.entity.dto.LoginRequest;
import com.app.entity.dto.LoginResponse;
import com.app.entity.modal.User;

public interface IAuthService {
    LoginResponse loginUser(LoginRequest request);
}
