// Class responsible for handling all details specific to a Player
public class Player {
    private String gamerTag;
    private double mvpScore;
    private double gamesPlayed;
    private double topThreeScore;
    private double numberOfKills;
    private double killDeathRatio;
    private double averageAccuracyScore;
    private double skillWeight;

    public Player(String gamerTag, double mvpScore, double gamesPlayed, double topThreeScore, double numberOfKills, double killDeathRatio, double averageAccuracyScore) {
        this.gamerTag = gamerTag;
        this.mvpScore = mvpScore;
        this.gamesPlayed = gamesPlayed;
        this.topThreeScore = topThreeScore;
        this.numberOfKills = numberOfKills;
        this.killDeathRatio = killDeathRatio;
        this.averageAccuracyScore = averageAccuracyScore;
        calculateSkillWeight();
    }

    public Player() {
        gamerTag = "";
        mvpScore = 0.0;
        gamesPlayed = 0.0;
        topThreeScore = 0.0;
        numberOfKills = 0.0;
        killDeathRatio = 0.0;
        averageAccuracyScore = 0.0;
        skillWeight = 0.0;
    }

    private void calculateSkillWeight() {
        skillWeight =((((numberOfKills/killDeathRatio)+numberOfKills)*((mvpScore+topThreeScore)/gamesPlayed))
                -(((gamesPlayed*averageAccuracyScore)+(numberOfKills*averageAccuracyScore)
                +(gamesPlayed*killDeathRatio))*(killDeathRatio*averageAccuracyScore)))
                /(((averageAccuracyScore*mvpScore)+(averageAccuracyScore*topThreeScore))
                *((numberOfKills/gamesPlayed)-((topThreeScore/gamesPlayed)
                *(mvpScore%averageAccuracyScore)*(mvpScore%killDeathRatio)
                *(mvpScore/gamesPlayed))))*(killDeathRatio/averageAccuracyScore);
    }

    public String getGamerTag() {
        return gamerTag;
    }

    public void setGamerTag(String gamerTag) {
        this.gamerTag = gamerTag;
    }

    public double getMvpScore() {
        return mvpScore;
    }

    public void setMvpScore(int mvpScore) {
        this.mvpScore = mvpScore;
    }

    public double getGamesPlayed() {
        return gamesPlayed;
    }

    public void setGamesPlayed(int gamesPlayed) {
        this.gamesPlayed = gamesPlayed;
    }

    public double getTopThreeScore() {
        return topThreeScore;
    }

    public void setTopThreeScore(int topThreeScore) {
        this.topThreeScore = topThreeScore;
    }

    public double getNumberOfKills() {
        return numberOfKills;
    }

    public void setNumberOfKills(int numberOfKills) {
        this.numberOfKills = numberOfKills;
    }

    public double getKillDeathRatio() {
        return killDeathRatio;
    }

    public void setKillDeathRatio(double killDeathRatio) {
        this.killDeathRatio = killDeathRatio;
    }

    public double getAverageAccuracyScore() {
        return averageAccuracyScore;
    }

    public void setAverageAccuracyScore(double averageAccuracyScore) {
        this.averageAccuracyScore = averageAccuracyScore;
    }

    public double getSkillWeight() {
        return skillWeight;
    }

    public void setSkillWeight(double skillWeight) {
        this.skillWeight = skillWeight;
    }
}
