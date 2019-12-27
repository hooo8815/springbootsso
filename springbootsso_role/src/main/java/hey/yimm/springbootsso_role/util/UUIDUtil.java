package hey.yimm.springbootsso_role.util;

import java.util.UUID;

public class UUIDUtil {
    public static String getUUID(){
        return UUID.randomUUID().toString();
    }
}
