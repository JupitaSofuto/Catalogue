package com.mrcrayfish.catalogue.client;

import net.minecraft.resources.ResourceLocation;

/**
 * Author: MrCrayfish
 */
public record ImageInfo(ResourceLocation resource, int width, int height, Runnable unregister)
{
}
