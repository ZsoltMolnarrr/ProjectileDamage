package net.projectiledamage.api;

import net.minecraft.entity.attribute.ClampedEntityAttribute;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.util.registry.Registry;

public class AdditionalEntityAttributes {
    public static String projectileDamageId = "generic.projectile_damage";
    public static final EntityAttribute GENERIC_PROJECTILE_DAMAGE = register(projectileDamageId, new ClampedEntityAttribute("attribute.name.generic.projectile_damage", 0, 0.0, 1024.0));
    private static EntityAttribute register(String id, EntityAttribute attribute) {
        return (EntityAttribute) Registry.register(Registry.ATTRIBUTE, id, attribute);
    }
}
