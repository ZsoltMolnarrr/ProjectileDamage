![Projectile Damage Attribute](.github/projectile_damage_title.png)

<div align="center">

<a href="">![Java 17](https://img.shields.io/badge/Java%2017-ee9258?logo=coffeescript&logoColor=ffffff&labelColor=606060&style=flat-square)</a>
<a href="">![Environment: Client & Server](https://img.shields.io/badge/environment-Client%20&%20Server-1976d2?style=flat-square)</a>
<a href="">[![Discord](https://img.shields.io/discord/973561601519149057.svg?label=&logo=discord&logoColor=ffffff&color=7389D8&labelColor=6A7EC2&style=flat-square)](https://discord.gg/KN9b3pjFTM)</a>

</div>

# 🏹️ Features

Adds new EntityAttribute to the game, with the following id: `projectile_damage:generic`. This allows customization of damage done by individual Bow and Crossbow items in the game.

Adds new status effect named `Impact`, that increases the projectile damage of the entity.

You can use the API provided by this mod, to set custom damage value to your custom ranged weapons.  

# 🔧 Configuration

**Server side** configuration can be found in the `config` directory, after running the game with the mod installed.

# 🔨 Using it as a modder

## Installation

Add this mod as dependency into your build.gradle file.

Repository
```groovy
repositories {
    maven {
        name = 'Modrinth'
        url = 'https://api.modrinth.com/maven'
        content {
            includeGroup 'maven.modrinth'
        }
    }
}
```

### Fabric workspace
```groovy
dependencies {
    modImplementation "maven.modrinth:projectile-damage-attribute:VERSION-fabric"
}
```
In `fabric.mod.json` add a dependency to the mod:
```json
  "depends": {
    "projectile_damage": ">=VERSION"
  },
```

(Substitute `VERSION` with the name of the latest release available on [Modrinth](https://modrinth.com/mod/projectile-damage-attribute/versions), for example: `3.0.0`)

### Forge workspace
```groovy
dependencies {
    implementation "maven.modrinth:projectile-damage-attribute:VERSION-forge"
}
```
In `mods.toml` add a dependency to the mod:
```
modId="projectile_damage"
mandatory=true
versionRange="[VERSION,)"
ordering="AFTER"
side="BOTH"
```

(Substitute `VERSION` with the name of the latest release available on [Modrinth](https://modrinth.com/mod/projectile-damage-attribute/versions), for example: `3.0.0`)

## Applying projectile damage to weapons

### Bows and Crossbows

1. Make sure your custom Bow or Crossbow inherits from vanilla Bow or Crossbow classes.  

2. Set the projectile damage for your weapon instance, preferably before registering it.
(Keep in mind, this doesn't fixate the damage output at a constant value, the vanilla behaviour adding randomness will be applied too)  
```java
((IProjectileWeapon)bowInstance).setProjectileDamage(10);
```

(Note: assigned damage value will be applied on the spawned arrow by this mod, no additional coding is required on your end.)

3. (Optional) If your weapon releases arrows at a **non default velocity**, use the following to **compensate** the velocity and make the weapon perform the expected amount of damage. (Default velocity: bow: 3.0, crossbow: 3.15).

```java
((IProjectileWeapon)bowInstance).setMaxProjectileVelocity(4.2);
```

### Custom weapon types

Custom weapon types such as: canons, blowpipes, etc...

1. Make sure the inheritance chain of your custom ranged weapon includes the vanilla class `ProjectileWeaponItem` (yarn:`RangedWeaponItem`) or provide a custom implementation of `net.projectile_damage.api.IProjectileWeapon` interface (default implementaion can be found [here](./common/src/main/java/net/projectile_damage/api/IProjectileWeapon.java)).

2. Create a custom weapon type and save it somewhere. This holds the shared properties of your weapon class, which each instance will be scaled against.

```java
public static class MyModItems {
    static RangedWeaponKind CANON = RangedWeaponKind.custom(6, 1.9D);
}
```

3. Configure your items.

```java
public static class MyModItems {
    static RangedWeaponKind CANNON = RangedWeaponKind.custom(6, 1.9D);
    
    public static MyCanon woodenCanon;
    public static MyCanon ironCanon;
    public static MyCanon diamondCanon;
    
    static {
        woodenCannon = new MyCannon(...);
        ((IProjectileWeapon)woodenCannon).setRangedWeaponKind(CANNON);
        // No custom damage is configured, default will be used from weapon kind
        
        ironCannon = new MyCannon(...);
        ((IProjectileWeapon)ironCannon).setRangedWeaponKind(CANNON);
        ((IProjectileWeapon)ironCannon).setProjectileDamage(8);
        
        diamondCannon = new MyCannon(...);
        ((IProjectileWeapon)diamondCannon).setRangedWeaponKind(CANNON);
        ((IProjectileWeapon)ironCannon).setProjectileDamage(10);
    } 
}
```