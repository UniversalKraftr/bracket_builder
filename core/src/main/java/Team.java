import java.util.LinkedList;
import java.util.List;

// Class specific for handling all details specific to a team
public class Team {
    private List<Player> team;
    private String teamName;
    private int teamSize;
    private double teamValue;
    private boolean isBalanced;

    public Team(String teamName, int teamSize, double teamValue) {
        this.team = new LinkedList<>();
        this.teamName = teamName;
        this.teamSize = teamSize;
        this.teamValue = teamValue;
        this.isBalanced = false;
    }

    public Team() {
        team = new LinkedList<>();
        teamName = "";
        teamSize = 0;
        teamValue = 0.0;
        this.isBalanced = false;
    }

    public boolean swapPlayers(Player targetPlayer, Player newPlayer){
        if(team.contains(targetPlayer)){
            updateTeamStats(targetPlayer, false);
            team.set(team.indexOf(targetPlayer), newPlayer);
            updateTeamStats(newPlayer, true);
            return true;
        }
        System.out.println(targetPlayer.getGamerTag() + " not found in team " + teamName);
        return false;
    }

    public boolean addPlayer(Player player, int maxSize){
        int teamSize = team.size();

        if(!(teamSize < 0) && !(teamSize > maxSize) && player != null && !team.contains(player)){
            team.add(player);
            updateTeamStats(player, true);
            return true;
        }
        return false;
    }

    public boolean removePlayer(Player player){
        if(teamSize > 0 && team.contains(player) && player != null){
            updateTeamStats(player, false);
            return team.remove(player);
        }
        return false;
    }

    private void updateTeamStats(Player player, boolean addRemoveFlag){
        updateTeamValue(player, addRemoveFlag);
        updateTeamSize();
    }

    public boolean isBalanced() {
        return isBalanced;
    }

    public void setBalanced(boolean balanced) {
        isBalanced = balanced;
    }

    private void updateTeamSize(){
        teamSize = team.size();
    }

    private void updateTeamValue(Player player, boolean addRemoveFlag){
        if(addRemoveFlag){
            teamValue += player.getSkillWeight();
        } else {
            teamValue -= player.getSkillWeight();
        }
    }

    public void displayTeam(){
        System.out.println("====================================================");
        System.out.println("Team Name: " + teamName);
        System.out.println("Team Size: " + teamSize);
        System.out.println("Team Value: " + teamValue);
        System.out.println("Players:");
        for(Player player: team){
            System.out.println("\t" + player.getGamerTag() + " : " + player.getSkillWeight());
        }
        System.out.println("====================================================\n\n");
    }

    public List<Player> getTeam() {
        return team;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public int getTeamSize() {
        return teamSize;
    }

    public double getTeamValue() {
        return teamValue;
    }

}
