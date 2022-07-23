package net.projectiledamage.client;

import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.projectiledamage.api.IProjectileWeapon;

import java.util.ArrayList;
import java.util.List;

import static net.minecraft.item.ItemStack.MODIFIER_FORMAT;

public class TooltipHelper {
    public static void initialize() {
        ItemTooltipCallback.EVENT.register((itemStack, context, lines) -> {
            if (itemStack.getItem() instanceof IProjectileWeapon) {
                mergeAttributeLines_MainHandOffHand(lines);
                replaceAttributeLines_BlueWithGreen(lines);
            }
        });
    }

    private static void mergeAttributeLines_MainHandOffHand(List<Text> tooltip) {
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

    private static void replaceAttributeLines_BlueWithGreen(List<Text> tooltip) {
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
