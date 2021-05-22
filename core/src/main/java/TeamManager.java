import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;
import java.util.List;

// Class manager that is responsible for knowing details specific to the teams as a collective
// and handling interactions with those teams, including the optimization and balancing
public class TeamManager {
    private LinkedList<Team> teams;

    private double totalSumTeamValue;
    private double averageTeamValue;
    private double overAverageLargestGapDifference;
    private double underAverageLargestGapDifference;

    private Team largestOverTeam;
    private Team largestUnderTeam;
    private Player optimalPlayerOver;
    private Player optimalPlayerUnder;

    private double doWhileThesholdBreak;

    private SizeRemClass src = new SizeRemClass();

    public TeamManager() {
        teams = new LinkedList<>();

        this.totalSumTeamValue = 0.0;
        this.averageTeamValue = 0.0;
        this.overAverageLargestGapDifference = 0.0;
        this.underAverageLargestGapDifference = 0.0;

        this.largestOverTeam = null;
        this.largestUnderTeam = null;
        this.optimalPlayerOver = null;
        this.optimalPlayerUnder = null;

        this.doWhileThesholdBreak = 0.0;
    }

    private static class SizeRemClass{
        private int maxTeamSize;
        private int remainder;
        private int numberOfTeams;

        public SizeRemClass() {
            this.maxTeamSize = 5;
            this.remainder = 0;
            this.numberOfTeams = 0;
        }

        public int getNumberOfTeams() {
            return numberOfTeams;
        }

        public void setNumberOfTeams(int numberOfTeams) {
            this.numberOfTeams = numberOfTeams;
        }

        public int getMaxTeamSize() {
            return maxTeamSize;
        }

        public void setMaxTeamSize(int maxTeamSize) {
            this.maxTeamSize = maxTeamSize;
        }

        public int getRemainder() {
            return remainder;
        }

        public void setRemainder(int remainder) {
            this.remainder = remainder;
        }
    }

    public int getNumberOfTeams(){
        return src.getNumberOfTeams();
    }

    public void optimizeTeams(){
        int iterations = 0;
        try{
            do {
                resetTeamsPlayersGaps();

                System.out.println("Iteration #" + ++iterations);

                findLargestUnderTeam();
                findOptimalPlayers();

//                System.out.println("FINAL-OVER\nPlayer: " + optimalPlayerOver.getGamerTag() + "|Skill Weight: " + optimalPlayerOver.getSkillWeight() + "|Average Proximity: " + (Math.abs(underAverageLargestGapDifference-optimalPlayerOver.getSkillWeight())));
//                System.out.println("Largest Over Team: " + overAverageLargestGapDifference);
//                largestOverTeam.displayTeam();
//                System.out.println("FINAL-UNDER\nPlayer: " + optimalPlayerUnder.getGamerTag() + "|Skill Weight: " + optimalPlayerUnder.getSkillWeight() + "|Average Proximity: " + (Math.abs(overAverageLargestGapDifference-optimalPlayerUnder.getSkillWeight())));
//                System.out.println("Largest Under Team: " + underAverageLargestGapDifference);
//                largestUnderTeam.displayTeam();

                swapPlayers();

//                System.out.println("\nNew Teams: ");
//                System.out.println("Over Team: " + calculateTeamGap(largestOverTeam));
//                largestOverTeam.displayTeam();
//                System.out.println(averageTeamValue + "\n");
//                System.out.println("Under Team: " + calculateTeamGap(largestUnderTeam));
//                largestUnderTeam.displayTeam();
//                System.out.println("======================================================================================================================\n\n\n");

                // Need to figure out how to properly break out of while loop
                // ultimately, things to look for are if when the over or under passes to the other side of the average
                // need to pay attention to the team values against the average
                // and see what the output is for how close the teams manage to get
                // run a control to get the first 50 iterations
                if (Math.abs(averageTeamValue - largestOverTeam.getTeamValue()) < doWhileThesholdBreak) {
                    largestOverTeam.setBalanced(true);
                }

                if (Math.abs(averageTeamValue - largestUnderTeam.getTeamValue()) < doWhileThesholdBreak) {
                    largestUnderTeam.setBalanced(true);
                }

//        } while((overAverageLargestGapDifference > doWhileThesholdBreak) && (underAverageLargestGapDifference < doWhileThesholdBreak));
//        } while(!(largestOverTeam.isBalanced()) || !(largestUnderTeam.isBalanced()));
            } while(iterations < (getNumberOfTeams()*2));
        } catch (NullPointerException npe){
            System.out.println(npe);
        }

        System.out.println("Total Num Iterations: " + iterations);
    }

    private double calculateTeamGap(Team targetTeam){
        return Math.abs(averageTeamValue - targetTeam.getTeamValue());
    }

    private void resetTeamsPlayersGaps(){
        optimalPlayerOver = null;
        optimalPlayerUnder = null;
        largestOverTeam = null;
        largestUnderTeam = null;
        overAverageLargestGapDifference = 0.0;
        underAverageLargestGapDifference = 0.0;
    }

    private void swapPlayers(){
        largestOverTeam.swapPlayers(optimalPlayerOver, optimalPlayerUnder);
        largestUnderTeam.swapPlayers(optimalPlayerUnder, optimalPlayerOver);
    }

    private static class OptimalPlayerStats{
        Player playerOver;
        Player playerUnder;
        double targetFixValue;

        public OptimalPlayerStats() {
            this.playerOver = null;
            this.playerUnder = null;
            this.targetFixValue = 0.0;
        }
    }

    private void findOptimalPlayers(){
        OptimalPlayerStats pStats = new OptimalPlayerStats();

        // find the lowest player in the under team
        for(Player player: largestUnderTeam.getTeam()){
            if(pStats.playerUnder == null){
                pStats.playerUnder = player;
                continue;
            }
            double playerUnderGap = Math.abs(underAverageLargestGapDifference - pStats.playerUnder.getSkillWeight());
            double playerGap = Math.abs(underAverageLargestGapDifference - player.getSkillWeight());

            if(playerGap > playerUnderGap){
                pStats.playerUnder = player;
            }
        }
        pStats.targetFixValue = underAverageLargestGapDifference + pStats.playerUnder.getSkillWeight();
        // add underAverageLargestGapDifference and the player's skill weight to get the targetFixValue
        // iterate through all teams that are over (i.e. their overUnderBool is true)
        for(Team team: teams){
            if(team.isBalanced() || team == largestUnderTeam){
                continue;
            }
            for(Player player: team.getTeam()){
                if(pStats.playerOver == null){
                    pStats.playerOver = player;
                    continue;
                }
                double playerOverGapToTarget = Math.abs(pStats.playerOver.getSkillWeight() - pStats.targetFixValue);
                double playerGapToTarget = Math.abs(player.getSkillWeight() - pStats.targetFixValue);

                if(playerGapToTarget < playerOverGapToTarget){
                    pStats.playerOver = player;
                    largestOverTeam = team;
                    overAverageLargestGapDifference = Math.abs(largestOverTeam.getTeamValue() - averageTeamValue);
                }
            }
        }
        // and find the player that is the closest to the targetFixValue
        optimalPlayerOver = pStats.playerOver;
        optimalPlayerUnder = pStats.playerUnder;
    }


    private void findLargestUnderTeam(){
        for(Team team: teams){
            if(team.isBalanced()){
                continue;
            }
            if(largestUnderTeam == null) {
                largestUnderTeam = team;
                underAverageLargestGapDifference = calculateTeamGap(largestUnderTeam);
                continue;
            }
            largestUnderTeam = (team.getTeamValue() < largestUnderTeam.getTeamValue()) ? team : largestUnderTeam;
            underAverageLargestGapDifference = calculateTeamGap(largestUnderTeam);
        }
    }

    public void formTeams(List<Player> players){
        if(!players.isEmpty()){
            calculateNumOfTeams(players);
            int numTeamsNeeded = src.getNumberOfTeams();
            System.out.println("Number of teams needed: " + numTeamsNeeded);
            if(numTeamsNeeded < 0){
                return;
            }

            initializePreTeams(numTeamsNeeded);
            int teamIndex = 0;

            for(Player player: players){
                if(teams.get(teamIndex).addPlayer(player, src.getMaxTeamSize())){
                    teamIndex++;
                }

                if(teamIndex >= numTeamsNeeded){
                    teamIndex = 0;
                }
            }

            calculateTotalSumTeamValue();
        }
    }

    public void displayTeams(){
        for(Team team: teams){
            team.displayTeam();
        }
    }

    private void initializePreTeams(int numTeams){
        for(int i = 0; i < numTeams; i++){
            teams.add(new Team(("Team #" + (i+1)), 0, 0));
        }
    }

    private void calculateNumOfTeams(@NotNull List<Player> players){
        if(players.isEmpty()){
            src.setMaxTeamSize(-1);
        }

        int numberOfPlayers = players.size();

        if(numberOfPlayers < 8){
            src.setMaxTeamSize(1);
        } else if(numberOfPlayers < 20){
            src.setMaxTeamSize(4);
            if((numberOfPlayers % 4 == 2) || (numberOfPlayers % 4 == 1)){
                src.setMaxTeamSize(3);
                if(numberOfPlayers % 3 != 0){
                    src.setMaxTeamSize(2);
                }
            }
            if((numberOfPlayers % 3 == 0) && (numberOfPlayers % 4 != 0) && (numberOfPlayers % 4 != 3)){
                src.setMaxTeamSize(3);
            }
            if(numberOfPlayers % 4 == 3){
                src.setRemainder(numberOfPlayers % 4);
            }
        } else{
            if(numberOfPlayers % 5 == 0){
                src.setMaxTeamSize(5);
            } else if(numberOfPlayers % 5 == 4){
                src.setRemainder(numberOfPlayers % 5);
                if(numberOfPlayers % 4 == 0){
                    src.setMaxTeamSize(4);
                    src.setRemainder(0);
                }

            } else{
                if((numberOfPlayers % 4 == 0) || (numberOfPlayers % 4 == 3)){
                    src.setMaxTeamSize(4);
                    src.setRemainder(numberOfPlayers % 4);
                } else{
                    src.setMaxTeamSize(3);
                    if(numberOfPlayers % 3 != 0){
                        src.setMaxTeamSize(2);
                    }
                }
            }
        }

        src.setNumberOfTeams(numberOfPlayers/src.getMaxTeamSize());

    }

    public double getTotalSumTeamValue() {
        return totalSumTeamValue;
    }

    private void calculateTotalSumTeamValue() {
        if(!teams.isEmpty()){
            // iterate through each team and extract individual player sum values
            // and add to the total
            for(Team team: teams){
                for(Player player: team.getTeam()){
                    totalSumTeamValue += player.getSkillWeight();
                }
            }
            calculateAverageTeamValue();
            calculateBalanceThreshold();
        }
    }

    private void calculateAverageTeamValue(){
        averageTeamValue = totalSumTeamValue/src.getNumberOfTeams();
    }

    public double getAverageTeamValue() {
        return averageTeamValue;
    }

    private void calculateBalanceThreshold(){
        this.doWhileThesholdBreak = this.averageTeamValue*0.10;
    }

}
