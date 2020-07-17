package cc.ghast.janitor.v6.check;

import cc.ghast.janitor.v6.protocol.ProtocolVersion;
import com.comphenix.protocol.utility.MinecraftVersion;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface CheckData {
    CheckInformation.Type type();
    String var();
    int maxVls() default 15;
    boolean bannable() default true;
    boolean enabled() default true;
    ProtocolVersion[] incompatibleServer() default {};
    ProtocolVersion[] incompatibleClient() default  {};
    CheckInformation.Stage stage() default CheckInformation.Stage.RELEASE;
}
