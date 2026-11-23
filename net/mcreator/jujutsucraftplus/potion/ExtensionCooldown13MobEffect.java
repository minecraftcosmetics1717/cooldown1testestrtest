package net.mcreator.jujutsucraftplus.potion;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

public class ExtensionCooldown13MobEffect extends MobEffect {
   public ExtensionCooldown13MobEffect() {
      super(MobEffectCategory.NEUTRAL, -1);
   }

   public boolean m_6584_(int duration, int amplifier) {
      return true;
   }
}
