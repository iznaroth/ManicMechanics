package com.iznaroth.industrizer.logistics;


import com.iznaroth.industrizer.api.IUpgradable;
import net.minecraft.item.ItemStack;

/** Network-Navigable blocks do a few things:
 *  - Permit passage through them when network manager is routing - see onItemPassesPoint()
 *  - Filter passage based on parameters
 *  - Act as legal anchors for Vacuum Highways (in addition to IInventory).
 */
public interface INetworkNavigable  {

    public boolean has_filter = false;
    public boolean filter_on = false;

    default boolean onItemPassesPoint(){
        return shouldFilter();
    }

    default boolean shouldFilter(){
        if(!has_filter){
            return false;
        }

        return filter_on;
    }

    boolean runFilterFor(ItemStack itemStack); //


}
