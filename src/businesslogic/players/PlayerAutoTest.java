package businesslogic.players;

import java.util.ArrayList;

import test.data.PlayerHighInfo;
import test.data.PlayerHotInfo;
import test.data.PlayerKingInfo;
import test.data.PlayerNormalInfo;
import common.mydatastructure.Filter;
import common.mydatastructure.SortCell;
import autoTestService.PlayerAutoTestService;

public class PlayerAutoTest implements PlayerAutoTestService {

	public ArrayList<PlayerHotInfo> getPlayerHot(int number, String field) {
		return null;
	}

	public ArrayList<PlayerKingInfo> getPlayerKingOfSeason(int number, String field) {
		return null;
	}

	public ArrayList<PlayerKingInfo> getPlayerKingOfDaily(int number, String field) {
		return null;
	}

	public ArrayList<PlayerHighInfo> getPlayerHigh(int number, SortCell[] sortcells) {
		return null;
	}

	public ArrayList<PlayerNormalInfo> getPlayerNormal_avg(int number, Filter filter, SortCell[] sortcells) {
		return null;
	}

	public ArrayList<PlayerNormalInfo> getPlayerNormal_tot(int number, Filter filter, SortCell[] sortcells) {
		return null;
	}

}
