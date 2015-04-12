package businesslogic.teams;

import test.data.TeamHighInfo;
import test.data.TeamHotInfo;
import test.data.TeamNormalInfo;

import common.mydatastructure.SortCell;

import autoTestService.TeamAutoTestService;

public class TeamAutoTest implements TeamAutoTestService {

	@Override
	public TeamHotInfo[] getTeamHot(int number, String field) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TeamHighInfo[] getTeamHigh(int number, SortCell[] sortcells) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TeamNormalInfo[] getTeamNormal_avg(int number, SortCell[] sortcells) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TeamNormalInfo[] getTeamNormal_tot(int number, SortCell[] sortcells) {
		// TODO Auto-generated method stub
		return null;
	}

}
