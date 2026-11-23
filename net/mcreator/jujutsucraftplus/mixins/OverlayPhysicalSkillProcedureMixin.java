package net.mcreator.jujutsucraftplus.mixins;

import net.mcreator.jujutsucraft.network.JujutsucraftModVariables;
import net.mcreator.jujutsucraft.procedures.OverlayDefaultSkillProcedure;
import net.mcreator.jujutsucraft.procedures.OverlayPhysicalSkillProcedure;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(
   value = {OverlayPhysicalSkillProcedure.class},
   remap = false
)
public abstract class OverlayPhysicalSkillProcedureMixin {
   @Overwrite
   public static boolean execute(Entity entity) {
      if (entity == null) {
         return false;
      } else {
         JujutsucraftModVariables.PlayerVariables pVars = (JujutsucraftModVariables.PlayerVariables)entity.getCapability(JujutsucraftModVariables.PLAYER_VARIABLES_CAPABILITY, (Direction)null).orElse(new JujutsucraftModVariables.PlayerVariables());
         return pVars.PhysicalAttack && !OverlayDefaultSkillProcedure.execute(entity);
      }
   }
}
