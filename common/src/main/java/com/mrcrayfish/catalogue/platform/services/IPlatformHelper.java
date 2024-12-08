package com.mrcrayfish.catalogue.platform.services;

import com.mojang.blaze3d.platform.NativeImage;
import com.mrcrayfish.catalogue.client.IModData;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public interface IPlatformHelper
{
    List<IModData> getAllModData();

    File getModDirectory();

    Path getConfigDirectory();

    NativeImage loadImageFromModResource(String modId, String resource) throws IOException;

    void drawUpdateIcon(GuiGraphics graphics, int x, int y);

    boolean isModLoaded(String modId);

    boolean isDevelopmentEnvironment();
}