package com.suncontrol.core.constant.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
@AllArgsConstructor
public enum Role {
    ROLE_ADMIN("ADMIN"),
    ROLE_USER("USER");
    private final String role;
    // TODO : 계정 권한
    public static final List<Role> LIST =
            Arrays.asList(Role.values());

    private static final Map<String, Role> CODE_MAP =
            Collections.unmodifiableMap(Stream.of(values())
                    .collect(Collectors.toMap(
                            Role::getRole,
                            Function.identity()
                    )));

    public static Role fromRole(String role) {
        return CODE_MAP.get(role);
    }
}
