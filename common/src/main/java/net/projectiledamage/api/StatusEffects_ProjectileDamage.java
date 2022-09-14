package net.projectiledamage.api;

import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.util.Identifier;
import net.projectiledamage.ProjectileDamage;

public class StatusEffects_ProjectileDamage {
    public static final String impactName = "impact";
    public static final Identifier impactId = new Identifier(ProjectileDamage.MODID, impactName);
    public static final String impactUsualUDID = "e4f2bf5c-329f-11ed-a261-0242ac120002";
    public static final StatusEffect IMPACT = (new ImpactStatusEffect(StatusEffectCategory.BENEFICIAL, 0xAAFFDD))
            .addAttributeModifier(
                    EntityAttributes_ProjectileDamage.GENERIC_PROJECTILE_DAMAGE,
                    impactUsualUDID,
                    ProjectileDamage.configManager.currentConfig.status_effect_impact_multiplier_per_stack, // 0.2 by default
                    EntityAttributeModifier.Operation.MULTIPLY_TOTAL);
}
