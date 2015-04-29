package start;

import java.util.Map.Entry;

import test.data.TeamHighInfo;
import businesslogic.CACHE;

public class Main {
	public static void main(String args[]) {
		Terminal console = new Terminal();
		console.excute(System.out, new String[] { "-player", "-king", "point", "-season", "-n", "3" });
		new Refresh().start();

		// for (Entry<String, PlayerHighInfo> temp :
		// CACHE.PLAYER_HIGH.entrySet()) {
		// System.out.println(temp.getValue().toString());
		// }

		// for (Entry<String, PlayerNormalInfo> temp :
		// CACHE.PLAYER_NORMAL.entrySet()) {
		// System.out.println(temp.getValue().toString());
		// }

		// for (Entry<String, TeamNormalInfo> temp :
		// CACHE.TEAM_NORMAL.entrySet()) {
		// System.out.println(temp.getValue().toString());
		// }

		// for (Entry<String, TeamHighInfo> temp : CACHE.TEAM_HIGH.entrySet()) {
		// System.out.println(temp.getValue().toString());
		// }
		// new Refresh().start();
	}
}
