package mcagent.actuator.movement;

import net.minecraft.client.Minecraft;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3;

import java.util.LinkedList;

/**
 * Created by Chad on 5/25/2015.
 */
public class Target {
    private WorldGrid w = WorldGrid.getInstance();
    private int x,y,z;
    private BlockPos block;
    private LinkedList<Target> neighbors = new LinkedList<Target>();
    private long lastUse;
    private boolean isCave;

    public Target(int x, int y, int z, boolean isCave) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.isCave = isCave;
        this.block = new BlockPos(x,y,z);
        lastUse = Minecraft.getMinecraft().getSystemTime();
    }

    public Target(BlockPos p, boolean isCave) {
        this.x = p.getX();
        this.y = p.getY();
        this.z = p.getZ();
        this.isCave = isCave;
        this.block = p;
        lastUse = Minecraft.getMinecraft().getSystemTime();
    }

    public LinkedList<Target> getNeighbors() {
        return neighbors;
    }

    public double getX() {
        return x;
    }
    public double getY() {
        return y;
    }
    public double getZ() {
        return z;
    }
    public double[] coords(){
        return new double[] {x,y,z};
    }
    public BlockPos getBlock() { return block; }
    public Vec3 getVector() { return new Vec3(block.getX()+0.5, block.getY(), block.getZ()); }
    public boolean isCave() {
        return isCave;
    }

    @Override
    public String toString() {
        return super.toString() + String.format(" {%6d, %6d, %6d}",x,y,z);
    }
}
