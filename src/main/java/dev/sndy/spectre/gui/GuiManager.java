package dev.sndy.spectre.gui;

import dev.sndy.spectre.Reference;
import dev.sndy.spectre.item.ItemType;
import dev.sndy.spectre.item.Items;
import dev.sndy.spectre.player.NetworkPlayer;
import dev.sndy.spectre.player.NetworkPlayerHandler;
import dev.sndy.spectre.player.NetworkPlayerProfile;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;

public class GuiManager implements Listener {

    private final static ItemsGui itemsGui = new ItemsGui();
    private final static ProfilesGui profilesGui = new ProfilesGui();
    private final static DeleteGui deleteGui = new DeleteGui();

    /**
     * Handles player clicking inventory GUI items.
     *
     * @param event InventoryClickEvent
     */
    @EventHandler
    public void inventoryClickEvent(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        switch (event.getView().getTitle()) {
            case "Items":
                //Items GUI
                if (Objects.requireNonNull(event.getClickedInventory()).getItem(event.getSlot()) != null && Objects.requireNonNull(event.getClickedInventory().getItem(event.getSlot())).getType() != Material.AIR) {
                    switch (Objects.requireNonNull(Objects.requireNonNull(event.getClickedInventory().getItem(event.getSlot())).getItemMeta()).getDisplayName()) {
                        case "§aItems":
                            event.setCancelled(true);
                            break;
                        case "§aPrevious Page":
                            event.setCancelled(true);
                            player.openInventory(itemsGui.getInventory(player, itemsGui.currentPage - 1));
                            break;
                        case "§aNext Page":
                            event.setCancelled(true);
                            player.openInventory(itemsGui.getInventory(player, itemsGui.currentPage + 1));
                            break;
                        default:
                            if (event.getRawSlot() == event.getSlot()) {
                                event.setCancelled(true);
                                ItemStack clicked = event.getClickedInventory().getItem(event.getSlot()).clone();
                                if(!Items.getType(clicked).equals(ItemType.EQUIPMENT)){
                                    clicked.setAmount(clicked.getMaxStackSize());
                                }
                                player.getOpenInventory().setCursor(clicked);
                            }
                    }
                }
                break;
            case "Profiles":
                //Profiles GUI
                event.setCancelled(true);
                if (Objects.requireNonNull(event.getClickedInventory()).getItem(event.getSlot()) != null && Objects.requireNonNull(event.getClickedInventory().getItem(event.getSlot())).getType() != Material.AIR) {
                    NetworkPlayer networkPlayer = NetworkPlayerHandler.getPlayer(player.getUniqueId());
                    switch (Objects.requireNonNull(Objects.requireNonNull(event.getClickedInventory().getItem(event.getSlot())).getItemMeta()).getDisplayName()) {
                        case "§cProfile 1":
                            if (event.getClick().isRightClick()) {
                                player.openInventory(deleteGui.getInventory(player, 0));
                            } else {
                                networkPlayer.getProfile().update(player);
                                networkPlayer.meta.currentProfile = 0;
                                networkPlayer.syncWithProfile();
                                player.openInventory(profilesGui.getInventory(player));
                                player.sendMessage(Reference.SWITCH_PROFILE_MESSAGE.replace("[profile]", "1"));
                            }
                            break;
                        case "§6Profile 2":
                            if (event.getClick().isRightClick()) {
                                player.openInventory(deleteGui.getInventory(player, 1));
                            } else {
                                networkPlayer.getProfile().update(player);
                                networkPlayer.meta.currentProfile = 1;
                                networkPlayer.syncWithProfile();
                                player.openInventory(profilesGui.getInventory(player));
                                player.sendMessage(Reference.SWITCH_PROFILE_MESSAGE.replace("[profile]", "2"));
                            }
                            break;
                        case "§eProfile 3":
                            if (event.getClick().isRightClick()) {
                                player.openInventory(deleteGui.getInventory(player, 2));
                            } else {
                                networkPlayer.getProfile().update(player);
                                networkPlayer.meta.currentProfile = 2;
                                networkPlayer.syncWithProfile();
                                player.openInventory(profilesGui.getInventory(player));
                                player.sendMessage(Reference.SWITCH_PROFILE_MESSAGE.replace("[profile]", "3"));
                            }
                            break;
                        case "§eClick to create!":
                            if(event.getSlot() == 11){
                                networkPlayer.meta.profiles[0] = new NetworkPlayerProfile(player, true);
                                networkPlayer.getProfile().update(player);
                                networkPlayer.meta.currentProfile = 0;
                                networkPlayer.syncWithProfile();
                                player.sendMessage(Reference.CREATE_PROFILE_MESSAGE.replace("[profile]", "1"));
                                player.closeInventory();
                            }
                            else if(event.getSlot() == 13){
                                networkPlayer.meta.profiles[1] = new NetworkPlayerProfile(player, true);
                                networkPlayer.getProfile().update(player);
                                networkPlayer.meta.currentProfile = 1;
                                networkPlayer.syncWithProfile();
                                player.sendMessage(Reference.CREATE_PROFILE_MESSAGE.replace("[profile]", "2"));
                                player.closeInventory();
                            }
                            else if(event.getSlot() == 15){
                                networkPlayer.meta.profiles[2] = new NetworkPlayerProfile(player, true);
                                networkPlayer.getProfile().update(player);
                                networkPlayer.meta.currentProfile = 2;
                                networkPlayer.syncWithProfile();
                                player.sendMessage(Reference.CREATE_PROFILE_MESSAGE.replace("[profile]", "3"));
                                player.closeInventory();
                            }

                    }
                }
            case "Delete Profile":
                //Delete Profile GUI
                if (Objects.requireNonNull(event.getClickedInventory()).getItem(event.getSlot()) != null && Objects.requireNonNull(event.getClickedInventory().getItem(event.getSlot())).getType() != Material.AIR) {
                    NetworkPlayer networkPlayer = NetworkPlayerHandler.getPlayer(player.getUniqueId());
                    switch (Objects.requireNonNull(Objects.requireNonNull(event.getClickedInventory().getItem(event.getSlot())).getItemMeta()).getDisplayName()) {
                        case "§c§lAre you SURE you want to delete this profile?":
                            networkPlayer.meta.profiles[networkPlayer.data.deletedProfile] = new NetworkPlayerProfile(player, false);
                            player.sendMessage(Reference.DELETE_PROFILE_MESSAGE.replace("[profile]", String.valueOf(networkPlayer.data.deletedProfile + 1)));
                            player.openInventory(profilesGui.getInventory(player));
                    }
                }
        }
    }

    public static void openGui(GuiEnum gui, Player player) {
        switch (gui) {
            case ITEM_GUI:
                player.openInventory(itemsGui.getInventory(player));
                break;
            case PROFILES_GUI:
                player.openInventory(profilesGui.getInventory(player));
                break;
            default:
        }
    }

}
