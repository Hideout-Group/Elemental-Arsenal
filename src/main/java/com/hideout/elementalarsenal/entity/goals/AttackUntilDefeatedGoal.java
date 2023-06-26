package com.hideout.elementalarsenal.entity.goals;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.AttackGoal;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.mob.MobEntity;

import java.util.EnumSet;

public class AttackUntilDefeatedGoal extends Goal {
    private final MobEntity mob;
    private LivingEntity target;
    private int cooldown;

    public AttackUntilDefeatedGoal(MobEntity mob, LivingEntity target) {
        this.mob = mob;
        this.target = target;
        this.setControls(EnumSet.of(Control.MOVE, Control.LOOK));
    }

    public void setTarget(LivingEntity target) {
        this.target = target;
    }

    public boolean canStart() {
        return target != null;
    }

    public boolean shouldContinue() {
        if (!this.target.isAlive()) {
            return false;
        } else if (this.mob.squaredDistanceTo(this.target) > 225.0) {
            return false;
        } else {
            return !this.mob.getNavigation().isIdle() || this.canStart();
        }
    }

    public void stop() {
        this.target = null;
        this.mob.getNavigation().stop();
    }

    public boolean shouldRunEveryTick() {
        return true;
    }

    public void tick() {
        this.mob.getLookControl().lookAt(this.target, 30.0F, 30.0F);
        double d = (this.mob.getWidth() * 2.0F * this.mob.getWidth() * 2.0F);
        double e = this.mob.squaredDistanceTo(this.target.getX(), this.target.getY(), this.target.getZ());
        double f = 0.8;
        if (e > d && e < 16.0) {
            f = 1.33;
        } else if (e < 225.0) {
            f = 0.6;
        }

        this.mob.getNavigation().startMovingTo(this.target, f);
        this.cooldown = Math.max(this.cooldown - 1, 0);
        if (!(e > d)) {
            if (this.cooldown <= 0) {
                this.cooldown = 20;
                this.mob.tryAttack(this.target);
            }
        }
    }
}
