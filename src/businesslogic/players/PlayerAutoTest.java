package businesslogic.players;

import common.mydatastructure.Filter;
import common.mydatastructure.SortCell;

import test.data.PlayerHighInfo;
import test.data.PlayerHotInfo;
import test.data.PlayerKingInfo;
import test.data.PlayerNormalInfo;
import autoTestService.PlayerAutoTestService;

public class PlayerAutoTest implements PlayerAutoTestService {

	public PlayerHotInfo[] getPlayerHot(int number, String field) {
		return null;
	}

	public PlayerKingInfo[] getPlayerKingOfSeason(int number, String field) {
		return null;
	}

	public PlayerKingInfo[] getPlayerKingOfDaily(int number, String field) {
		return null;
	}

	public PlayerHighInfo[] getPlayerHigh(int number, SortCell[] sortcells) {
		return null;
	}

	public PlayerNormalInfo[] getPlayerNormal_avg(int number, Filter filter, SortCell[] sortcells) {
		return null;
	}

	public PlayerNormalInfo[] getPlayerNormal_tot(int number, Filter filter, SortCell[] sortcells) {
		return null;
	}

}
