package databaseutility;

public class OneMatch_add extends OneMatch_init {
	private boolean isPlayerNormalRefresh = false;
	private boolean isTeamNormalRefresh = false;

	public OneMatch_add(String matchName) {
		super(matchName);
	}

	public void writePlayerNormalInfoToCACHE() {
		isPlayerNormalRefresh = true;
	}

	public void writeTeamNormalInfoToCACHE() {
		isTeamNormalRefresh = true;
	}

	public void writePlayerHighInfoToCACHE() {
		if (isPlayerNormalRefresh && isTeamNormalRefresh) {

		}
	}

	public void writeTeamHighInfoToCACHE() {
		if (isTeamNormalRefresh) {

		}

	}
}
