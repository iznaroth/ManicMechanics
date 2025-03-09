package com.iznaroth.m4.datagen;

import com.iznaroth.m4.common.M4;

import com.iznaroth.m4.common.registration.M4Blocks;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class M4ItemModels extends ItemModelProvider {
    public M4ItemModels(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, M4.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        withExistingParent(M4Blocks.OBLITERATION_PLINTH.getId().getPath(), modLoc("block/obliteration_plinth"));
        //withExistingParent(M4Blocks.MANUFACTORUM.getId().getPath(), modLoc("block/manufactorum_main"));
        withExistingParent(M4Blocks.HYPERFIELD_EXTRACT_POLARIZATION_CHAMBER.getId().getPath(), modLoc("block/hyperfield_extract_polarization_chamber_off"));
        withExistingParent(M4Blocks.CHARGING_STATION.getId().getPath(), modLoc("block/charging_station_off"));
    }
}
