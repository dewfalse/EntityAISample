package Robot;

// 全ての子AIが非活性だった場合のための番兵
public class EntityRobotAIDoNothing extends EntityRobotAIChild {
    public EntityRobotAIDoNothing(EntityRobot entityRobot) {
    }

    @Override
    public boolean shouldExecute() {
        return false;
    }
}
