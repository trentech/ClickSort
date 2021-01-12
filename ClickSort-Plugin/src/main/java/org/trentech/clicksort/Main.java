package org.trentech.clicksort;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Collections;

public class Main extends JavaPlugin implements Listener {

    private static Main plugin;
    private NMS nmsHandler;

    public static Main getPlugin() {
        return plugin;
    }

    public NMS getVersionHandler() {
        return nmsHandler;
    }

    @Override
    public void onEnable() {
        plugin = this;

        String version = getServer().getClass().getPackage().getName();

        version = version.substring(version.lastIndexOf('.') + 1);

        try {
            final Class<?> clazz = Class.forName("org.trentech.clicksort.nms." + version + ".NMSHandler");

            if (NMS.class.isAssignableFrom(clazz)) {
                this.nmsHandler = (NMS) clazz.getConstructor().newInstance();
            }
        } catch (final Exception e) {
            e.printStackTrace();
            this.getLogger().severe("Could not find support for this CraftBukkit version.");
            this.getLogger().info("Current Version " + version);
            this.setEnabled(false);
            return;
        }

        getServer().getPluginManager().registerEvents(this,this);

        this.getLogger().info("Loading support for " + version);
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    public void onInventoryClicked(final InventoryClickEvent event) {
        if(!(event.getWhoClicked() instanceof Player)) {
            return;
        }

        if(event.getClick() != ClickType.MIDDLE) {
            return;
        }

        Bukkit.getScheduler().runTask(this, new Runnable() {
            @Override
            public void run() {
                int rawSlot = event.getRawSlot();
                int slot = event.getView().convertSlot(rawSlot);

                Inventory inv;
                if (slot == rawSlot) {
                    inv = event.getView().getTopInventory();

                    if (slot >= inv.getSize()) {
                        inv = event.getView().getBottomInventory();
                    }
                } else {
                    inv = event.getView().getBottomInventory();
                }

                Inventory newInv = Main.getPlugin().getServer().createInventory(event.getWhoClicked(), inv.getStorageContents().length, "Temp");
//                Inventory work;
//
//                int min = 0;
//                int max = inv.getSize() - 1;
//
//                if(inv instanceof PlayerInventory) {
//                    if (slot < 9) {
//                    	max = 8;
//
//                    	work = Main.getPlugin().getServer().createInventory(event.getWhoClicked(), 9, "Temp");
//
//                    	int x = 0;
//                    	for(ItemStack item : inv.getStorageContents()) {
//                    		if(x < 9 && item != null) {
//                            	work.addItem(item);
//                    		} else if(item != null) {
//                    			newInv.setItem(x, item);
//                    		}
//
//                    		x++;
//                    	}
//                	} else {
//                		min = 9;
//                		max = 26;
//
//                    	work = Main.getPlugin().getServer().createInventory(event.getWhoClicked(), 27, "Temp");
//
//                    	int x = 0;
//                    	for(ItemStack item : inv.getStorageContents()) {
//                    		if(x >= 9 && item != null) {
//                            	work.addItem(item);
//                    		} else if(item != null) {
//                    			newInv.setItem(x, item);
//                    		}
//
//                    		x++;
//                    	}
//                	}
//                } else {
//                	work = Main.getPlugin().getServer().createInventory(event.getWhoClicked(), inv.getStorageContents().length, "Temp");
//                	work.setStorageContents(inv.getStorageContents());
//
//                	for(ItemStack item : inv.getStorageContents()) {
//                		if(item != null) {
//                        	work.addItem(item);
//                		}
//                	}
//                }

                ArrayList<ComparableItemStack> list = new ArrayList<ComparableItemStack>();

                for(ItemStack item : inv.getStorageContents()) {
                    if(item != null) {
                        list.add(new ComparableItemStack(item));
                    }
                }

                Collections.sort(list);

//                int index = 0;
//            	for(int x = min; x <= max; x++) {
//            		if(index < list.size()) {
//            			newInv.setItem(x, list.get(index).getItemStack());
//            		}
//
//            		index++;
//            	}

                for(int x = 0; x < newInv.getSize(); x++) {
                    if(x < list.size()) {
                        newInv.addItem(list.get(x).getItemStack());
                    }
                }

                inv.setStorageContents(newInv.getContents());
            }
        });
    }
}
