package com.example.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.particle.ParticleEffect;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ParticleManager.class)
public class ExampleMixin {
    
    private static long lastHitTime = 0;
    private static final long BLIND_DURATION = 13000; // 13 секунд

    @Inject(method = "addParticle(Lnet/minecraft/particle/ParticleEffect;DDDDDD)V", at = @At("HEAD"), cancellable = true)
    private void onAddParticle(ParticleEffect parameters, double x, double y, double z, double velocityX, double velocityY, double velocityZ, CallbackInfo ci) {
        MinecraftClient client = MinecraftClient.getInstance();
        
        if (client.player != null && client.player.hurtTime > 0) {
            lastHitTime = System.currentTimeMillis();
        }

        if (System.currentTimeMillis() - lastHitTime < BLIND_DURATION) {
            ci.cancel(); // Блокируем частицы на 13 секунд
        }
    }
}
