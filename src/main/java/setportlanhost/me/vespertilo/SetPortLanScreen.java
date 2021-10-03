package setportlanhost.me.vespertilo;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.GameMenuScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.network.MessageType;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.world.GameMode;

import static setportlanhost.me.vespertilo.SetPortLanHost.gamemode;
import static setportlanhost.me.vespertilo.SetPortLanHost.port;

public class SetPortLanScreen extends Screen {
    private final boolean showMenu;

    private TextFieldWidget levelNameField;
    private ButtonWidget gameModeSwitchButton;
    private Mode currentMode = Mode.SURVIVAL;
    private boolean hardcore = false;

    public SetPortLanScreen(boolean bl) {
        super(Text.method_30163("Lan Settings Screen"));
        this.showMenu = bl;
    }

    protected void init() {
        if (this.showMenu) {
            this.initWidgets();
        }

    }

    private void initWidgets() {
        ButtonWidget buttonWidget = this.addButton(new ButtonWidget(this.width / 2-49, this.height / 4 + 96 + -16, 98, 20, Text.method_30163("Done"), (buttonWidgetx) -> {
            this.client.openScreen(new GameMenuScreen(true));
            MinecraftClient mc = MinecraftClient.getInstance();
            mc.inGameHud.addChatMessage(MessageType.SYSTEM, Text.method_30163("Port binded to " + port), mc.player.getUuid());
        }));
        this.levelNameField = new TextFieldWidget(this.textRenderer, this.width / 2 - 100, this.height/4 + 40-8 ,200, 20, Text.method_30163("LAN Port"));
        this.levelNameField.setText(String.valueOf(port));
        this.levelNameField.setChangedListener((string) -> {
            boolean isInt = false;
            try {
                if (!string.isEmpty() && !string.equals("0")) {
                    port = Integer.parseInt(string);
                    if (port >= 10000 && port <= 99999) {
                        isInt = true;
                    }
                }
            } catch (NumberFormatException ignored) {
            }
            buttonWidget.active = isInt;
        });
        this.children.add(this.levelNameField);

        this.gameModeSwitchButton = this.addButton(new ButtonWidget(this.width / 2 - 75, this.height / 4 + 60-4, 150, 20, new TranslatableText("selectWorld.gameMode"), (buttonWidget2) -> {
            switch (this.currentMode) {
                case SURVIVAL:
                    this.tweakDefaultsTo(Mode.HARDCORE);
                    break;
                case HARDCORE:
                    this.tweakDefaultsTo(Mode.CREATIVE);
                    break;
                case CREATIVE:
                    this.tweakDefaultsTo(Mode.SURVIVAL);
                    break;
            }
            gamemode = currentMode.defaultGameMode;
        }) {
            public Text getMessage() {
                return super.getMessage().shallowCopy().append(": ").append((Text) (new TranslatableText("selectWorld.gameMode." + currentMode.translationSuffix)));
            }
        });

    }

    public void tick() {
        super.tick();
        this.levelNameField.tick();
    }

    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        if (this.showMenu) {
            this.renderBackground(matrices);
            this.drawCenteredText(matrices, this.textRenderer, this.title, this.width / 2, 40, 16777215);
            this.drawStringWithShadow(matrices, this.textRenderer, "LAN Port", this.width / 2 - 100, this.height/4 + 32, -6250336);
            this.levelNameField.render(matrices, mouseX, mouseY, delta);
        } else {
            this.drawCenteredText(matrices, this.textRenderer, this.title, this.width / 2, 10, 16777215);
        }

        super.render(matrices, mouseX, mouseY, delta);
    }

    private void tweakDefaultsTo(Mode mode) {

        if (mode == Mode.HARDCORE) {
            this.hardcore = true;
        } else {
            this.hardcore = false;
        }

        this.currentMode = mode;
    }

    @Environment(EnvType.CLIENT)
    enum Mode {
        SURVIVAL("survival", GameMode.SURVIVAL),
        HARDCORE("hardcore", GameMode.SURVIVAL),
        CREATIVE("creative", GameMode.CREATIVE),
        DEBUG("spectator", GameMode.SPECTATOR);

        private final String translationSuffix;
        private final GameMode defaultGameMode;

        private Mode(String string2, GameMode gameMode) {
            this.translationSuffix = string2;
            this.defaultGameMode = gameMode;
        }
    }


}
