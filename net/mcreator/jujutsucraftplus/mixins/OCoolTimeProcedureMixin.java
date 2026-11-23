package net.mcreator.jujutsucraftplus.mixins;

import net.mcreator.jujutsucraft.init.JujutsucraftModMobEffects;
import net.mcreator.jujutsucraft.network.JujutsucraftModVariables;
import net.mcreator.jujutsucraft.procedures.OCoolTimeProcedure;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.registries.ForgeRegistries;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(
   value = {OCoolTimeProcedure.class},
   remap = false
)
public abstract class OCoolTimeProcedureMixin {
   @Overwrite
   public static String execute(Entity entity) {
      if (entity == null) {
         return "";
      } else {
         int durationTicks = 0;
         if (entity instanceof LivingEntity) {
            LivingEntity _living = (LivingEntity)entity;
            if (_living.m_21023_((MobEffect)JujutsucraftModMobEffects.UNSTABLE.get())) {
               durationTicks = _living.m_21124_((MobEffect)JujutsucraftModMobEffects.UNSTABLE.get()).m_19557_();
            } else {
               JujutsucraftModVariables.PlayerVariables pVars = (JujutsucraftModVariables.PlayerVariables)entity.getCapability(JujutsucraftModVariables.PLAYER_VARIABLES_CAPABILITY, (Direction)null).orElse(new JujutsucraftModVariables.PlayerVariables());
               double subSkillS1 = pVars.PlayerSelectCurseTechnique;
               int id = (int)subSkillS1;
               if (id >= 1 && id <= 19) {
                  String effectName = id == 1 ? "extension_cooldown" : "extension_cooldown_" + id;
                  MobEffect extCooldown = (MobEffect)ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation("jujutsucraft_acerycd", effectName));
                  if (extCooldown != null && _living.m_21023_(extCooldown)) {
                     durationTicks = _living.m_21124_(extCooldown).m_19557_();
                  }
               }
            }
         }

         double displayVal = Math.ceil((double)durationTicks * 0.1);
         return displayVal > (double)0.0F ? String.valueOf(Math.round(displayVal)) : "";
      }
   }
}
