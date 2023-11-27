package net.daveyx0.primitivemobs.entity.ai;

import net.daveyx0.primitivemobs.entity.monster.EntityPrimitiveCreeper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;

public class EntityAIPrimitiveCreeperSwell extends EntityAIBase {
  EntityPrimitiveCreeper swellingCreeper;
  
  EntityLivingBase creeperAttackTarget;
  
  private static final String __OBFID = "CL_00001614";
  
  public EntityAIPrimitiveCreeperSwell(EntityPrimitiveCreeper entityPrimitiveCreeper) {
    this.swellingCreeper = entityPrimitiveCreeper;
    setMutexBits(1);
  }
  
  public boolean shouldExecute() {
    EntityLivingBase entitylivingbase = this.swellingCreeper.getAttackTarget();
    return (this.swellingCreeper.getCreeperState() > 0 || (entitylivingbase != null && this.swellingCreeper.getDistanceSqToEntity((Entity)entitylivingbase) < 9.0D));
  }
  
  public void startExecuting() {
    this.swellingCreeper.getNavigator().clearPathEntity();
    this.creeperAttackTarget = this.swellingCreeper.getAttackTarget();
  }
  
  public void resetTask() {
    this.creeperAttackTarget = null;
  }
  
  public void func_75246_d() {
    if (this.creeperAttackTarget == null) {
      this.swellingCreeper.setCreeperState(-1);
    } else if (this.swellingCreeper.getDistanceSqToEntity((Entity)this.creeperAttackTarget) > 49.0D) {
      this.swellingCreeper.setCreeperState(-1);
    } else if (!this.swellingCreeper.getEntitySenses().canSee((Entity)this.creeperAttackTarget)) {
      this.swellingCreeper.setCreeperState(-1);
    } else {
      this.swellingCreeper.setCreeperState(1);
    } 
  }
}
