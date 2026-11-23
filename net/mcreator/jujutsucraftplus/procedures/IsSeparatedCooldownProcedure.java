package net.mcreator.jujutsucraftplus.procedures;

import javax.annotation.Nullable;
import net.mcreator.jujutsucraftplus.JujutsucraftAcerycdMod;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemCooldowns;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;

@EventBusSubscriber
public class IsSeparatedCooldownProcedure {
   @SubscribeEvent
   public static void onRightClickItem(PlayerInteractEvent.RightClickItem event) {
      if (event.getHand() == event.getEntity().m_7655_()) {
         execute(event, event.getLevel(), event.getEntity());
      }
   }

   public static void execute(LevelAccessor world, Entity entity) {
      execute((Event)null, world, entity);
   }

   private static void execute(@Nullable Event event, LevelAccessor world, Entity entity) {
      if (entity != null) {
         JujutsucraftAcerycdMod.queueServerWork(1, () -> {
            IForgeRegistry var10000 = ForgeRegistries.ITEMS;
            ItemStack var10001;
            if (entity instanceof LivingEntity _livEnt) {
               var10001 = _livEnt.m_21206_();
            } else {
               var10001 = ItemStack.f_41583_;
            }

            if (var10000.getKey(var10001.m_41720_()).toString().equals("jujutsucraft:copied_cursed_technique")) {
               if (entity instanceof Player) {
                  Player _player = (Player)entity;
                  ItemCooldowns var9 = _player.m_36335_();
                  if (entity instanceof LivingEntity) {
                     LivingEntity _livEnt = (LivingEntity)entity;
                     var10001 = _livEnt.m_21206_();
                  } else {
                     var10001 = ItemStack.f_41583_;
                  }

                  Item var13 = var10001.m_41720_();
                  ItemStack var10002;
                  if (entity instanceof LivingEntity) {
                     LivingEntity _livEnt = (LivingEntity)entity;
                     var10002 = _livEnt.m_21206_();
                  } else {
                     var10002 = ItemStack.f_41583_;
                  }

                  var9.m_41524_(var13, (int)var10002.m_41784_().m_128459_("COOLDOWN_TICKS"));
               }
            } else {
               var10000 = ForgeRegistries.ITEMS;
               if (entity instanceof LivingEntity) {
                  LivingEntity _livEnt = (LivingEntity)entity;
                  var10001 = _livEnt.m_21205_();
               } else {
                  var10001 = ItemStack.f_41583_;
               }

               if (var10000.getKey(var10001.m_41720_()).toString().equals("jujutsucraft:copied_cursed_technique") && entity instanceof Player) {
                  Player _player = (Player)entity;
                  ItemCooldowns var11 = _player.m_36335_();
                  if (entity instanceof LivingEntity) {
                     LivingEntity _livEnt = (LivingEntity)entity;
                     var10001 = _livEnt.m_21205_();
                  } else {
                     var10001 = ItemStack.f_41583_;
                  }

                  Item var16 = var10001.m_41720_();
                  ItemStack var17;
                  if (entity instanceof LivingEntity) {
                     LivingEntity _livEnt = (LivingEntity)entity;
                     var17 = _livEnt.m_21205_();
                  } else {
                     var17 = ItemStack.f_41583_;
                  }

                  var11.m_41524_(var16, (int)var17.m_41784_().m_128459_("COOLDOWN_TICKS"));
               }
            }

         });
      }
   }
}
