package net.mcreator.jujutsucraftplus.mixins;

import net.mcreator.jujutsucraft.network.JujutsucraftModVariables;
import net.mcreator.jujutsucraft.procedures.CooldownTimeEffectStartedappliedProcedure;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraftforge.registries.ForgeRegistries;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(
   value = {CooldownTimeEffectStartedappliedProcedure.class},
   remap = false
)
public abstract class CooldownTimeEffectStartedappliedProcedureMixin {
   @Overwrite
   public static void execute(Entity entity) {
      if (entity != null) {
         double duration = (double)0.0F;
         if (entity instanceof LivingEntity) {
            LivingEntity _living = (LivingEntity)entity;
            JujutsucraftModVariables.PlayerVariables pVars = (JujutsucraftModVariables.PlayerVariables)entity.getCapability(JujutsucraftModVariables.PLAYER_VARIABLES_CAPABILITY, (Direction)null).orElse(new JujutsucraftModVariables.PlayerVariables());
            int id = (int)pVars.PlayerSelectCurseTechnique;
            if (id >= 1 && id <= 19) {
               String effectName = id == 1 ? "extension_cooldown" : "extension_cooldown_" + id;
               MobEffect extCooldown = (MobEffect)ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation("jujutsucraft_acerycd", effectName));
               if (extCooldown != null && _living.m_21023_(extCooldown)) {
                  duration = (double)_living.m_21124_(extCooldown).m_19557_();
               }
            }
         }

         if (entity instanceof Player) {
            Player _player = (Player)entity;
            _player.m_36335_().m_41524_(Items.f_42383_, (int)duration);
         }

         entity.getPersistentData().m_128347_("COOLDOWN_TICKS", Math.max(entity.getPersistentData().m_128459_("COOLDOWN_TICKS"), (double)Math.round(duration)));
      }
   }
}
