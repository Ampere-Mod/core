package com.gtlugo.ampere.common;

import com.gtlugo.ampere.Ampere;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class Tags {
  public static class Blocks {
    public static final TagKey<Block> WIRE_CONNECTABLE = tag("wire_connectable");

    private static TagKey<Block> tag(String name) {
      return BlockTags.create(ResourceLocation.fromNamespaceAndPath(Ampere.MOD_ID, name));
    }
  }
}
