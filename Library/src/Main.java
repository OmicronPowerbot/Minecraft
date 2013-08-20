import org.qatide.minecraft.MinecraftFormatException;
import org.qatide.minecraft.world.World;

import java.io.File;
import java.io.IOException;

/**
 * @author Omicron
 */
public class Main {
	public static void main(String[] args) throws MinecraftFormatException, IOException, InterruptedException {
		long time = System.currentTimeMillis();
		World world = new World(new File("C:\\Users\\Scott\\AppData\\Roaming\\.minecraft\\saves\\Pokemon"));
		long time1 = System.currentTimeMillis();
		world.save();
		long time2 = System.currentTimeMillis();
		System.out.println((time1 - time) + " " + (time2 - time1));
	}
}
