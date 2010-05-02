/*
Commands:
x: exit
s x: start game as player x
e: end game
i: info
v x y: make a move at (x,y)
d: dump board position
*/

import java.util.*;
import java.io.Console;

public class TicTac{

	public static void main(String[] args){
		System.out.println("Tic Tac Toe AI :D\n");
		ioloop();
	}

	static void ioloop(){
		Console console = System.console();
		while(true){
			System.out.print("> ");
			String line = console.readLine();

			// 0: OK, 1: exit, -1: error
			int ret = runcmd(line);
			
			if(ret==1) break;
			if(ret==-1) throw new RuntimeException();
		}
	}

	static Game curGame = null;
	static TicTacAI ai = null;

	static int runcmd(String cmd){

		// commands should be strings seperated by spaces.
		String[] cmds = cmd.split(" ");
		String base = cmds[0];

		if(base.equals("x")) return 1;
		
		// No input?
		else if(base.equals("")) return 0;

		else if(base.equals("s")){
			if(cmds.length != 2){
				System.out.println("Invalid command.");
				return 0;
			}

			if(curGame == null){
				curGame = new Game();
				int pl = Integer.parseInt(cmds[1]);

				// AI is the opposite player as the human.
				ai = new TicTacAI(curGame, 3-pl);

				if(3-pl == 1)
					ai.makeMove();
			}
			else System.out.println("Game is already in progress.");
		}

		else if(base.equals("e")){
			if(curGame != null){
				curGame.end();
				curGame = null;
			}
			else System.out.println("No game to end.");
		}

		else if(base.equals("i")){
			System.out.println("Game in progress: " + (curGame != null));
			if(curGame != null){
				System.out.println("Turn: " + curGame.turn);
				System.out.println("AI is player: " + ai.pln);
			}
		}

		else if(base.equals("v")){
			// Do arguments checking.
			if(curGame == null){
				System.out.println("Game not started!");
				return 0;
			}
			
			if(cmds.length != 3){
				System.out.println("Invalid command.");
				return 0;
			}

			int cx = Integer.parseInt(cmds[1]);
			int cy = Integer.parseInt(cmds[2]);

			if(cx<1 || cy<1 || cx>3 || cy>3){
				System.out.println("Bad move.");
				return 0;
			}

			int crds = 10*cx + cy;
			int r = curGame.move(crds);
			if(r!=0){
				System.out.println("Bad move.");
				return 0;
			}

			int win = s_checkwin();
			if(win == 0 && ai != null){
				ai.makeMove();
				s_checkwin();
			}
		}

		else if(base.equals("d")){
			if(curGame == null){
				System.out.println("Game not started!");
				return 0;
			}

			System.out.println(curGame.dump());
		}

		else{
			System.out.println("Invalid command.");
		}

		return 0;
	}

	static int s_checkwin(){
		int win = curGame.checkwin();
		if(win==3){
			System.out.println("Game drawn.");
		}
		else if(win>0){
			System.out.printf("Player %d wins!\n", win);
		}

		if(win>0){
			System.out.println(curGame.dump());
			curGame.end();
			curGame = null;
		}
		return win;
	}
}

/*
Game is played with 2 players:
Player 1: X
Player 2: O
*/
class Game{
	
	// Board position is board[x][y].
	// empty: 0
	// player 1: 1
	// player 2: 2
	int[][] board;

	// Whose turn is it?
	// This is the player who will make the next move.
	int turn;

	Game(){
		board = new int[3][3];
		for(int i=0; i<3; i++) for(int j=0; j<3; j++) board[i][j] = 0;
		turn = 1;
	}

	void end(){
	}

	// The coords is stored as a base-10 integer. The tens digit is the X position
	// while the ones digit is the Y position.
	// Thus, the board looks like this:
	// 1 2 3
	// 4 5 6
	// 7 8 9

	// The return code is 0 if it's valid, 1 if it's not.
	int put(int player, int coord){
		int cx = coord / 10;
		int cy = coord % 10;

		//off by one.
		cx--; cy--;
		
		// Now we put it, if applicable.
		int piece = board[cx][cy];

		// already a piece here.
		if(piece != 0) return 1;
		board[cx][cy] = player;

		return 0;
	}

	int move(int coord){

		int c = put(turn, coord);
		if(c!=0) return c;

		System.out.printf("%d: %d %d\n", turn, coord/10, coord%10);

		// Make it the other player's turn.
		// Don't switch players if the move is invalid, of course.
		if(turn==1) turn=2;
		else if(turn==2) turn=1;

		return c;
	}

	// I made this method static because it's so useful, and not limited
	// to this class.
	int checkwin(){
		return g_status(board);
	}

	String dump(){
		// string representation of the board.
		String[][] b_st = new String[3][3];
		for(int i=0; i<3; i++) for(int j=0; j<3; j++)
			if(board[i][j]==0) b_st[i][j]=" ";
			else if(board[i][j]==1) b_st[i][j]="X";
			else if(board[i][j]==2) b_st[i][j]="O";

		String ret = String.format(
			" %s | %s | %s \n---|---|---\n %s | %s | %s \n---|---|---\n %s | %s | %s ",
			b_st[0][0],b_st[1][0],b_st[2][0],b_st[0][1],b_st[1][1],b_st[2][1],
			b_st[0][2],b_st[1][2],b_st[2][2]);
		return ret;
	}

	// Checks if anybody has won (by placing 3 in a row).
	// Returns player who has won, or 0 if nobody has won.
	// 3 if it's a draw.
	static int g_status(int[][] board){
		if(board[0][0]==1 && board[0][1]==1 && board[0][2]==1) return 1;
		if(board[1][0]==1 && board[1][1]==1 && board[1][2]==1) return 1;
		if(board[2][0]==1 && board[2][1]==1 && board[2][2]==1) return 1;
		if(board[0][0]==1 && board[1][0]==1 && board[2][0]==1) return 1;
		if(board[0][1]==1 && board[1][1]==1 && board[2][1]==1) return 1;
		if(board[0][2]==1 && board[1][2]==1 && board[2][2]==1) return 1;
		if(board[0][0]==1 && board[1][1]==1 && board[2][2]==1) return 1;
		if(board[0][2]==1 && board[1][1]==1 && board[2][0]==1) return 1;

		if(board[0][0]==2 && board[0][1]==2 && board[0][2]==2) return 2;
		if(board[1][0]==2 && board[1][1]==2 && board[1][2]==2) return 2;
		if(board[2][0]==2 && board[2][1]==2 && board[2][2]==2) return 2;
		if(board[0][0]==2 && board[1][0]==2 && board[2][0]==2) return 2;
		if(board[0][1]==2 && board[1][1]==2 && board[2][1]==2) return 2;
		if(board[0][2]==2 && board[1][2]==2 && board[2][2]==2) return 2;
		if(board[0][0]==2 && board[1][1]==2 && board[2][2]==2) return 2;
		if(board[0][2]==2 && board[1][1]==2 && board[2][0]==2) return 2;

		boolean draw = true;
		for(int i=0; i<3; i++) for(int j=0; j<3; j++)
			if(board[i][j]==0) draw=false;

		if(draw) return 3;

		return 0;
	}

}

/*
AI that plays the game.
*/
class TicTacAI{
	Game game;
	Random rnd;
	int pln;

	// Second argument: Which player is the AI?
	TicTacAI(Game game, int pln){
		this.game = game;
		this.pln = pln;
		rnd = new Random();
	}

	/*
	Call this when you want the AI to make a move, because it doesn't
	know by itself when you have made a move.
	*/
	void makeMove(){
		List<int[][]> nextmoves = nextMoves(game.board, pln, rnd);
		int[] scores = new int[nextmoves.size()];

		for(int i=0; i<scores.length; i++)
			scores[i] = eval(nextmoves.get(i), 3-pln);

		// Find the one with the best score.
		// If multiple entries have the same value, then it picks the first one.
		// That's ok, because the list is already shuffled.
		int max = Integer.MIN_VALUE;
		int maxp = 0;
		for(int i=0; i<scores.length; i++){
			if(scores[i] > max){
				max = scores[i];
				maxp = i;
			}
		}

		int[][] bboard = nextmoves.get(maxp);
		//now we have to find out where exactly did we place lol.

		int cx=0, cy=0;
		for(int i=0; i<3; i++) for(int j=0; j<3; j++){
			if(bboard[i][j] != game.board[i][j]){
				cx = i+1; cy = j+1;
			}
		}

		game.move(10*cx + cy);
	}

	static class Position{
		int[][] pos;
		int turn;

		Position(int[][] pos, int turn){
			this.pos=pos; this.turn=turn;
		}

		public int hashCode(){
			return Arrays.deepHashCode(pos) + turn;
		}

		public boolean equals(Object o){
			Position b = (Position) o;
			return Arrays.deepEquals(pos,b.pos) && turn==b.turn;
		}
	}

	// The transposition table maps from the hash code of an array
	// to the score of that position.
	Map<Position,Integer> transtable = new HashMap<Position,Integer>();

	// Returns a more positive result for winning positions, and
	// a more negative result for losing positions.
	int eval(int[][] pos, int turn){
		int sc;
		Position hashpos = new Position(pos, turn);

		if(transtable.containsKey(hashpos)){
			sc = transtable.get(hashpos);
		}

		else{
			int status = Game.g_status(pos);
			//draw
			if(status == 3) sc = 0;
			//we win
			else if(status == pln) sc = 1;
			//we lose
			else if(status == (3-pln)) sc = -1;

			else{

				// if we got here, the game is not yet over.
				List<int[][]> nexts = nextMoves(pos, turn, rnd);
				int[] scores = new int[nexts.size()];

				for(int i=0; i<scores.length; i++)
					scores[i] = eval(nexts.get(i), 3-turn);

				// Two cases: it's our move, or it's the opponent's move.
				if(turn == pln){
					// it's our move: the score should be the maximum score of the next positions.
					int ret = Integer.MIN_VALUE;
					for(int i=0; i<scores.length; i++)
						if(scores[i]>ret)
							ret = scores[i];
					sc = ret;
				}
				else{
					// it's the other player's move, so the score should be the minimum
					// score of these positions.
					int ret = Integer.MAX_VALUE;
					for(int i=0; i<scores.length; i++)
						if(scores[i]<ret)
							ret = scores[i];
					sc = ret;
				}
			}

			transtable.put(hashpos, sc);
		}

		return sc;
	}

	// Get a list of all possible continuations from this board.
	static List<int[][]> nextMoves(int[][] bd, int turn, Random rnd){
		List<int[][]> moves = new ArrayList<int[][]>();

		for(int i=0; i<3; i++) for(int j=0; j<3; j++){
			if(bd[i][j]!=0) continue;

			// copy the board.
			int[][] newboard = new int[3][3];
			for(int a=0; a<3; a++) for(int b=0; b<3; b++)
				newboard[a][b] = bd[a][b];

			newboard[i][j] = turn;
			moves.add(newboard);
		}

		Collections.shuffle(moves, rnd);
		return moves;
	}
}
