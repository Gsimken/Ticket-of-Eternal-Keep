package net.gsimken.mixin;

import net.gsimken.utils.TicketUtils;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayerEntity.class)
public class ServerPlayerEntityMixin {

	@Inject(method = "copyFrom", at = @At("HEAD"), cancellable = true)
	private void copyFrom(ServerPlayerEntity oldPlayer, boolean alive, CallbackInfo info) {

		if (!alive && TicketUtils.checkForTicket(oldPlayer)) {
			//Copy old inventory in new simulating keepinventory
			((PlayerEntity) (Object) this).getInventory().clone(oldPlayer.getInventory());
			((PlayerEntity) (Object) this).experienceLevel = oldPlayer.experienceLevel;
			((PlayerEntity) (Object) this).totalExperience = oldPlayer.totalExperience;
			TicketUtils.consumeTicket((ServerPlayerEntity) (Object) this);
		}
	}





}