package net.projectiledamage.api;

import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.projectiledamage.ProjectileDamage;

public class StatusEffects_ProjectileDamage {
    public static final String accuracyId = "accuracy";
    public static final String accuracyUsualUDID = "e4f2bf5c-329f-11ed-a261-0242ac120002";
    public static final StatusEffect ACCURACY = (new AccuracyStatusEffect(StatusEffectCategory.BENEFICIAL, 0xAAFFDD))
            .addAttributeModifier(
                    EntityAttributes_ProjectileDamage.GENERIC_PROJECTILE_DAMAGE,
                    accuracyUsualUDID,
                    ProjectileDamage.configManager.currentConfig.status_effect_accuracy_multiplier_per_stack, // 0.2 by default
                    EntityAttributeModifier.Operation.MULTIPLY_TOTAL);
}
