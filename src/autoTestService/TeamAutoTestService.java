package autoTestService;

import java.util.ArrayList;

import test.data.TeamHighInfo;
import test.data.TeamHotInfo;
import test.data.TeamNormalInfo;
import common.mydatastructure.SortCell;

public interface TeamAutoTestService {
	public ArrayList<TeamHotInfo> getTeamHot(int number, String field);

	public ArrayList<TeamHighInfo> getTeamHigh(int number, SortCell[] sortcell);

	public ArrayList<TeamNormalInfo> getTeamNormal_avg(int number, SortCell[] sortcell);

	public ArrayList<TeamNormalInfo> getTeamNormal_tot(int number, SortCell[] sortcell);
}
