package net.projectiledamage.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.LiteralText;
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

import static net.minecraft.item.ItemStack.MODIFIER_FORMAT;

@Environment(EnvType.CLIENT)
@Mixin(ItemStack.class)
public class ItemStackMixin {
    @Shadow private Item item;

    @Inject(method = "getTooltip", at = @At(value = "RETURN", target = "Ljava/util/List;add(Ljava/lang/Object;)Z"))
    private void combineTooltip(PlayerEntity player, TooltipContext context, CallbackInfoReturnable<List<Text>> cir) {
        if (this.item instanceof IProjectileWeapon) {
            List<Text> list = cir.getReturnValue();
            mergeAttributeLines_MainHandOffHand(list);
            replaceAttributeLines_BlueWithGreen(list);
        }
    }

    private void mergeAttributeLines_MainHandOffHand(List<Text> tooltip) {
        List<TranslatableText> heldInHandLines = new ArrayList<>();
        List<TranslatableText> mainHandAttributes = new ArrayList<>();
        List<TranslatableText> offHandAttributes = new ArrayList<>();
        for (int i = 0; i < tooltip.size(); i++) {
            if (tooltip.get(i) instanceof TranslatableText translatableText) {
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
            var mainHandLine = tooltip.indexOf(heldInHandLines.get(0));
            var offHandLine = tooltip.indexOf(heldInHandLines.get(1));
            tooltip.set(mainHandLine, new TranslatableText("item.modifiers.both_hands").formatted(Formatting.GRAY));
            tooltip.remove(offHandLine);
            for (var offhandAttribute: offHandAttributes) {
                if(mainHandAttributes.contains(offhandAttribute)) {
                    tooltip.remove(tooltip.lastIndexOf(offhandAttribute));
                }
            }

            var lastIndex = tooltip.size() - 1;
            var lastLine = tooltip.get(lastIndex);
            if (lastLine.asString().isEmpty()) {
                tooltip.remove(lastIndex);
            }
        }
    }

    private void replaceAttributeLines_BlueWithGreen(List<Text> tooltip) {
        var attributeTranslationKey = "attribute.name.generic.projectile_damage";
        for (int i = 0; i < tooltip.size(); i++)  {
            var line = tooltip.get(i);
            if (line instanceof TranslatableText translatableLine) {
                var isProjectileAttributeLine = false;
                var attributeValue = 0.0;
                var args = translatableLine.getArgs();
                if (translatableLine.getKey().startsWith("attribute.modifier.plus.0")) { // `.0` suffix for addition
                    for (var arg: args) {
                        if (arg instanceof String string) {
                            try {
                                var number = Double.valueOf(string);
                                attributeValue = number;
                            } catch (Exception ignored) { }
                        }
                        if (arg instanceof TranslatableText attribute) {
                            if (attribute.getKey().startsWith(attributeTranslationKey)) {
                                isProjectileAttributeLine = true;
                            }
                        }
                    }
                }

                if (isProjectileAttributeLine && attributeValue > 0) {
                    // The construction of this line is copied from ItemStack.class
                    var greenAttributeLine =  (new LiteralText(" "))
                            .append(
                                    new TranslatableText("attribute.modifier.equals." + EntityAttributeModifier.Operation.ADDITION.getId(),
                                            new Object[]{ MODIFIER_FORMAT.format(attributeValue), new TranslatableText(attributeTranslationKey)})
                            )
                            .formatted(Formatting.DARK_GREEN);
                    tooltip.set(i, greenAttributeLine);
                }
            }
        }
    }
}
