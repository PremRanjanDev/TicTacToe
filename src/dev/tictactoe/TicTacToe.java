package dev.tictactoe;

import java.io.PrintStream;
import java.text.MessageFormat;
import java.util.Random;
import java.util.Scanner;

public class TicTacToe {
	static Object[] t3;
	private static int vacant;
	
	public static void main(String[] args) {
		printOut("♦ ♦ ♦ ♦ ♦ ♦ ♦ TicTacToe ♦ ♦ ♦ ♦ ♦ ♦ ♦\n");
		printOut("Instructions:\n");
		printOut("- Hit Enter key twice to exit any moment.\n");
		
		Scanner sn = new Scanner(System.in);
		boolean playing = true;
		
		while (playing) {
			printOut(getT3Matrix());
			printOut("\nEnter X or O to play, press enter to exit : ");
			String playerChar = sn.nextLine().toUpperCase();
			String AIChar;
			
			if (playerChar.isEmpty()) {
				playing = false;
			}else if(!"XO".contains(playerChar)) {
				printErr("Oho!, You entered other than X and O\n");
			}else {
				if("X".equals(playerChar))
					AIChar = "O";
				else
					AIChar = "X";
				
				while (playing) {
					printOut(getT3Matrix());
					printOut("\nEnter Position to mark : ");
					String str = sn.nextLine();
					
					if (str.isEmpty()) {
						playing = false;
					} else {
						int pos = 0;
						try {
							pos = Integer.parseInt(str);
						} catch (NumberFormatException ex) {
							printErr("Oho!, You entered non-numaric. Enter value from 1 to 9\n");
							continue;
						}

						if (pos < 1 || pos > 9) {
							printErr("Oho!, Enter only numaric from 1 to 9\n");
							continue;
						} else if (!t3[pos - 1].equals("" + pos)) {
							printErr("Oho!, This place is already marked with ");
							printOut(t3[pos - 1] + "\n");
						} else {
							mark(pos - 1, playerChar);
							int win = checkWin(playerChar);
							if(win != -1) {
								celebrate(win, true);
								resetT3();
								printOut("♦ ♦ ♦ ♦ ♦ ♦ ♦ TicTacToe ♦ ♦ ♦ ♦ ♦ ♦ ♦\n");
								break;
							}
							mark(getAIPos(), AIChar);
							win = checkWin(AIChar);
							if(win != -1) {
								celebrate(win, false);
								resetT3();
								printOut("♦ ♦ ♦ ♦ ♦ ♦ ♦ TicTacToe ♦ ♦ ♦ ♦ ♦ ♦ ♦\n");
								break;
							}
						}
					}
				}
			}
		}
		sn.close();
		printOut("Thanks....! ");
		printErr("☺");
	}

	private static void celebrate(int win, boolean player) {
		if(win == 0)
			printErr("\n♣ ♣ ♣ ♣ ♣ ♣ ♣ Its a Draw ♣ ♣ ♣ ♣ ♣ ♣ ♣\n");
		if(player)
			printErr("\n♥ ♥ ♥ ♥ ♥ ♥ ♥ You Win ♥ ♥ ♥ ♥ ♥ ♥ ♥\n");
		else
			printErr("\n♠ ♠ ♠ ♠ ♠ ♠ ♠ You Lose ♠ ♠ ♠ ♠ ♠ ♠ ♠\n");
		
		printErr(getT3Matrix() + "\n");
	}

	private static int checkWin(String ch) {
		if (ch.equals(t3[4])) {
			if (ch.equals(t3[3]) && ch.equals(t3[5])) {
				return 1;
			} else if (ch.equals(t3[1]) && ch.equals(t3[7])) {
				return 1;
			} else if (ch.equals(t3[3]) && ch.equals(t3[5])) {
				return 1;
			} else if (ch.equals(t3[0]) && ch.equals(t3[8])) {
				return 1;
			} else if (ch.equals(t3[2]) && ch.equals(t3[6])) {
				return 1;
			}
		} else if (ch.equals(t3[0])) {
			if (ch.equals(t3[1]) && ch.equals(t3[2])) {
				return 1;
			} else if (ch.equals(t3[3]) && ch.equals(t3[6])) {
				return 1;
			}
		} else if (ch.equals(t3[8])) {
			if (ch.equals(t3[2]) && ch.equals(t3[5])) {
				return 1;
			} else if (ch.equals(t3[6]) && ch.equals(t3[7])) {
				return 1;
			}
		}

		if (vacant == 0)
			return 0;
		else
			return -1;
	}

	private static void mark(int pos, String ch) {
		t3[pos] = ch;
		vacant--;
	}
	
	private static void resetT3() {
		Object[] t3Temp = { "1", "2", "3", "4", "5", "6", "7", "8", "9" };
		t3 = t3Temp;
		vacant = 9;
	}
	
	private static int getAIPos() {

		Random rand = new Random();
		while (true) {
			int n = rand.nextInt(8);

			if (t3[n].equals("" + (n + 1)))
				return n;
		}
	}

	/***
	 * to return string in format:
	 * 
	 *   1 | 2 | 3 
	 *  ---+---+--- 
	 *   4 | 5 | 6 
	 *  ---+---+--- 
	 *   7 | 8 | 9
	 *
	 */
	static String getT3Matrix() {
		if(t3 == null)
			resetT3();
		
		String temp =   "\n {0} | {1} | {2} \n" + 
						"---+---+---\n" + 
						" {3} | {4} | {5} \n" + 
						"---+---+---\n" + 
						" {6} | {7} | {8} \n";

		return MessageFormat.format(temp, t3);
	}
	
	static private void printOut(Object obj) {
		PrintStream out = System.out;
		out.print(obj);
		out.flush();
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	static private void printErr(Object obj) {
		PrintStream err = System.err;
		err.print(obj);
		err.flush();
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
