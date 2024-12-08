package com.mrcrayfish.catalogue.client;

import com.mojang.blaze3d.platform.NativeImage;
import com.mrcrayfish.catalogue.exception.InvalidBrandingImageException;
import org.apache.commons.lang3.function.TriFunction;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;

/**
 * Author: MrCrayfish
 */
public record ImagePredicate(TriFunction<NativeImage, Integer, Integer, Boolean> predicate, BiFunction<Integer, Integer, String> errorMessage) implements BiPredicate<NativeImage, Branding>
{
    public static final ImagePredicate SQUARE = new ImagePredicate((image, maxWidth, maxHeight) -> Objects.equals(image.getWidth(), image.getHeight()), (maxWidth, maxHeight) -> "Image must be a square");
    public static final ImagePredicate EQUAL = new ImagePredicate((image, maxWidth, maxHeight) -> image.getWidth() == maxWidth && image.getHeight() == maxHeight, "Image dimensions must be exactly %spx by %spx"::formatted);
    public static final ImagePredicate LESS_THAN_OR_EQUAL = new ImagePredicate((image, maxWidth, maxHeight) -> image.getWidth() <= maxWidth && image.getHeight() <= maxHeight, "Image dimensions must be less than or equal to %spx by %spx"::formatted);

    @Override
    public boolean test(NativeImage image, Branding branding) throws InvalidBrandingImageException
    {
        if(!this.predicate.apply(image, branding.imageWidth(), branding.imageHeight()))
        {
            throw new InvalidBrandingImageException(this.errorMessage.apply(branding.imageWidth(), branding.imageHeight()));
        }
        return true;
    }
}
