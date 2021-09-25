package com.app.entity.modal;

public enum UserType {
    DOCTOR, PATIENT, ADMIN;

    @Override
    public String toString() {
        return name();
    }
}
