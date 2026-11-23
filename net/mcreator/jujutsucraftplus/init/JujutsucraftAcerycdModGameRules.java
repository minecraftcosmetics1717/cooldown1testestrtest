package net.mcreator.jujutsucraftplus.init;

import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.GameRules.Category;
import net.minecraft.world.level.GameRules.IntegerValue;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(
   bus = Bus.MOD
)
public class JujutsucraftAcerycdModGameRules {
   public static final GameRules.Key<GameRules.IntegerValue> SIX_EYES_BUFF;
   public static final GameRules.Key<GameRules.IntegerValue> SEPARATE_COOLDOWN_VALUE;

   static {
      SIX_EYES_BUFF = GameRules.m_46189_("sixEyesBuff", Category.PLAYER, IntegerValue.m_46312_(10));
      SEPARATE_COOLDOWN_VALUE = GameRules.m_46189_("separateCooldownValue", Category.PLAYER, IntegerValue.m_46312_(2));
   }
}
