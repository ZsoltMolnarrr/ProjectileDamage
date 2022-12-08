package net.projectile_damage.api;

import net.minecraft.entity.attribute.ClampedEntityAttribute;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.projectile_damage.ProjectileDamageMod;

public class EntityAttributes_ProjectileDamage {
    public static final Identifier attributeId = new Identifier(ProjectileDamageMod.ID, "generic");
    public static final String translationKey = "attribute.name.projectile_damage.generic";
    public static final EntityAttribute GENERIC_PROJECTILE_DAMAGE = register(attributeId, new ClampedEntityAttribute(translationKey, 0, 0.0, 1024.0));
    private static EntityAttribute register(Identifier id, EntityAttribute attribute) {
        return (EntityAttribute) Registry.register(Registry.ATTRIBUTE, id, attribute);
    }
}
