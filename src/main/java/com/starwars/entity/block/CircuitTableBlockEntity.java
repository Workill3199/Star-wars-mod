package com.starwars.entity.block;

import com.starwars.entity.ModBlockEntities;
import com.starwars.item.ModItems;
import com.starwars.screen.CircuitTableScreenHandler;
import com.starwars.util.ImplementedInventory;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class CircuitTableBlockEntity extends BlockEntity implements NamedScreenHandlerFactory, ImplementedInventory {
    // 3 Inputs (Plate, Cable, Special), 1 Output
    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(4, ItemStack.EMPTY);

    public CircuitTableBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.CIRCUIT_TABLE_BE, pos, state);
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return inventory;
    }

    @Override
    public Text getDisplayName() {
        return Text.translatable("block.star_wars_mod.circuit_table");
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new CircuitTableScreenHandler(syncId, playerInventory, this);
    }

    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.writeNbt(nbt, registryLookup);
        Inventories.writeNbt(nbt, inventory, registryLookup);
    }

    @Override
    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.readNbt(nbt, registryLookup);
        Inventories.readNbt(nbt, inventory, registryLookup);
    }
    
    public void tick(World world, BlockPos pos, BlockState state) {
        if(world.isClient()) return;
        
        if (isOutputSlotEmpty()) {
            if (hasR2D2Recipe()) {
                craftR2D2();
                markDirty(world, pos, state);
            } else if (hasC3PORecipe()) {
                craftC3PO();
                markDirty(world, pos, state);
            }
        }
    }

    private boolean hasR2D2Recipe() {
        boolean hasPlate = getStack(0).getItem() == ModItems.PLASTEEL_PLATE;
        boolean hasCable = getStack(1).getItem() == ModItems.CABLE;
        // Maybe add a third component like Redstone for "circuit"
        boolean hasRedstone = getStack(2).getItem() == Items.REDSTONE;
        
        return hasPlate && hasCable && hasRedstone;
    }

    private boolean hasC3PORecipe() {
        boolean hasPlate = getStack(0).getItem() == ModItems.PLASTEEL_PLATE;
        boolean hasCable = getStack(1).getItem() == ModItems.CABLE;
        // Maybe Gold Ingot for C-3PO's gold plating?
        boolean hasGold = getStack(2).getItem() == Items.GOLD_INGOT;
        
        return hasPlate && hasCable && hasGold;
    }

    private boolean isOutputSlotEmpty() {
        return getStack(3).isEmpty();
    }

    private void craftR2D2() {
        this.removeStack(0, 1);
        this.removeStack(1, 1);
        this.removeStack(2, 1);
        
        this.setStack(3, new ItemStack(ModItems.R2D2_SPAWN_EGG));
    }

    private void craftC3PO() {
        this.removeStack(0, 1);
        this.removeStack(1, 1);
        this.removeStack(2, 1);
        
        this.setStack(3, new ItemStack(ModItems.C3PO_SPAWN_EGG));
    }
}
