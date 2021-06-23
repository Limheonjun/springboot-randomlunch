package emgc.randomlunch.enums;

import java.util.Arrays;
import java.util.stream.Collectors;

public enum Role {
    ADMIN, USER, ANONYMOUS ;

    public static String getRoleHierarchy() {
        return Arrays.stream(Role.values())
                .map(role -> "ROLE_"+role.getRole())
                .collect(Collectors.joining(" > "));
    }

    public String getRole(){
        return name();
    }
}
