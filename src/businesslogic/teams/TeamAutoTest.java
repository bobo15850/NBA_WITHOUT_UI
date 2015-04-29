package businesslogic.teams;

import java.util.ArrayList;

import test.data.TeamHighInfo;
import test.data.TeamHotInfo;
import test.data.TeamNormalInfo;
import common.mydatastructure.SortCell;
import autoTestService.TeamAutoTestService;

public class TeamAutoTest implements TeamAutoTestService {

	public ArrayList<TeamHotInfo> getTeamHot(int number, String field) {
		return null;
	}

	public ArrayList<TeamHighInfo> getTeamHigh(int number, SortCell[] sortcell) {
		return null;
	}

	public ArrayList<TeamNormalInfo> getTeamNormal_avg(int number, SortCell[] sortcell) {
		return null;
	}

	public ArrayList<TeamNormalInfo> getTeamNormal_tot(int number, SortCell[] sortcell) {
		return null;
	}
}
