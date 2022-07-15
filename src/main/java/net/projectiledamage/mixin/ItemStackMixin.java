package net.projectiledamage.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.projectiledamage.api.IProjectileWeapon;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.List;

@Environment(EnvType.CLIENT)
@Mixin(ItemStack.class)
public class ItemStackMixin {
    @Mutable
    @Final
    @Shadow
    private final Item item;

    public ItemStackMixin(Item item) {
        this.item = item;
    }

    @Inject(method = "getTooltip", at = @At(value = "RETURN", target = "Ljava/util/List;add(Ljava/lang/Object;)Z"))
    private void combineTooltip(PlayerEntity player, TooltipContext context, CallbackInfoReturnable<List<Text>> cir) {
        if (this.item instanceof IProjectileWeapon) {
            List<Text> list = cir.getReturnValue();
            List<TranslatableText> modifierList = new ArrayList<>();
            int index = -1;
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i) instanceof TranslatableText translatableText) {
                    if (translatableText.getKey().startsWith("item.modifiers")) {
                        modifierList.add(translatableText);
                        if (index == -1) {
                            index = i;
                        }
                    }
                    if (modifierList.size() > 1) {
                        list.subList(i-1, list.size()).clear();
                    }
                }
            }
            if (modifierList.size() > 1) {
                list.removeAll(modifierList);
                list.add(index, new TranslatableText("item.modifiers.both_hands").formatted(Formatting.GRAY));
            }
        }
    }
}
