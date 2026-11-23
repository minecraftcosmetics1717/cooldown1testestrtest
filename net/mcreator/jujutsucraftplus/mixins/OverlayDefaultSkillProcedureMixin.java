package net.mcreator.jujutsucraftplus.mixins;

import net.mcreator.jujutsucraft.network.JujutsucraftModVariables;
import net.mcreator.jujutsucraft.procedures.OverlayDefaultSkillProcedure;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(
   value = {OverlayDefaultSkillProcedure.class},
   remap = false
)
public abstract class OverlayDefaultSkillProcedureMixin {
   @Overwrite
   public static boolean execute(Entity entity) {
      if (entity == null) {
         return false;
      } else {
         JujutsucraftModVariables.PlayerVariables pVars = (JujutsucraftModVariables.PlayerVariables)entity.getCapability(JujutsucraftModVariables.PLAYER_VARIABLES_CAPABILITY, (Direction)null).orElse(new JujutsucraftModVariables.PlayerVariables());
         double subSkill = pVars.PlayerSelectCurseTechnique % (double)100.0F;
         return pVars.PhysicalAttack && subSkill >= (double)0.0F && subSkill <= (double)2.0F;
      }
   }
}
