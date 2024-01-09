package com.viamatica.svbackend.util;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Getter
public enum Role {

    ADMINISTRATOR(
                    Arrays.asList(
                    Permission.SAVE_ONE_USER,
                    Permission.MODIFY_ONE_USER,
                    Permission.DELETE_ONE_USER,
                    Permission.READ_ALL_USERS,
                    Permission.APPROVE_USER_CREATION,
                    Permission.ASSIGN_POS
            )
    ),
    MANAGER(
            Arrays.asList(

                    Permission.SAVE_ONE_USER,
                    Permission.MODIFY_ONE_USER,
                    Permission.DELETE_ONE_USER,
                    Permission.READ_ALL_USERS,
                    Permission.ASSIGN_POS
            )
    ),
    USER(
            Arrays.asList(
                    Permission.READ_ALL_USERS
            )
    ),;

    private List<Permission> permissions;

    Role(List<Permission> permissions) {
        this.permissions = permissions;
    }

    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }

}
