package setportlanhost.me.vespertilo;

import net.fabricmc.api.ModInitializer;
import net.minecraft.world.GameMode;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SetPortLanHost implements ModInitializer {
    public static Logger LOGGER = LogManager.getLogger();
    public static final String MOD_ID = "setportlanhost";
    public static final String MOD_NAME = "SetPortLanHost";
    public static int port = 25565;
    public static GameMode gamemode = GameMode.SURVIVAL;
    public static boolean allowCheats = true;


    @Override
    public void onInitialize() {
        log(Level.INFO, "Initializing");
    }

    public static void log(Level level, String message) {
        LOGGER.log(level, "[LanHosting] " + message);
    }
}
