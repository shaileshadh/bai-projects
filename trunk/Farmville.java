
/*
 * Farmville Macro by Bai.
 * Tested on Google Chrome on Vista.
 * Screen resolution must be 1280 x 960 (although it would probably work with other
 * resolutions if you adjust some coordinates).
 * Plots must be arranged perfectly in a grid.
 *
 * Start centered with maximum zoom-out in fullscreen mode.
 * To quit: Alt-Tab to Command Prompt and press Ctrl-C.
 */

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import java.util.*;
import javax.imageio.*;

public class Farmville{
// Whole thing is written C-style.

// ---------------------- DATA SECTION -----------------------
// Below are used variables, make sure they are correct before
// switching computers/accounts.

// If true, don't actually click anything and only move the mouse.
static boolean DEBUG = false;
// If true, wait and press OK
static boolean IsDelete = false;
// If true, use empty plow coordinates
static boolean IsEmpty = false;
// Take screenshots or not?
static boolean Screenshot = false;
// Mode for operation.
static int RunMode = 0;
// Starting position while fullscreened
static final int[] Center = {640,472};
// Starting position for plowing empty land
static final int[] PlowCenter = {640,484};
// Soybeans!
static final int[] SoybeanSquare = {564,576};
static final int[] SoybeanPlow = {564,591};
// Position of OK button
static final int[] OK = {561,573};
// Position of Market button
static final int[] Market = {945,925};
// Position of Plow button
static final int[] Plow = {945,868};
// Position of Delete button
static final int[] Delete = {992,868};
// Position of Soybean 'buy'
static final int[] BuySoybean = {847,534};
static final int[] DistanceBetweenPlotsAtMaxZoom = {25,12};
// Don't click these. These are the square coordinates, not screen.
static final int[][] DontClick = new int[][]{
	//{15,12}, // Soybean square
	{9,9} // Person caged here
};
// Time to sleep between clicks
static final int ClickSleepTime = 0;
// Time to sleep to wait for a popup
static final int PopupSleepTime = 1000;
//12, 14, 16, 18, 20
static final int FarmSize = 20;

// Unused variables.
// Top left corner. All other windowed coordinates are based off this one.
//static final int[] WindowedTopLeftCorner = {161,209};
// Location of Full-screen button.
//static final int[] WindowedButton = {695,458};
// Position of zoom-out button
//static final int[] Zoomout = {924,824};
//static final int[] ConfirmPlow = {948,797};
// Position of Normal button
//static final int[] Normal = {898,868};
// Position of the cursor after clicking normal
//static final int[] ConfirmNormal = {897,798};


// ---------------------- VARIABLES SECTION ----------------------
static Robot robot;
static int[][][] coords;
static int size = FarmSize;
static Random random;
static long startTime;

// ---------------------- CODE SECTION ---------------------------
public static void main(String... args)
	throws Throwable{

	// Command line arguments.
	boolean success = parseArgs(args);
	if(!success)
		System.exit(1);

	constructGrid();
	
	// Initialize components.
	robot = new Robot();
	random = new Random();
	
	// Wait a bit
	Thread.sleep(5000);

	startTime = System.currentTimeMillis();
	System.out.println("Started " + new Date(startTime));
	
	// We're moving!
	start();

	// Done.
	runFinalize();
}

static boolean parseArgs(String[] args){
	if(args.length==0){
		System.out.println(
"Farmville Macro. Copyright 2009 Bai. Unlikely to work.\n"+
"-debug -delete -plow -pics run reverse random soybean soybeans"
			);
		return false;
	}
	// Parse args one by one
	for(String arg: args){
		if(arg.equals("-debug"))
			DEBUG=true;
		else if(arg.equals("-delete"))
			IsDelete=true;
		else if(arg.equals("-plow"))
			IsEmpty=true;
		else if(arg.equals("-pics"))
			Screenshot=true;

		else if(arg.equals("run"))
			RunMode=2;
		else if(arg.equals("reverse"))
			RunMode=5;
		else if(arg.equals("random"))
			RunMode=3;
		else if(arg.equals("soybean"))
			RunMode=6;
		else if(arg.equals("soybeans"))
			RunMode=7;
		else{
			System.out.println("Unrecognized argument: " + arg);
			return false;
		}
	}
	return true;
}

static void constructGrid(){
	coords = new int[FarmSize][FarmSize][];
	int[] center;
	if(IsEmpty)
		center = PlowCenter;
	else center = Center;
	int[] firstSquare = sumTuples(
		center, new int[]{0,
			-(2*getY(DistanceBetweenPlotsAtMaxZoom)
				*(FarmSize/2-1))});

	for(int i=0; i<FarmSize; i++){
		int[] stpos = sumTuples(firstSquare,
		new int[]{
			getX(DistanceBetweenPlotsAtMaxZoom)*-i,
			getY(DistanceBetweenPlotsAtMaxZoom)*i
		});
		for(int j=0; j<FarmSize; j++){
			coords[i][j] = sumTuples(stpos,
			new int[]{
				getX(DistanceBetweenPlotsAtMaxZoom)*j,
				getY(DistanceBetweenPlotsAtMaxZoom)*j
			});
		}
	}
}

// Does final tasks and exits the program. Can only be run once.
static void runFinalize(){
	long finishTime = System.currentTimeMillis();
	System.out.println("Finished " + new Date(finishTime));
	System.out.println("Approx time run: " + ((finishTime-startTime) / 1000) + " seconds.");
	System.exit(0);
}


static void clickAt(int i, int j){
	for(int[] c : DontClick)
		if(getX(c)==i && getY(c)==j) return;
	if(DEBUG)
		System.out.println("Clicked: " + i + " , " + j);
	try{
		if(DEBUG)
			mouseMove(robot, coords[i][j]);
		else
			mouseClick(robot, coords[i][j]);
		Thread.sleep(ClickSleepTime);
		if(IsDelete){
			Thread.sleep(PopupSleepTime);
			if(DEBUG)
				mouseMove(robot, OK);
			else
				mouseClick(robot, OK);
			Thread.sleep(PopupSleepTime);
		}
	}catch(InterruptedException e){
		System.out.println("Sleep failed (?!)");
	}
}

/*
 * Patterns:
 *
 * 1 = Start from the top and work its way down and right through the first
 * row. When it hits the last square, continue from the square left and down
 * from the original square and repeat. Sort of like reading a book left
 * to right.
 *
 * 2 = Start from the top and work its way down and right. When it reaches
 * the end of the first row, do the second row reverse. When it reaches
 * the start of the second row, do the third row normally, and so on.
 *
 * 3 = Click the squares randomly. Will not click on the same square
 * twice.
 *
 * 4 = 1, reverse
 *
 * 5 = 2, reverse
 *
 * 6 = Just farm soybeans. Start with empty square.
 *
 * 7 = Improved soybeans algorithm (copied from Wen Li). Farm an entire
 * row of soybeans.
 *
 */
static void start(){
	if(RunMode==0){
		System.out.println("No run mode specified!");
		return;
	}
	if(RunMode==1){
	//Don't use.
	/*
	for(int i=1; i<size; i++)
		for(int j=0; j<size; j++){
			clickAt(i,j);
		}
	*/
	}
	else if(RunMode==2){
	int p = 0; // 0(at start), 1(at end)
	for(int i=1; i<size; i++){
		if(p==0){
			for(int j=0; j<size; j++){
				clickAt(i,j);
			}
			p=1;
		}
		else if(p==1){
			for(int j=size-1; j>=0; j--){
				clickAt(i,j);
			}
			p=0;
		}
	} } //end RunMode 2
	else if(RunMode==3){
	java.util.List<int[]> list = new ArrayList<int[]>(); 
	for(int i=0; i<size; i++)
		for(int j=0; j<size; j++)
			list.add(new int[]{i,j});
	Collections.shuffle(list, random);
	for(int[] elem : list)
		clickAt(getX(elem), getY(elem));
	} // end RunMode 3
	else if(RunMode==4){
	// Don't use.
	/* 
	for(int i=size-1; i>=1; i--){
		for(int j=size-1; j>=0; j--){
			clickAt(i,j);
		}
	}
	*/
	}
	else if(RunMode==5){
	int p = 1;
	for(int i=size-1; i>=1; i--){
		if(p==0){
			for(int j=0; j<size; j++){
				clickAt(i,j);
			}
			p=1;
		}
		else if(p==1){
			for(int j=size-1; j>=0; j--){
				clickAt(i,j);
			}
			p=0;
		}
	} } // end RunMode 5
	else if(RunMode==6){
	try{ //Try...
	int c=1;
	//Start: Plow
	while(true){

		// If not, c will always remain to be 1.
		if(Screenshot)
			c++;

		//Make a screenshot once in a while.
		BufferedImage im = robot.createScreenCapture(new Rectangle(1280,960));
		if(c%50==0){
			String picstru = new Date(System.currentTimeMillis()).toString();
			String picstr = picstru.replaceAll(":", " ");
			ImageIO.write(im, "jpg", new File("C:/Users/Student/Desktop/fv/" +
					 picstr + ".jpg"));
			System.out.println("Screenshot " + picstru);
		}

		// Every time after we sleep, check if we are still in the game.

		//Start by opening plow window
		mouseClick(robot, Plow);

		//Plow square
		mouseClick(robot, SoybeanPlow);

		//Open market window
		mouseClick(robot, Market);
		Thread.sleep(2200);
		quitIfNotInGame();
		
		//Click 'BUY'
		mouseClick(robot, BuySoybean);

		//Plant soybean
		mouseClick(robot, SoybeanSquare);

		//Delete soybean
		mouseClick(robot, Delete);
		Thread.sleep(1700);
		quitIfNotInGame();

		mouseClick(robot, SoybeanSquare);
		Thread.sleep(1100);
		quitIfNotInGame();

		mouseClick(robot, OK);
	} /*end try*/ }catch(InterruptedException e){
		System.out.println("Sleep failed (?!)");
	}
	catch(IOException e){
		System.out.println("Could not write screen dump");
	} } // end RunMode 6.
	else if(RunMode==7){
	try{
	// Start with an empty row, and plow.
	while(true){

		// Open plow option.
		mouseClick(robot, Plow);

		for(int i=0; i<FarmSize; i++){
			// Plow: offset = 12.
			int[] thissquare = coords[0][i];
			int[] plowsquare = new int[]{thissquare[0], thissquare[1]+12};
			mouseClick(robot, plowsquare);
		}
		Thread.sleep(20000);

		//Open market window
		mouseClick(robot, Market);
		Thread.sleep(2200);

		//Click 'BUY'
		mouseClick(robot, BuySoybean);

		// Plant them.
		for(int i=0; i<FarmSize; i++){
			mouseClick(robot, coords[0][i]);
		}

		// Finally delete them.
		Thread.sleep(3000);
		mouseClick(robot, Delete);
		for(int i=0; i<FarmSize; i++){
			mouseClick(robot, coords[0][i]);
			Thread.sleep(i==0?2000:1000);
			mouseClick(robot, OK);
			Thread.sleep(800);
		}

	}/*end try*/ }catch(InterruptedException e){
		System.out.println("Sleep failed (?!)");
	} } // end RunMode 7.
	else throw new RuntimeException("Unknown pattern:" + RunMode);
}


// ---------------------- UTILITIES SECTION ------------------------------------
// Get X in a tuple.
static int getX(int[] tuple){
	if(tuple.length==2)
		return tuple[0];
	else throw new RuntimeException("Invalid Tuple: getX()");
}

// Get Y in a tuple.
static int getY(int[] tuple){
	if(tuple.length==2)
		return tuple[1];
	else throw new RuntimeException("Invalid Tuple: getY()");
}

// Sum of (two) tuples.
static int[] sumTuples(int[] t1, int[] t2){
	if(t1.length==2 && t2.length==2)
		return new int[]{t1[0]+t2[0], t1[1]+t2[1]};
	else throw new RuntimeException("Invalid Tuple: sumTuples");
}

// Move mouse here.
public static void mouseMove(Robot robot, int[] tuple){
	robot.mouseMove(getX(tuple), getY(tuple));
}

// Move the mouse to a position relative to the first tuple.
public static void mouseMoveRelative(Robot robot, int[] rel, int[] tuple){
	int[] sum = sumTuples(rel, tuple);
	robot.mouseMove(getX(sum), getY(sum));
}

// Click here.
public static void mouseClick(Robot robot, int[] tuple){
	mouseMove(robot, tuple);
	robot.mousePress(InputEvent.BUTTON1_MASK);
	robot.mouseRelease(InputEvent.BUTTON1_MASK);
}

// Click, relatively.
public static void mouseClickRelative(Robot robot, int[] rel, int[] tuple){
	mouseMoveRelative(robot, rel, tuple);
	robot.mousePress(InputEvent.BUTTON1_MASK);
	robot.mouseRelease(InputEvent.BUTTON1_MASK);
}

// Color of pixel on the screen at (x,y).
public static int getColorOfPixel(int x, int y){
	BufferedImage im = robot.createScreenCapture(new Rectangle(1280,960));
	int pix = im.getRGB(x,y);
	return pix;
}

// Whether the pixel matches a given RGB.
public static boolean checkPixel
	(int pix, int pR_, int pG_, int pB_, int threshold){
	int pR = (pix >>> 16) & 0xff;
	int pG = (pix >>> 8) & 0xff;
	int pB = pix & 0xff;
	if(Math.abs(pR-pR_)>5 || Math.abs(pG-pG_)>5 || Math.abs(pB-pB_)>5)
		return false;
	return true;
}

// Checks if we are still in the game by getting the color of the
// top left (0,0) pixel.
public static boolean checkWhetherInGame(){
	int pix = getColorOfPixel(0,0);
	return checkPixel(pix, 0x43, 0xa1, 0x43, 5);
}

// If not still in game, save a screenshot and quit.
public static void quitIfNotInGame()
	throws IOException{
	boolean ingame = checkWhetherInGame();
	if(!ingame){
		//Create screen capture.
		System.out.println("Error: not in farmville.");
		BufferedImage im = robot.createScreenCapture(new Rectangle(1280,960));
		if(Screenshot)
			ImageIO.write(im, "jpg", new File("C:/Users/Student/Desktop/fv/dump.jpg"));
		runFinalize();
	}
}

} // End class.


