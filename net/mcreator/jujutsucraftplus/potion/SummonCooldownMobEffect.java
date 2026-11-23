package net.mcreator.jujutsucraftplus.potion;

import net.mcreator.jujutsucraftplus.procedures.SummonCooldownOnEffectActiveTickProcedure;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class SummonCooldownMobEffect extends MobEffect {
   public SummonCooldownMobEffect() {
      super(MobEffectCategory.NEUTRAL, -1);
   }

   public void m_6742_(LivingEntity entity, int amplifier) {
      SummonCooldownOnEffectActiveTickProcedure.execute(entity);
   }

   public boolean m_6584_(int duration, int amplifier) {
      return true;
   }
}
