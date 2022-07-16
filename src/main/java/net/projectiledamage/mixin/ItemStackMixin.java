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
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.List;

@Environment(EnvType.CLIENT)
@Mixin(ItemStack.class)
public class ItemStackMixin {
    @Shadow private Item item;

    @Inject(method = "getTooltip", at = @At(value = "RETURN", target = "Ljava/util/List;add(Ljava/lang/Object;)Z"))
    private void combineTooltip(PlayerEntity player, TooltipContext context, CallbackInfoReturnable<List<Text>> cir) {
        if (this.item instanceof IProjectileWeapon) {
            List<Text> list = cir.getReturnValue();
            List<TranslatableText> modifierList = new ArrayList<>();
            int indexOfFirstHeld = -1;
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i) instanceof TranslatableText translatableText) {
                    if (translatableText.getKey().startsWith("item.modifiers")) {
                        modifierList.add(translatableText);
                        if (indexOfFirstHeld == -1) {
                            indexOfFirstHeld = i;
                        }
                    }
                    if (modifierList.size() > 1) {
                        list.subList(i-1, list.size()).clear();
                    }
                }
            }
            if (indexOfFirstHeld >= 0) {
                list.add(indexOfFirstHeld, new TranslatableText("item.modifiers.both_hands").formatted(Formatting.GRAY));
            }
            if (modifierList.size() > 1) {
                list.removeAll(modifierList);
            }
        }
    }
}
