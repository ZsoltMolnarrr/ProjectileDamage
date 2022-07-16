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
            List<TranslatableText> heldInHandLines = new ArrayList<>();
            List<TranslatableText> mainHandAttributes = new ArrayList<>();
            List<TranslatableText> offHandAttributes = new ArrayList<>();
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i) instanceof TranslatableText translatableText) {
                    if (translatableText.getKey().startsWith("item.modifiers")) {
                        heldInHandLines.add(translatableText);
                    }
                    if (translatableText.getKey().startsWith("attribute.modifier")) {
                        if (heldInHandLines.size() == 1) {
                            mainHandAttributes.add(translatableText);
                        }
                        if (heldInHandLines.size() == 2) {
                            offHandAttributes.add(translatableText);
                        }
                    }
                }
            }
            if(heldInHandLines.size() == 2) {
                var mainHandLine = list.indexOf(heldInHandLines.get(0));
                var offHandLine = list.indexOf(heldInHandLines.get(1));
                list.set(mainHandLine, new TranslatableText("item.modifiers.both_hands").formatted(Formatting.GRAY));
                list.remove(offHandLine);
                for (var offhandAttribute: offHandAttributes) {
                    if(mainHandAttributes.contains(offhandAttribute)) {
                        list.remove(list.lastIndexOf(offhandAttribute));
                    }
                }

                var lastIndex = list.size() - 1;
                var lastLine = list.get(lastIndex);
                if (lastLine.asString().isEmpty()) {
                    list.remove(lastIndex);
                }
            }
        }
    }
}
