import java.io.IOException;
import java.util.LinkedList;

public class Main {

    public static void main(String[] args) throws IOException {

        LinkedList<Player> players = new LinkedList<>();
//        String fileName = "core\\src\\main\\resources\\testFile.xlsx";
        String fileName = "core\\src\\main\\resources\\Multiplayer_Tournament_Roster.xlsx";
//        String fileName = "E:\\ImmortalX4z\\Multiplayer_Tournament_Roster.xslx";

        FileReader fileReader = new FileReader(fileName);
        fileReader.readFile(players);

//        Tester code for teamManager, Team, and Player
        TeamManager teamManager = new TeamManager();
        teamManager.formTeams(players);
        teamManager.displayTeams();

        teamManager.optimizeTeams();
        System.out.println("\n\n\nFINAL TEAMS\n");
        teamManager.displayTeams();
    }
}

/*
create an object to represent a player
create an object to represent a team

open file
read in a single line
extract gamertag
calculate player's skill weight
store gamertag and skill weight in dictionary as key:value pair
store skill weight in sum total
repeat until all users are entered into dictionary
close file

run modulo 5 on number of keys in dictionary
calculate total number of teams of 5
calculate remainder difference and run modulo 4
if not equal to zero, repeat process, but use 4 as the top and 3
if second iteration not equal to zero, repeat for 3 and 2
if 3 and 2 does not work, then restore back to 5 and 4,
    with 3 to be used for high skilled player balance
be sure to get the total number of teams for each size count
and calculate the average team value based on the total number
    of teams that will be created

create a list that will store single key:value paired dictionaries
    (key: list of players; value: total team value)
add in as many dictionaries with their preconstructed empty lists keys and 0 values
    as needed to equal the total number of teams needed
pull first player from player dictionary who has the highest value
and place in first nested dictionary
make sure that player is removed from the player dictionary
and placed into a secondary dictionary
add their name to the nested dictionary's key list, and their value to the value
repeat to the next one until each list has one
repeat until all players are distributed and have been moved from
    the original dictionary and placed in the secondary

construct two variables
one to store the over difference widest gap
one to store the under difference widest gap

construct two more variables
one to store the index of the team that has the highest over difference gap
one to store the index of the team that has the highest under difference gap

start iterating through the pre-assembled teams dictionary
first iteration is just using each team's sum value
check each team's sum value
if the value is less than the average team value calculation,
  calculate the difference and assign to the under difference widest gap
  and extract the team's in
if the value is higher than the average team value calculation,
  calculate the difference and assign to the over difference widest gap
iterate through entire list of teams to find highest over and under difference gaps

start second iteration to work through each individual team

find the player who's value is the most over (or closest) to the over difference widest gap
pull that player from the list, subtract that value from the team's sum
find the team who is furthest away from the average team value
check on the team size
if equal to five
check the team's sum value
if it is higher than the average team value calculation
find the player with the value that has the highest gap over the team's
    sum value difference to the average team value
swap the two players, and make sure to remove and add the respective player values to the sum
update the over difference widest gap if the new value is further from zero
if it is lower
find the player with the value that has the highest gap under the team's
    sum value difference to the average team value
swap the two players, and make sure to remove and add the respective player values to the sum
update the under difference widest gap if the new value is further from zero
if less than five
place the pulled player into that team list and add their value to the sum
inspect the over/under difference and update the appropriate variable if it is further from zero
*/