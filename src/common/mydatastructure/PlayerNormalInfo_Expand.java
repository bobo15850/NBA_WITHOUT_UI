package common.mydatastructure;

import businesslogic.players.CalculationOfPlayerPerform;
import test.data.PlayerNormalInfo;

public class PlayerNormalInfo_Expand extends PlayerNormalInfo {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private double totalHit;// 总命中数
	private double totalShot;// 总出手数
	private double threeHit;// 三分命中数
	private double threeShot;// 三分出手数
	private double freeHit;// 罚球命中数
	private double freeShot;// 罚球出手数
	private int doubleTwo;// 两双数
	private int tripleTwo;// 三双数
	// 以下为本队球队数据
	private double teamMinute = 0;// 球队所有球员上场时间
	private double teamTotalRebound = 0;// 球队所有篮板数
	private double teamOffendRebound = 0;
	private double teamDefendRebound = 0;
	private double teamTotalHit = 0; // 球队所有命中数
	private double teamTotalshot = 0;// 球队所有出手数
	private double teamFreeShot = 0;// 球队所有罚球数
	private double teamFault = 0;// 球队所有失误数
	// 以下为对手球队数据
	private double oppOffendRound = 0;// 对手进攻回合数
	private double oppTotalShot = 0;// 对手总出手数
	private double oppThreeShot = 0;// 对手三分出手数
	private double oppRebound = 0;// 对手所有篮板数
	private double oppOffendRebound = 0;// 对手进攻篮板数
	private double oppDefendRebound = 0;// 对手防守篮板数

	public int getDoubleTwo() {
		return doubleTwo;
	}

	public void setDoubleTwo(int doubleTwo) {
		this.doubleTwo = doubleTwo;
	}

	public int getTripleTwo() {
		return tripleTwo;
	}

	public void setTripleTwo(int tripleTwo) {
		this.tripleTwo = tripleTwo;
	}

	public double getTotalHit() {
		return totalHit;
	}

	public void setTotalHit(double totalHit) {
		this.totalHit = totalHit;
	}

	public double getTotalShot() {
		return totalShot;
	}

	public void setTotalShot(double totalShot) {
		this.totalShot = totalShot;
	}

	public double getThreeHit() {
		return threeHit;
	}

	public void setThreeHit(double threeHit) {
		this.threeHit = threeHit;
	}

	public double getThreeShot() {
		return threeShot;
	}

	public void setThreeShot(double threeShot) {
		this.threeShot = threeShot;
	}

	public double getFreeHit() {
		return freeHit;
	}

	public void setFreeHit(double freeHit) {
		this.freeHit = freeHit;
	}

	public double getFreeShot() {
		return freeShot;
	}

	public void setFreeShot(double freehot) {
		this.freeShot = freehot;
	}

	public double getOppTotalShot() {
		return oppTotalShot;
	}

	public void setOppTotalShot(double oppTotalshot) {
		this.oppTotalShot = oppTotalshot;
	}

	public double getOppThreeShot() {
		return oppThreeShot;
	}

	public void setOppThreeShot(double oppThreeShot) {
		this.oppThreeShot = oppThreeShot;
	}

	public double getOppRebound() {
		return oppRebound;
	}

	public void setOppRebound(double oppRebound) {
		this.oppRebound = oppRebound;
	}

	public double getOppOffendRebound() {
		return oppOffendRebound;
	}

	public void setOppOffendRebound(double oppOffendRebound) {
		this.oppOffendRebound = oppOffendRebound;
	}

	public double getOppDefendRebound() {
		return oppDefendRebound;
	}

	public void setOppDefendRebound(double oppDefendRebound) {
		this.oppDefendRebound = oppDefendRebound;
	}

	public PlayerNormalInfo_Expand getPlayerNormal_avg() {
		PlayerNormalInfo_Expand playerNormal_avg = new PlayerNormalInfo_Expand();
		double numOfGame = getNumOfGame();
		playerNormal_avg.setAge(getAge());
		playerNormal_avg.setAssist(CalculationOfPlayerPerform.cutTail(getAssist() / numOfGame));
		playerNormal_avg.setBlockShot(CalculationOfPlayerPerform.cutTail(getBlockShot() / numOfGame));
		playerNormal_avg.setDefend(CalculationOfPlayerPerform.cutTail(getDefend() / numOfGame));
		playerNormal_avg.setDoubleTwo(doubleTwo);
		playerNormal_avg.setEfficiency(getEfficiency());
		playerNormal_avg.setFault(CalculationOfPlayerPerform.cutTail(getFault() / numOfGame));
		playerNormal_avg.setFoul(CalculationOfPlayerPerform.cutTail(getFoul() / numOfGame));
		playerNormal_avg.setFreeHit(CalculationOfPlayerPerform.cutTail(freeHit / numOfGame));
		playerNormal_avg.setFreeShot(CalculationOfPlayerPerform.cutTail(freeShot / numOfGame));
		playerNormal_avg.setMinute(CalculationOfPlayerPerform.cutTail(getMinute() / numOfGame));
		playerNormal_avg.setName(getName());
		playerNormal_avg.setNumOfGame(getNumOfGame());
		playerNormal_avg.setOffend(CalculationOfPlayerPerform.cutTail(getOffend() / numOfGame));
		playerNormal_avg.setPenalty(getPenalty());
		playerNormal_avg.setPoint(CalculationOfPlayerPerform.cutTail(getPoint() / numOfGame));
		playerNormal_avg.setRebound(CalculationOfPlayerPerform.cutTail(getRebound() / numOfGame));
		playerNormal_avg.setShot(getShot());
		playerNormal_avg.setStart(getStart());
		playerNormal_avg.setSteal(CalculationOfPlayerPerform.cutTail(getSteal() / numOfGame));
		playerNormal_avg.setTeamName(getTeamName());
		playerNormal_avg.setThree(getThree());
		//
		playerNormal_avg.setThreeHit(CalculationOfPlayerPerform.cutTail(threeHit / numOfGame));
		playerNormal_avg.setThreeShot(CalculationOfPlayerPerform.cutTail(threeShot / numOfGame));
		playerNormal_avg.setTotalHit(CalculationOfPlayerPerform.cutTail(totalHit / numOfGame));
		playerNormal_avg.setTotalShot(CalculationOfPlayerPerform.cutTail(totalShot / numOfGame));
		playerNormal_avg.setTripleTwo(tripleTwo);
		//
		playerNormal_avg.setOppTotalShot(oppTotalShot / numOfGame);
		playerNormal_avg.setOppThreeShot(oppThreeShot / numOfGame);
		playerNormal_avg.setOppRebound(oppRebound / numOfGame);
		playerNormal_avg.setOppOffendRebound(oppOffendRebound / numOfGame);
		playerNormal_avg.setOppDefendRebound(oppDefendRebound / numOfGame);
		return playerNormal_avg;
	}

	public double getTeamMinute() {
		return teamMinute;
	}

	public void setTeamMinute(double teamMinute) {
		this.teamMinute = teamMinute;
	}

	public double getTeamTotalRebound() {
		return teamTotalRebound;
	}

	public void setTeamTotalRebound(double teamTotalRebound) {
		this.teamTotalRebound = teamTotalRebound;
	}

	public double getTeamOffendRebound() {
		return teamOffendRebound;
	}

	public void setTeamOffendRebound(double teamOffendRebound) {
		this.teamOffendRebound = teamOffendRebound;
	}

	public double getTeamDefendRebound() {
		return teamDefendRebound;
	}

	public void setTeamDefendRebound(double teamDefendRebound) {
		this.teamDefendRebound = teamDefendRebound;
	}

	public double getTeamTotalHit() {
		return teamTotalHit;
	}

	public void setTeamTotalHit(double teamTotalHit) {
		this.teamTotalHit = teamTotalHit;
	}

	public double getTeamTotalShot() {
		return teamTotalshot;
	}

	public void setTeamTotalshot(double teamTotalshot) {
		this.teamTotalshot = teamTotalshot;
	}

	public double getTeamFreeShot() {
		return teamFreeShot;
	}

	public void setTeamFreeShot(double teamFreeShot) {
		this.teamFreeShot = teamFreeShot;
	}

	public double getTeamFault() {
		return teamFault;
	}

	public void setTeamFault(double teamFault) {
		this.teamFault = teamFault;
	}

	public double getOppOffendRound() {
		return oppOffendRound;
	}

	public void setOppOffendRound(double oppOffendRound) {
		this.oppOffendRound = oppOffendRound;
	}
}
