package com.mrcrayfish.catalogue.platform;

import com.mojang.blaze3d.platform.NativeImage;
import com.mrcrayfish.catalogue.Constants;
import com.mrcrayfish.catalogue.client.FabricModData;
import com.mrcrayfish.catalogue.client.IModData;
import com.mrcrayfish.catalogue.platform.services.IPlatformHelper;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.fabricmc.loader.impl.FabricLoaderImpl;
import net.minecraft.client.gui.GuiGraphics;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

public class FabricPlatformHelper implements IPlatformHelper
{
    @Override
    public List<IModData> getAllModData()
    {
        return FabricLoader.getInstance().getAllMods().stream().map(ModContainer::getMetadata).map(FabricModData::new).collect(Collectors.toList());
    }

    @Override
    public File getModDirectory()
    {
        return FabricLoaderImpl.INSTANCE.getModsDirectory();
    }

    @Override
    public Path getConfigDirectory()
    {
        return FabricLoaderImpl.INSTANCE.getConfigDir();
    }

    @Override
    public NativeImage loadImageFromModResource(String modId, String resource)
    {
        return FabricLoader.getInstance()
            .getModContainer(modId)
            .flatMap(c -> c.findPath(resource))
            .map(path -> {
                try(InputStream is = Files.newInputStream(path)) {
                    return NativeImage.read(is);
                } catch(IOException e) {
                    Constants.LOG.error("Failed to load image resource '{}' from mod {}", resource, modId, e);
                    return null;
                }
            }).orElse(null);
    }

    @Override
    public void drawUpdateIcon(GuiGraphics graphics, int x, int y) {}

    @Override
    public boolean isModLoaded(String modId)
    {
        return FabricLoader.getInstance().isModLoaded(modId);
    }

    @Override
    public boolean isDevelopmentEnvironment()
    {
        return FabricLoader.getInstance().isDevelopmentEnvironment();
    }
}
