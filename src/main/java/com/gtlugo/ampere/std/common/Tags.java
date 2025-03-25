package com.gtlugo.ampere.std.common;

import com.gtlugo.ampere.std.AmpereStd;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class Tags {
  public static class Blocks {
    public static final TagKey<Block> WIRE_CONNECTABLE = tag("wire_connectable");

    private static TagKey<Block> tag(String name) {
      return BlockTags.create(ResourceLocation.fromNamespaceAndPath(AmpereStd.MOD_ID, name));
    }
  }
}
