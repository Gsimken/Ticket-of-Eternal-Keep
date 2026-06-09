package net.gsimken.mixin;

import net.gsimken.utils.TicketUtils;
import net.minecraft.server.level.ServerPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayer.class)
public class ServerPlayerEntityMixin {

	@Inject(method = "restoreFrom", at = @At("HEAD"), cancellable = true)
	private void copyFrom(ServerPlayer oldPlayer, boolean alive, CallbackInfo info) {

		if (!alive && TicketUtils.checkForTicket(oldPlayer)) {
			ServerPlayer player = (ServerPlayer) (Object) this;
			//Copy old inventory in new simulating keepinventory
			player.getInventory().replaceWith(oldPlayer.getInventory());
			player.experienceLevel = oldPlayer.experienceLevel;
			player.totalExperience = oldPlayer.totalExperience;
			TicketUtils.consumeTicket(player);
			TicketUtils.applyVanishCurse(player, oldPlayer.isCreative());
		}
	}





}
