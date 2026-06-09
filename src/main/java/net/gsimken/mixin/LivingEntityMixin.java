package net.gsimken.mixin;

import net.gsimken.utils.TicketUtils;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {

	@Inject(method = "dropAllDeathLoot", at = @At("HEAD"), cancellable = true)
	private void onDrop(ServerLevel world, DamageSource damageSource, CallbackInfo ci) {
		if(((Object) this) instanceof ServerPlayer){
			ServerPlayer player = (ServerPlayer) (Object) this;
			if (TicketUtils.checkForTicket(player)) {
				ci.cancel();
			}
		}
	}





}
