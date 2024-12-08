package com.mrcrayfish.catalogue.platform;

import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mrcrayfish.catalogue.client.IModData;
import com.mrcrayfish.catalogue.client.NeoForgeModData;
import com.mrcrayfish.catalogue.exception.ModResourceNotFoundException;
import com.mrcrayfish.catalogue.platform.services.IPlatformHelper;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.RenderType;
import net.neoforged.fml.ModList;
import net.neoforged.fml.loading.FMLLoader;
import net.neoforged.fml.loading.FMLPaths;
import net.neoforged.neoforgespi.language.IModFileInfo;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Author: MrCrayfish
 */
public class NeoForgePlatformHelper implements IPlatformHelper
{
    @Override
    public List<IModData> getAllModData()
    {
        return ModList.get().getMods().stream().map(NeoForgeModData::new).collect(Collectors.toList());
    }

    @Override
    public File getModDirectory()
    {
        return FMLPaths.MODSDIR.get().toFile();
    }

    @Override
    public Path getConfigDirectory()
    {
        return FMLPaths.CONFIGDIR.get();
    }

    @Override
    public NativeImage loadImageFromModResource(String modId, String resource) throws IOException
    {
        IModFileInfo info = ModList.get().getModFileById(modId);
        Path path = info.getFile().findResource(resource);
        if(Files.exists(path))
        {
            try(InputStream stream = Files.newInputStream(path))
            {
                return NativeImage.read(stream);
            }
        }
        else
        {
            throw new ModResourceNotFoundException();
        }
    }

    @Override
    public void drawUpdateIcon(GuiGraphics graphics, int x, int y)
    {
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        graphics.blit(RenderType::guiTextured, NeoForgeModData.VERSION_CHECK_ICONS, x, y, 24, 0, 8, 8, 64, 16);
    }

    @Override
    public boolean isModLoaded(String modId)
    {
        return ModList.get().isLoaded(modId);
    }

    @Override
    public boolean isDevelopmentEnvironment()
    {
        return !FMLLoader.isProduction();
    }
}
