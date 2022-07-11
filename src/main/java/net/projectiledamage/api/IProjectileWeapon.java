package net.projectiledamage.api;

public interface IProjectileWeapon {
    public void setProjectileDamage(double value, boolean mainHand, boolean offHand);

    default void setProjectileDamage(double value) {
        this.setProjectileDamage(value, true, true);
    }
}
