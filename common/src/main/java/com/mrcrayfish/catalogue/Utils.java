package com.mrcrayfish.catalogue;

import net.minecraft.resources.ResourceLocation;

/**
 * Author: MrCrayfish
 */
public class Utils
{
    public static ResourceLocation resource(String name)
    {
        return ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, name);
    }
}
