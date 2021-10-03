package setportlanhost.me.vespertilo.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.GameMenuScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.network.MessageType;
import net.minecraft.text.Text;
import net.minecraft.world.GameMode;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import setportlanhost.me.vespertilo.SetPortLanScreen;

import static setportlanhost.me.vespertilo.SetPortLanHost.*;

@Mixin(GameMenuScreen.class)
public abstract class GameMenuScreenMixin extends Screen {


    protected GameMenuScreenMixin(Text text) {
        super(text);
    }

    @Inject(at = @At("RETURN"), method = "initWidgets")
    private void addCustomButton(CallbackInfo ci) {
        if (this.client.isInSingleplayer()) {
            ButtonWidget buttonWidget = (ButtonWidget) this.addButton(new ButtonWidget(this.width - 105, this.height / 4 + 96 + -16, 98, 20, Text.method_30163("Quick Host"), (buttonWidgetx) -> {
                MinecraftClient mc = MinecraftClient.getInstance();
                mc.openScreen((Screen) null);
                this.client.mouse.lockCursor();

                this.client.getServer().openToLan(gamemode, allowCheats, port);
                mc.inGameHud.addChatMessage(MessageType.SYSTEM, Text.method_30163("Server quick-hosted on port " + port), mc.player.getUuid());
            }));
            buttonWidget.active = this.client.isIntegratedServerRunning() && !this.client.getServer().isRemote();

            ButtonWidget hostSettings = (ButtonWidget) this.addButton(new ButtonWidget(this.width - 105, this.height / 4 + 96 + 8, 98, 20, Text.method_30163("Quick Host Settings"), (buttonWidgetx) -> {
                this.client.openScreen(new SetPortLanScreen(true));
                //this.client.mouse.lockCursor();
            }));
        }
    }
}
