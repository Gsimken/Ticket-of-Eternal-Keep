package net.gsimken.mixin;

import net.gsimken.utils.TicketUtils;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {

	@Inject(method = "drop", at = @At("HEAD"), cancellable = true)
	private void onDrop(ServerWorld world, DamageSource damageSource, CallbackInfo ci) {
		if(((Object) this) instanceof ServerPlayerEntity){
			ServerPlayerEntity player = (ServerPlayerEntity) (Object) this;
			if (TicketUtils.checkForTicket(player)) {
				ci.cancel();
			}
		}
	}





}