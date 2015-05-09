package dataservice.teams;

public interface TeamInfoDataService {
	// 根据球队名称查找所属球队
	public String getLeagueOfTeam(String teamNameForShort);
}
