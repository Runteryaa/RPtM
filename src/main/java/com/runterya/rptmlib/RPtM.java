package com.runterya.rptmlib;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RPtM implements ModInitializer {
    public static final String MOD_ID = "rptmlib";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        LOGGER.info("Initializing RPtMLib - Scanning for bundled resource packs...");

        FabricLoader.getInstance().getAllMods().forEach(container -> {
            java.nio.file.Path resourcePacksPath = container.getPath("resourcepacks");
            String containerId = container.getMetadata().getId();

            if (java.nio.file.Files.exists(resourcePacksPath) && java.nio.file.Files.isDirectory(resourcePacksPath)) {
                try (java.util.stream.Stream<java.nio.file.Path> paths = java.nio.file.Files.list(resourcePacksPath)) {
                    paths.forEach(path -> {
                        String name = path.getFileName().toString();

                        if (java.nio.file.Files.isDirectory(path) || name.endsWith(".zip")) {
                            boolean success = ResourceManagerHelper.registerBuiltinResourcePack(
                                Identifier.of(containerId, name),
                                container,
                                Text.literal(name.replace(".zip", "")),
                                ResourcePackActivationType.DEFAULT_ENABLED
                            );

                            if (success) {
                                LOGGER.info("RPtMLib registered built-in pack: '{}' from mod '{}'", name, containerId);
                            } else {
                                LOGGER.warn("RPtMLib failed to register built-in pack: '{}' from mod '{}'", name, containerId);
                            }
                        }
                    });
                } catch (java.io.IOException e) {
                    LOGGER.error("Failed to list resource packs directory for mod: {}", containerId, e);
                }
            }
        });
    }
}
