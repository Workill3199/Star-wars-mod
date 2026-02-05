package com.starwars.entity.block;

import com.starwars.entity.ModBlockEntities;
import com.starwars.item.ModItems;
import com.starwars.screen.LightsaberForgeScreenHandler;
import com.starwars.util.ImplementedInventory;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class LightsaberForgeBlockEntity extends BlockEntity implements NamedScreenHandlerFactory, ImplementedInventory {
    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(4, ItemStack.EMPTY);

    public LightsaberForgeBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.LIGHTSABER_FORGE_BE, pos, state);
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return inventory;
    }

    @Override
    public Text getDisplayName() {
        return Text.translatable("block.star_wars_mod.lightsaber_forge");
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new LightsaberForgeScreenHandler(syncId, playerInventory, this);
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
        
        if (hasRecipe() && isOutputSlotEmpty()) {
            craftItem();
            markDirty(world, pos, state);
        }
    }

    private boolean hasRecipe() {
        boolean hasHilt = getStack(0).getItem() == ModItems.LIGHTSABER_HILT;
        boolean hasEmitter = getStack(1).getItem() == ModItems.LIGHTSABER_EMITTER;
        boolean hasCrystal = isCrystal(getStack(2));
        
        return hasHilt && hasEmitter && hasCrystal;
    }

    private boolean isCrystal(ItemStack stack) {
        return stack.getItem() == ModItems.KYBER_CRYSTAL || 
               stack.getItem() == ModItems.RED_KYBER_CRYSTAL || 
               stack.getItem() == ModItems.GREEN_KYBER_CRYSTAL || 
               stack.getItem() == ModItems.PURPLE_KYBER_CRYSTAL;
    }

    private boolean isOutputSlotEmpty() {
        return getStack(3).isEmpty();
    }

    private void craftItem() {
        this.removeStack(0, 1);
        this.removeStack(1, 1);
        ItemStack crystalStack = this.removeStack(2, 1);
        
        ItemStack result = new ItemStack(ModItems.LIGHTSABER);
        String color = "blue";
        if (crystalStack.getItem() == ModItems.RED_KYBER_CRYSTAL) color = "red";
        else if (crystalStack.getItem() == ModItems.GREEN_KYBER_CRYSTAL) color = "green";
        else if (crystalStack.getItem() == ModItems.PURPLE_KYBER_CRYSTAL) color = "purple";
        
        ((com.starwars.item.custom.LightsaberItem)ModItems.LIGHTSABER).setColor(result, color);
        
        this.setStack(3, result);
    }
}
