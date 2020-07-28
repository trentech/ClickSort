package org.trentech.clicksort.nms.v1_16_R1;

import org.bukkit.craftbukkit.v1_16_R1.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

import net.minecraft.server.v1_16_R1.CreativeModeTab;
import net.minecraft.server.v1_16_R1.Item;
import org.trentech.clicksort.NMS;

public class NMSHandler implements NMS {

    @Override
	public String getCreativeTabName(ItemStack itemStack) {
		Item item = CraftItemStack.asNMSCopy(itemStack).getItem();

		CreativeModeTab creativeModeTab = item.q();

		if (creativeModeTab == CreativeModeTab.b) {
			return "building blocks";
		}else if (creativeModeTab == CreativeModeTab.c) {
			return "decorations";
		}else if (creativeModeTab == CreativeModeTab.d) {
			return "redstone";
		}else if (creativeModeTab == CreativeModeTab.e) {
			return "transportation";
		}else if (creativeModeTab == CreativeModeTab.f) {
			return "miscellaneous";
		}else if (creativeModeTab == CreativeModeTab.g) {
			return "search";
		}else if (creativeModeTab == CreativeModeTab.h) {
			return "food";
		}else if (creativeModeTab == CreativeModeTab.i) {
			return "tools";
		}else if (creativeModeTab == CreativeModeTab.j) {
			return "combat";
		}else if (creativeModeTab == CreativeModeTab.k) {
			return "brewing";
		}else if (creativeModeTab == CreativeModeTab.l) {
			return "miscellaneous";
		}else if (creativeModeTab == CreativeModeTab.m) {
			return "hotbar";
		}else if (creativeModeTab == CreativeModeTab.n) {
			return "inventory";
		} else {
			return null;
		}
	}

}
