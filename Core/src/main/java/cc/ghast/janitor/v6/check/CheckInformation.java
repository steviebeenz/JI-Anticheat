package cc.ghast.janitor.v6.check;

import cc.ghast.janitor.v6.protocol.ProtocolVersion;
import com.comphenix.protocol.utility.MinecraftProtocolVersion;
import com.comphenix.protocol.utility.MinecraftVersion;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Ghast
 * @since 17/07/2020
 * Ghast Holdings LLC / JIv6 © 2020
 */

@AllArgsConstructor
@Getter
public class CheckInformation {
    private final Type type;
    private final String var;
    private final int maxVls;
    private final boolean bannable;
    private final boolean enabled;
    private final ProtocolVersion[] incompatibleServer;
    private final ProtocolVersion[] incompatibleClient;
    private final Stage stage;

    public static final class Builder {
        private Type type = Type.UNKNOWN;
        private String var = "X";
        private int maxVls = 15;
        private boolean bannable = false;
        private boolean enabled = true;
        private ProtocolVersion[] incompatibleServer = {ProtocolVersion.getGameVersion()};
        private ProtocolVersion[] incompatibleClient = {ProtocolVersion.getGameVersion()};
        private Stage stage = Stage.EXPERIMENTAL;

        public Builder setType(Type type) {
            this.type = type;
            return this;
        }

        public Builder setVar(String var){
            this.var = var;
            return this;
        }

        public Builder setMaxVls(int i){
            this.maxVls = i;
            return this;
        }

        public Builder setBannable(boolean var) {
            this.bannable = var;
            return this;
        }

        public Builder setEnabled(boolean var) {
            this.enabled = var;
            return this;
        }

        public Builder setIncompatibleServer(ProtocolVersion... vers) {
            this.incompatibleServer = vers;
            return this;
        }

        public Builder setIncompatibleClient(ProtocolVersion... vers) {
            this.incompatibleClient = vers;
            return this;
        }

        public Builder setStage(Stage stage) {
            this.stage = stage;
            return this;
        }

        public CheckInformation build() {
            return new CheckInformation(type, var, maxVls, bannable, enabled, incompatibleServer, incompatibleClient, stage);
        }
    }

    public static final class Parser {
        public static CheckInformation parse(Class<?> clazz) {
            CheckData data = clazz.getAnnotation(CheckData.class);
            return new Builder()
                    .setType(data.type())
                    .setVar(data.var())
                    .setMaxVls(data.maxVls())
                    .setEnabled(data.enabled())
                    .setBannable(data.bannable())
                    .setIncompatibleServer(data.incompatibleServer())
                    .setIncompatibleClient(data.incompatibleClient())
                    .setStage(data.stage())
                    .build();
        }
    }


    public enum Stage {
        EXPERIMENTAL,
        RELEASE
    }

    public enum Type {
        AURA,
        AIM,
        SPEED,
        FLY,
        INVALID,
        JESUS,

        UNKNOWN;
    }
}

