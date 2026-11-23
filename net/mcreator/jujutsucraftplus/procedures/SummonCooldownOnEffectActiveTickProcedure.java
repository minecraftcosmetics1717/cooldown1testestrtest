package net.mcreator.jujutsucraftplus.procedures;

import net.mcreator.jujutsucraftplus.init.JujutsucraftAcerycdModMobEffects;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

public class SummonCooldownOnEffectActiveTickProcedure {
   public static void execute(Entity entity) {
      if (entity != null) {
         int var10000;
         label58: {
            if (entity instanceof LivingEntity) {
               LivingEntity _livEnt = (LivingEntity)entity;
               if (_livEnt.m_21023_((MobEffect)JujutsucraftAcerycdModMobEffects.SUMMON_COOLDOWN.get())) {
                  var10000 = _livEnt.m_21124_((MobEffect)JujutsucraftAcerycdModMobEffects.SUMMON_COOLDOWN.get()).m_19557_();
                  break label58;
               }
            }

            var10000 = 0;
         }

         if (var10000 >= 2000 && entity instanceof LivingEntity) {
            LivingEntity _entity = (LivingEntity)entity;
            if (!_entity.m_9236_().m_5776_()) {
               MobEffectInstance var10001;
               MobEffect var10003;
               int var10004;
               label49: {
                  var10001 = new MobEffectInstance;
                  var10003 = (MobEffect)JujutsucraftAcerycdModMobEffects.SUMMON_COOLDOWN.get();
                  if (entity instanceof LivingEntity) {
                     LivingEntity _livEnt = (LivingEntity)entity;
                     if (_livEnt.m_21023_((MobEffect)JujutsucraftAcerycdModMobEffects.SUMMON_COOLDOWN.get())) {
                        var10004 = _livEnt.m_21124_((MobEffect)JujutsucraftAcerycdModMobEffects.SUMMON_COOLDOWN.get()).m_19557_();
                        break label49;
                     }
                  }

                  var10004 = 0;
               }

               var10001.<init>(var10003, var10004, 1, false, false);
               _entity.m_7292_(var10001);
            }
         }

         label44: {
            if (entity instanceof LivingEntity) {
               LivingEntity _livEnt = (LivingEntity)entity;
               if (_livEnt.m_21023_((MobEffect)JujutsucraftAcerycdModMobEffects.SUMMON_COOLDOWN.get())) {
                  var10000 = _livEnt.m_21124_((MobEffect)JujutsucraftAcerycdModMobEffects.SUMMON_COOLDOWN.get()).m_19564_();
                  break label44;
               }
            }

            var10000 = 0;
         }

         if (var10000 >= 1) {
            label37: {
               if (entity instanceof LivingEntity) {
                  LivingEntity _livEnt = (LivingEntity)entity;
                  if (_livEnt.m_21023_((MobEffect)JujutsucraftAcerycdModMobEffects.SUMMON_COOLDOWN.get())) {
                     var10000 = _livEnt.m_21124_((MobEffect)JujutsucraftAcerycdModMobEffects.SUMMON_COOLDOWN.get()).m_19557_();
                     break label37;
                  }
               }

               var10000 = 0;
            }

            if (var10000 <= 1 && entity instanceof LivingEntity) {
               LivingEntity _entity = (LivingEntity)entity;
               _entity.m_21195_((MobEffect)JujutsucraftAcerycdModMobEffects.SUMMON_COOLDOWN.get());
            }
         }

      }
   }
}
