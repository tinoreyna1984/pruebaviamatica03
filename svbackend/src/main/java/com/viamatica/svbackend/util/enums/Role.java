package com.viamatica.svbackend.util.enums;

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
            ),
            Arrays.asList(
                    Route.HOME,
                    Route.CONTRACTS,
                    Route.CUSTOMERS,
                    Route.CASH,
                    Route.ATTENTIONS,
                    Route.USERS,
                    Route.DASHBOARD
            )
    ),
    MANAGER(
            Arrays.asList(
                    Permission.SAVE_ONE_USER,
                    Permission.MODIFY_ONE_USER,
                    Permission.DELETE_ONE_USER,
                    Permission.READ_ALL_USERS,
                    Permission.ASSIGN_POS
            ),
            Arrays.asList(
                    Route.HOME,
                    Route.CONTRACTS,
                    Route.CUSTOMERS,
                    Route.CASH,
                    Route.ATTENTIONS,
                    Route.USERS
            )
    ),
    USER(
            Arrays.asList(
                    Permission.READ_ALL_USERS
            ),
            Arrays.asList(
                    Route.HOME,
                    Route.CONTRACTS,
                    Route.CUSTOMERS
                    //Route.CASH, // es para el gestor
                    //Route.ATTENTIONS, // es para el gestor
                    //Route.DASHBOARD // es para el administrador
            )
    ),;

    private List<Permission> permissions;
    private List<Route> routes;

    Role(List<Permission> permissions, List<Route> routes) {
        this.permissions = permissions;
        this.routes = routes;
    }

    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }

    public void setRoutes(List<Route> routes) {
        this.routes = routes;
    }

}
