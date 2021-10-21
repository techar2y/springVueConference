package com.arty.springVueConference.domains;

public enum EPermission
{
    USER_READ("user:read"),
    USER_WRITE("user:write"),
    ROOM_READ("room:read"),
    ROOM_WRITE("room:write"),
    PRESENTATION_READ("presentation:read"),
    PRESENTATION_WRITE("presentation:write"),
    ROLE_READ("role:read"),
    ROLE_WRITE("role:write");

    private final String permission;

    EPermission(String permission)
    {
        this.permission = permission;
    }

    public String getPermission() {
        return this.permission;
    }
}
