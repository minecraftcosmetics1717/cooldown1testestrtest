package net.mcreator.jujutsucraftplus.potion;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

public class ExtensionCooldown4MobEffect extends MobEffect {
   public ExtensionCooldown4MobEffect() {
      super(MobEffectCategory.NEUTRAL, -1);
   }

   public boolean m_6584_(int duration, int amplifier) {
      return true;
   }
}
