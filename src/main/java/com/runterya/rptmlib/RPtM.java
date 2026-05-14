package com.runterya.rptmlib;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

public class RPtM implements ModInitializer {
    @Override
    public void onInitialize() {
        FabricLoader.getInstance().getAllMods().forEach(mod -> {
            Path packs = mod.getPath("resourcepacks");
            String id = mod.getMetadata().getId();

            if (Files.isDirectory(packs)) {
                try (Stream<Path> paths = Files.list(packs)) {
                    paths.filter(Files::isDirectory).forEach(path -> {
                        String name = path.getFileName().toString();
                        ResourceManagerHelper.registerBuiltinResourcePack(
                            Identifier.of(id, name),
                            mod,
                            Text.literal(name),
                            ResourcePackActivationType.DEFAULT_ENABLED
                        );
                    });
                } catch (Exception ignored) {}
            }
        });
    }
}
