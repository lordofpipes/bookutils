package me.samipourquoi.mixin;

import me.samipourquoi.Settings;
import net.minecraft.client.options.GameOptions;
import net.minecraft.nbt.CompoundTag;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.io.PrintWriter;
import java.util.Iterator;

@Mixin(GameOptions.class)
public class MixinGameOptions {
	@Inject(method = "load",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/client/render/entity/PlayerModelPart;values()[Lnet/minecraft/client/render/entity/PlayerModelPart;"
			),
			locals = LocalCapture.CAPTURE_FAILEXCEPTION)
	private void loadInject(CallbackInfo info, CompoundTag local1, CompoundTag local2,
							Iterator local3, String key, String value) {

		Settings.loadOptions(key, value);
	}

	@Inject(method = "write",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/client/render/entity/PlayerModelPart;values()[Lnet/minecraft/client/render/entity/PlayerModelPart;"
			),
			locals = LocalCapture.CAPTURE_FAILEXCEPTION)
	private void writeInject(CallbackInfo info, PrintWriter printWriter) {
		Settings.writeOptions(printWriter);
	}
}
