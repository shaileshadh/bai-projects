import java.awt.Robot;
public class Click{
	static int MODE;
	static int CLICKS;
	static int DURATION;
	static int SLEEP;
	static int[][] PRESETS = {
		// Mode 0: Click 50 times.
		{1, 50, 0, 50},
		// Mode 1: Click for 10 seconds.
		{2, 0, 10000, 50},
		// Mode 2: Click for 30 seconds.
		{2, 0, 30000, 50}
	};
	public static void main(String[] args) throws Throwable{
		if(args.length != 1){
			System.out.println("Preset");
			return;
		}
		int preset = Integer.parseInt(args[0]);
		MODE = PRESETS[preset][0];
		CLICKS = PRESETS[preset][1];
		DURATION = PRESETS[preset][2];
		SLEEP = PRESETS[preset][3];
		Robot r = new Robot();
		Thread.sleep(2000);
		if(MODE==1)
			for(int i=0; i<CLICKS; i++){
				r.mousePress(16);
				r.mouseRelease(16);
				Thread.sleep(SLEEP);
			}
		else if(MODE==2){
			long l = System.currentTimeMillis() + DURATION;
			while(System.currentTimeMillis() < l){
				r.mousePress(16);
				r.mouseRelease(16);
				Thread.sleep(SLEEP);
			}
		}
	}
}
