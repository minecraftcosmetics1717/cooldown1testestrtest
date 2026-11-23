package net.mcreator.jujutsucraftplus.mixins;

import net.mcreator.jujutsucraft.init.JujutsucraftModGameRules;
import net.mcreator.jujutsucraft.init.JujutsucraftModItems;
import net.mcreator.jujutsucraft.init.JujutsucraftModMobEffects;
import net.mcreator.jujutsucraft.network.JujutsucraftModVariables;
import net.mcreator.jujutsucraft.procedures.KeyChangeTechniqueOnKeyPressedProcedure;
import net.mcreator.jujutsucraft.procedures.LogicStartProcedure;
import net.mcreator.jujutsucraft.procedures.ResetCounterProcedure;
import net.mcreator.jujutsucraft.procedures.StartCursedTechniqueProcedure;
import net.mcreator.jujutsucraftplus.init.JujutsucraftAcerycdModGameRules;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.registries.ForgeRegistries;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(
   value = {StartCursedTechniqueProcedure.class},
   remap = false
)
public abstract class StartCursedTechniqueProcedureMixin {
   @Overwrite
   public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
      if (entity != null) {
         String techniqueName = "";
         boolean isCreative = false;
         boolean isItemUse = false;
         boolean noUseCursePower = false;
         double cost = (double)0.0F;
         double skillId = (double)0.0F;
         double cooldownTicks = (double)0.0F;
         double categoryT1 = (double)0.0F;
         double subSkillS1 = (double)0.0F;
         JujutsucraftModVariables.PlayerVariables pVars = (JujutsucraftModVariables.PlayerVariables)entity.getCapability(JujutsucraftModVariables.PLAYER_VARIABLES_CAPABILITY, (Direction)null).orElse(new JujutsucraftModVariables.PlayerVariables());
         ItemStack var10000;
         if (entity instanceof LivingEntity) {
            LivingEntity _liv = (LivingEntity)entity;
            var10000 = _liv.m_21205_();
         } else {
            var10000 = ItemStack.f_41583_;
         }

         ItemStack mainHandItemOrigin = var10000;
         ItemStack mainHandItem = mainHandItemOrigin.m_41777_();
         if (mainHandItem.m_41784_().m_128441_("used_item") && mainHandItem.m_41784_().m_128471_("used_item")) {
            mainHandItemOrigin.m_41784_().m_128379_("used_item", false);
            isItemUse = true;
         }

         if (isItemUse) {
            techniqueName = mainHandItem.m_41786_().getString();
            skillId = mainHandItem.m_41784_().m_128459_("skill");
            cost = mainHandItem.m_41784_().m_128459_("COOLDOWN_TICKS");
            if (mainHandItem.m_41720_() == JujutsucraftModItems.SWORD_OKKOTSU_YUTA.get()) {
               noUseCursePower = true;
            }

            categoryT1 = (double)Math.round(Math.floor(skillId / (double)100.0F));
            subSkillS1 = (double)Math.round(Math.floor(skillId % (double)100.0F));
         } else {
            pVars.noChangeTechnique = true;
            KeyChangeTechniqueOnKeyPressedProcedure.execute(world, x, y, z, entity);
            techniqueName = pVars.PlayerSelectCurseTechniqueName;
            categoryT1 = pVars.SecondTechnique ? pVars.PlayerCurseTechnique2 : pVars.PlayerCurseTechnique;
            subSkillS1 = pVars.PlayerSelectCurseTechnique;
         }

         if (entity instanceof LivingEntity) {
            LivingEntity _living = (LivingEntity)entity;
            MobEffect extCooldown = getExtensionCooldownEffect((int)subSkillS1);
            if (extCooldown != null && _living.m_21023_(extCooldown)) {
               return;
            }
         }

         label204: {
            if (isItemUse) {
               if (entity instanceof LivingEntity) {
                  LivingEntity _liv = (LivingEntity)entity;
                  if (_liv.m_21023_((MobEffect)JujutsucraftModMobEffects.CURSED_TECHNIQUE.get())) {
                     break label204;
                  }
               }
            } else if (!LogicStartProcedure.execute(entity)) {
               break label204;
            }

            GameType gameMode = GameType.SURVIVAL;
            if (entity instanceof ServerPlayer) {
               ServerPlayer _sp = (ServerPlayer)entity;
               gameMode = _sp.f_8941_.m_9290_();
            } else if (world.m_5776_() && entity instanceof Player) {
               Player _p = (Player)entity;
               if (Minecraft.m_91087_().m_91403_().m_104949_(_p.m_20148_()) != null) {
                  gameMode = Minecraft.m_91087_().m_91403_().m_104949_(_p.m_20148_()).m_105325_();
               }
            }

            if (gameMode != GameType.SPECTATOR) {
               isCreative = gameMode == GameType.CREATIVE;
               double powerCheck = pVars.PlayerCursePower - (noUseCursePower ? (double)0.0F : (isItemUse ? cost : pVars.PlayerSelectCurseTechniqueCost));
               if (powerCheck < (double)0.0F && !isCreative) {
                  if (entity instanceof Player) {
                     Player _p = (Player)entity;
                     if (!world.m_5776_()) {
                        _p.m_5661_(Component.m_237115_("jujutsu.message.dont_use"), true);
                     }
                  }

                  return;
               }

               if (!isCreative) {
                  pVars.PlayerCursePower = powerCheck;
               }

               entity.getPersistentData().m_128347_("skill", isItemUse ? skillId : categoryT1 * (double)100.0F + subSkillS1);
               ResetCounterProcedure.execute(entity);
               if (entity instanceof LivingEntity) {
                  LivingEntity _liv = (LivingEntity)entity;
                  if (!world.m_5776_()) {
                     _liv.m_7292_(new MobEffectInstance((MobEffect)JujutsucraftModMobEffects.CURSED_TECHNIQUE.get(), Integer.MAX_VALUE, 0, false, false));
                  }
               }

               if ((categoryT1 != (double)2.0F || subSkillS1 != (double)5.0F) && (categoryT1 != (double)9.0F || subSkillS1 != (double)10.0F) && (categoryT1 != (double)18.0F || subSkillS1 < (double)10.0F || subSkillS1 >= (double)15.0F) && !isCreative) {
                  if (subSkillS1 == (double)20.0F) {
                     cooldownTicks = (double)20.0F;
                  } else {
                     cooldownTicks = Math.max((isItemUse ? cost : pVars.PlayerSelectCurseTechniqueCostOrgin) / (double)2.0F, (double)20.0F);
                     if (techniqueName.equals(Component.m_237115_("jujutsu.technique.attack1").getString())) {
                        cooldownTicks = (double)5.0F;
                     } else if (techniqueName.equals(Component.m_237115_("jujutsu.technique.attack2").getString())) {
                        cooldownTicks = (double)15.0F;
                     } else if (techniqueName.equals(Component.m_237115_("jujutsu.technique.attack3").getString())) {
                        cooldownTicks = (double)20.0F;
                     } else if (techniqueName.equals(Component.m_237115_("jujutsu.technique.attack4").getString())) {
                        cooldownTicks = (double)100.0F;
                     } else if (techniqueName.equals(Component.m_237115_("jujutsu.technique.attack5").getString())) {
                        cooldownTicks = (double)200.0F;
                     } else if (techniqueName.equals(Component.m_237115_("jujutsu.technique.attack7").getString())) {
                        cooldownTicks = (double)100.0F;
                     }
                  }
               } else {
                  cooldownTicks = (double)1.0F;
               }

               int multiplier = world.m_6106_().m_5470_().m_46215_(JujutsucraftAcerycdModGameRules.SEPARATE_COOLDOWN_VALUE);
               cooldownTicks *= (double)multiplier;
               if (world.m_6106_().m_5470_().m_46207_(JujutsucraftModGameRules.JUJUTSU_GAIN_FAME)) {
                  pVars.PlayerTechniqueUsedNumber += (double)Math.round(cooldownTicks);
                  double diff = (double)1.0F + (double)world.m_6106_().m_5470_().m_46215_(JujutsucraftModGameRules.JUJUTSUUPGRADEDIFFICULTY) / (double)10.0F;
                  if (entity instanceof ServerPlayer) {
                     ServerPlayer _sp = (ServerPlayer)entity;
                     grantAdvancement(_sp, "jujutsucraft:mastery_simple_domain", pVars.PlayerTechniqueUsedNumber > (double)(categoryT1 != (double)27.0F ? 4000 : 2000) * diff);
                     grantAdvancement(_sp, "jujutsucraft:mastery_domain_expansion", pVars.PlayerTechniqueUsedNumber > (double)(categoryT1 != (double)27.0F ? 12000 : 100) * diff);
                  }
               }

               if (entity instanceof LivingEntity) {
                  LivingEntity _living = (LivingEntity)entity;
                  if (!world.m_5776_()) {
                     MobEffect extCooldown = getExtensionCooldownEffect((int)subSkillS1);
                     if (extCooldown != null) {
                        _living.m_7292_(new MobEffectInstance(extCooldown, (int)Math.round(cooldownTicks), 0, false, false));
                     }
                  }
               }

               entity.getPersistentData().m_128347_("COOLDOWN_TICKS", Math.max(entity.getPersistentData().m_128459_("COOLDOWN_TICKS"), (double)Math.round(cooldownTicks)));
               return;
            }
         }

         if (entity instanceof Player) {
            Player _p = (Player)entity;
            if (!world.m_5776_()) {
               _p.m_5661_(Component.m_237115_("jujutsu.message.dont_use"), true);
            }
         }

      }
   }

   private static MobEffect getExtensionCooldownEffect(int id) {
      if (id >= 1 && id <= 19) {
         String effectName = id == 1 ? "extension_cooldown" : "extension_cooldown_" + id;
         return (MobEffect)ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation("jujutsucraft_acerycd", effectName));
      } else {
         return null;
      }
   }

   private static void grantAdvancement(ServerPlayer player, String id, boolean condition) {
      if (condition) {
         Advancement adv = player.f_8924_.m_129889_().m_136041_(new ResourceLocation(id));
         if (adv != null) {
            AdvancementProgress ap = player.m_8960_().m_135996_(adv);
            if (!ap.m_8193_()) {
               for(String criteria : ap.m_8219_()) {
                  player.m_8960_().m_135988_(adv, criteria);
               }
            }
         }

      }
   }
}
