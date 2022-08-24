package tomas.chests;

import org.bukkit.World;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class ChestCreatorPlayer {

    private int x;
    private int y;
    private int z;
    private World world;
    private boolean selected;
    private Chest focusedChest;
    private List<ItemStack> items;

    public void setFocusedChest(Chest focusedChest) {
        this.focusedChest = focusedChest;
    }


    public Chest getFocusedChest() {
        return focusedChest;
    }

    public List<ItemStack> getItems() {
        return items;
    }

    public void setItems(List<ItemStack> items) {
        this.items = items;
    }

    public ChestCreatorPlayer() {
        selected = false;
        items = new ArrayList<>();
    }

    public boolean isSelected() {
        return selected;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }

    public World getWorld() {
        return world;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setZ(int z) {
        this.z = z;
    }
}
