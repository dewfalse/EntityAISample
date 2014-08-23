package Robot;

import net.minecraft.entity.ai.EntityAIBase;
import java.util.ArrayList;
import java.util.List;

// EntityRobot専用AI
// ただし、このクラスはメソッドの受付だけを行い、実際の処理は子AIに移譲する
// 通常時は何もしない子AIが選択された状態になっており、
// shouldExecuteが呼ばれた際に適切な子AIを選択する
// 子AIの終了が確定するresetTaskで何もしない子AIが選択された状態に戻す
public class EntityRobotAIManager extends EntityAIBase {
    // EntityRobot専用AIリスト
    List<EntityRobotAIChild> children = new ArrayList<>();

    // 現在活性化している子AI
    EntityRobotAIChild working = null;

    // 何もしないAI。NullObject的存在
    EntityRobotAIDoNothing aiDoNothing;

    public EntityRobotAIManager(EntityRobot entityRobot, float v) {
        // 専用AIを子AIリストに登録
        children.add(new EntityRobotAITask1(entityRobot));
        children.add(new EntityRobotAITask2(entityRobot));
        children.add(new EntityRobotAITask3(entityRobot));

        // 番兵を初期化して子AIリスト末尾に登録
        aiDoNothing = new EntityRobotAIDoNothing(entityRobot);
        children.add(aiDoNothing);

        // 現在有効な子AIを初期化
        working = aiDoNothing;
    }

    @Override
    public boolean shouldExecute() {
        // 有効な子AIを入れ替える

        // 現在有効な子AIが引き続き実行可能ならそのまま使用
        if (working.shouldExecute()) {
            return true;
        }

        // 現在有効な子AIが実行不可なので子AIリストから使える子AIを探す
        for(EntityRobotAIChild child : children) {
            if(child.shouldExecute()) {
                // 有効な子AIを入れ替え
                working = child;
                return true;
            }
        }

        // 有効な子AIが見つからなかったので何もしないAIを選択
        working = aiDoNothing;
        return false;
    }

    @Override
    public boolean continueExecuting() {
        return working.continueExecuting();
    }

    @Override
    public boolean isInterruptible() {
        return working.isInterruptible();
    }

    @Override
    public void startExecuting() {
        working.startExecuting();
    }

    @Override
    public void resetTask() {
        working.resetTask();

        // 子AIの実行が完了したので初期状態に戻す
        working = aiDoNothing;
    }

    @Override
    public void updateTask() {
        working.updateTask();
    }

    @Override
    public void setMutexBits(int p_75248_1_) {
        working.setMutexBits(p_75248_1_);
    }

    @Override
    public int getMutexBits() {
        return working.getMutexBits();
    }
}
