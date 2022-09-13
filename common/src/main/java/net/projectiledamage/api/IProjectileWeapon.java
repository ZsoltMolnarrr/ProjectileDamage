package net.projectiledamage.api;

public interface IProjectileWeapon {
    void setProjectileDamage(double value, boolean mainHand, boolean offHand);
    default void setProjectileDamage(double value) {
        this.setProjectileDamage(value, true, true);
    }
    double getProjectileDamage();
    void setMaxProjectileVelocity(Double value);
    Double getMaxProjectileVelocity();
}
