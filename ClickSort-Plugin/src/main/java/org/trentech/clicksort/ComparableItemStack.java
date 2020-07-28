package org.trentech.clicksort;

import org.bukkit.inventory.ItemStack;

public class ComparableItemStack implements Comparable<ComparableItemStack> {

	private ItemStack itemStack;
	
	public ComparableItemStack(ItemStack itemStack) {
		this.itemStack = itemStack;
	}
	
	public ItemStack getItemStack() {
		return itemStack;
	}
	
	@Override
	public int compareTo(ComparableItemStack arg0) {
		String cat1 = Main.getPlugin().getVersionHandler().getCreativeTabName(itemStack);
		String cat2 = Main.getPlugin().getVersionHandler().getCreativeTabName(arg0.getItemStack());
		
		int ret;
		if(cat1 != null && cat2 != null) {
			ret =  cat1.compareTo(cat2);
		} else {
			ret = itemStack.getType().name().compareTo(arg0.getItemStack().getType().name());
		}
		
		if(ret == 0) {
			if(itemStack.getType().name().equals(arg0.getItemStack().getType().name())) {
				if(itemStack.getAmount() < arg0.getItemStack().getAmount()) {
					return 1;
				} else if(itemStack.getAmount() > arg0.getItemStack().getAmount()) {
					return -1;
				} else {
					return 0;
				}
			} else {
				return itemStack.getType().name().compareTo(arg0.getItemStack().getType().name());
			}
		}
		
		return ret;
	}
}
