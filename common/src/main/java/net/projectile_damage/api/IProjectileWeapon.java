package net.projectile_damage.api;

public interface IProjectileWeapon {
    /**
     * Use this in case your ranged weapon does not inherit from vanilla Bow or Crossbow classes
     * Make sure to input values those match with the actual behaviour.
     *
     * For example, use this for a canon.
     *
     * @param kind - a custom data set describing the default behaviour of your weapon kind
     */
    void setRangedWeaponKind(RangedWeaponKind kind);
    RangedWeaponKind getRangeWeaponKind();

    /**
     * Adjust how much damage a projectile shot by this weapon should do.
     * The characteristics of your weapon (such as randomness in damage output, critical strikes)
     * will not be altered.
     * @param value - custom damage value for scaling
     */
    default void setProjectileDamage(double value) {
        this.setProjectileDamage(value, true, true);
    }
    void setProjectileDamage(double value, boolean mainHand, boolean offHand);
    double getProjectileDamage();

    /**
     * Use this setter in case your weapon shoots faster/slower projectiles compared to the default
     * item of this kind, in order to correctly scale projectile damage.
     *
     * For example, use this if your bow shoots fast traveling arrows.
     *
     * @param value
     */
    void setCustomLaunchVelocity(Double value);
    Double getCustomLaunchVelocity();

    // Deprecated API

    @Deprecated
    default void setMaxProjectileVelocity(Double value) {
        setCustomLaunchVelocity(value);
    }

    @Deprecated
    default Double getMaxProjectileVelocity() {
        return getCustomLaunchVelocity();
    }
}
