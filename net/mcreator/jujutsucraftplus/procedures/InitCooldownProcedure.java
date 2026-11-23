package net.mcreator.jujutsucraftplus.procedures;

import javax.annotation.Nullable;
import net.mcreator.jujutsucraft.init.JujutsucraftModMobEffects;
import net.mcreator.jujutsucraft.network.JujutsucraftModVariables;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.TickEvent.Phase;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.registries.ForgeRegistries;

@EventBusSubscriber
public class InitCooldownProcedure {
   @SubscribeEvent
   public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
      if (event.phase == Phase.END) {
         execute(event, event.player.m_9236_(), event.player);
      }

   }

   public static void execute(LevelAccessor world, Entity entity) {
      execute((Event)null, world, entity);
   }

   private static void execute(@Nullable Event event, LevelAccessor world, Entity entity) {
      if (entity != null && entity instanceof LivingEntity _living) {
         JujutsucraftModVariables.PlayerVariables pVars = (JujutsucraftModVariables.PlayerVariables)entity.getCapability(JujutsucraftModVariables.PLAYER_VARIABLES_CAPABILITY, (Direction)null).orElse(new JujutsucraftModVariables.PlayerVariables());
         int skillId = (int)pVars.PlayerSelectCurseTechnique;
         boolean hasExtensionActive = false;
         if (skillId >= 1 && skillId <= 19) {
            String effectName = skillId == 1 ? "extension_cooldown" : "extension_cooldown_" + skillId;
            MobEffect extCooldown = (MobEffect)ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation("jujutsucraft_acerycd", effectName));
            if (extCooldown != null && _living.m_21023_(extCooldown)) {
               hasExtensionActive = true;
               if (!world.m_5776_()) {
                  boolean isPressingZ = entity.getPersistentData().m_128471_("PRESS_Z");
                  boolean hasCursedTechnique = _living.m_21023_((MobEffect)JujutsucraftModMobEffects.CURSED_TECHNIQUE.get());
                  if (isPressingZ && hasCursedTechnique) {
                     int maxTicks = (int)entity.getPersistentData().m_128459_("COOLDOWN_TICKS");
                     if (maxTicks > 0) {
                        _living.m_7292_(new MobEffectInstance(extCooldown, maxTicks, 0, false, false));
                     }
                  }

                  int currentAmp = _living.m_21023_((MobEffect)JujutsucraftModMobEffects.COOLDOWN_TIME.get()) ? _living.m_21124_((MobEffect)JujutsucraftModMobEffects.COOLDOWN_TIME.get()).m_19564_() : 0;
                  _living.m_7292_(new MobEffectInstance((MobEffect)JujutsucraftModMobEffects.COOLDOWN_TIME.get(), 1, currentAmp + 1, false, false));
                  int currentCombatAmp = _living.m_21023_((MobEffect)JujutsucraftModMobEffects.COOLDOWN_TIME_COMBAT.get()) ? _living.m_21124_((MobEffect)JujutsucraftModMobEffects.COOLDOWN_TIME_COMBAT.get()).m_19564_() : 0;
                  _living.m_7292_(new MobEffectInstance((MobEffect)JujutsucraftModMobEffects.COOLDOWN_TIME_COMBAT.get(), 1, currentCombatAmp + 1, false, false));
               }
            }
         }

         if (!hasExtensionActive && !world.m_5776_()) {
            _living.m_21195_((MobEffect)JujutsucraftModMobEffects.COOLDOWN_TIME.get());
            _living.m_21195_((MobEffect)JujutsucraftModMobEffects.COOLDOWN_TIME_COMBAT.get());
         }

      }
   }
}
