package com.mystory.domain;

public enum UserRoleEnum {
    USER(Authority.USER),
    ADMIN(Authority.ADMIN);

    private final String autority;

    UserRoleEnum(String autority) {
        this.autority = autority;
    }

    public String getAuthority() {
        return this.autority;
    }

    public static class Authority {
        public static final String USER = "ROLE_USER";
        public static final String ADMIN = "ROLE_ADMIN";
    }
}
