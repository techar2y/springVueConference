package com.arty.springVueConference.domains;


import org.springframework.security.core.authority.SimpleGrantedAuthority;
import java.util.Set;
import java.util.stream.Collectors;

public enum ERole {
    /*ROLE_USER,
    ROLE_PRESENTER,
    ROLE_ADMIN*/
    USER(Set.of(EPermission.PRESENTATION_READ, EPermission.ROLE_READ, EPermission.ROOM_READ)),

    PRESENTER(Set.of(EPermission.PRESENTATION_READ, EPermission.PRESENTATION_WRITE,
                        EPermission.ROLE_READ, EPermission.ROOM_READ)),

    ADMIN(Set.of(EPermission.USER_READ, EPermission.USER_WRITE,
                    EPermission.PRESENTATION_READ, EPermission.PRESENTATION_WRITE,
                    EPermission.ROOM_READ, EPermission.ROOM_WRITE,
                    EPermission.ROLE_READ, EPermission.ROLE_WRITE));

    Set<EPermission> permissionSet;

    ERole(Set<EPermission> permissionSet) {
        this.permissionSet = permissionSet;
    }

    public Set<EPermission> getPermissionSet ()
    {
        return permissionSet;
    }

    public Set<SimpleGrantedAuthority> getAuthorities() {
        return getPermissionSet().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
    }
}
