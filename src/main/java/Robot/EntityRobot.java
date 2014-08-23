package Robot;

import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.world.World;

public class EntityRobot extends EntityTameable {
    public EntityRobot(World p_i1604_1_) {
        super(p_i1604_1_);

        this.setSize(0.9F, 0.9F);
        this.getNavigator().setAvoidsWater(true);
        this.tasks.addTask(1, new EntityAISwimming(this));
        this.tasks.addTask(2, new EntityAIFollowOwner(this, 1.0D, 10.0F, 5.0F));
        this.tasks.addTask(3, new EntityAIAvoidEntity(this, EntityMob.class, 16.0F, 0.8D, 1.33D));
        this.tasks.addTask(4, new EntityAILeapAtTarget(this, 0.3F));

        // 適当な優先度で専用AIを登録
        this.tasks.addTask(5, new EntityRobotAIManager(this, 1.25F));

        this.tasks.addTask(6, new EntityAIWander(this, 1.25F));
        this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityLiving.class, 6.0F, 0.02F));
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(10.0D);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.25D);
    }

    @Override
    public boolean isAIEnabled() {
        return true;
    }

    @Override
    public EntityAgeable createChild(EntityAgeable p_90011_1_) {
        return null;
    }
}
