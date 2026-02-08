package com.starwars.worldgen.biome;

import com.starwars.StarWarsMod;
import net.minecraft.util.Identifier;
import terrablender.api.Regions;
import terrablender.api.TerraBlenderApi;

public class StarWarsTerraBlender implements TerraBlenderApi {
    @Override
    public void onTerraBlenderInitialized() {
        // Register the region with a weight (higher weight = more frequent)
        Regions.register(new TatooineRegion(Identifier.of(StarWarsMod.MOD_ID, "tatooine_region"), 5));
    }
}
