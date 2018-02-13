import java.util.Random;
import java.util.Scanner;
/**
 * Contains all the main methods that control the object (the game)
 * that is 'Shut The Box'
 *
 * @author (Tucker Tavarone)
 * @version (2/9/18)
 */
public class ShutTheBox
{
    private int[] numUpH, numUpL, numUpC;
    private int pointsH, pointsL, roll, errNum, flippedDownH, flippedDownL;
    private int pointsSumH, pointsSumL, flippedDownSumH, flippedDownSumL, concatNumSumH, concatNumSumL;
    private boolean continueGame, numError, lowFound, highFound;
    private Random r = new Random();
    private String concatNumH = new String();
    private String concatNumL = new String();

    /**
     * Main constructor, initialises all the variables to default values
     */
    public ShutTheBox()
    {
        numUpH = new int[10]; // contains all the numbers that are still up and 0's for the ones that are down
        numUpL = new int[10];
        numUpC = new int[10];
        for(int i=0; i<9; i++){ // initialises the numbers up as what is up
            numUpH[i] = i+1;
            numUpL[i] = i+1;
            numUpC[i] = i+1;
        }
        pointsH = 0;
        pointsL = 0;// starts with nothing
        roll = 0;
        flippedDownH = 0;
        flippedDownL = 0;
        errNum = 0;
        continueGame = true; // game will be initialised as being able to continue
        numError = false;
        lowFound = false;
        highFound = false;
        pointsSumH =0;
        pointsSumL = 0;
        flippedDownSumH = 0;
        flippedDownSumL = 0;
        concatNumSumH = 0;
        concatNumSumL = 0;
    }

    /**
     * A dice roll simulator, simulates rolling two dice and summing it up
     * so values can be anywhere from 1-9
     * 
     * @return int that contains the sum of rolling 'two' dice
     */
    public void rollDice()
    {
        int max = 10;
        int min = 1;
        roll = min + r.nextInt(max);
    }

    /**
     * Runs through the array of numbers down and if it is a 0, then numbers up gets
     * a 0 in that position, if not it stays as the number it should be
     * 
     * It is only run in this class after numbers down, to update what is still up
     */
    private void curNumUp(int numIn, int[] arrayIn)
    {
        for(int i = 0; i<arrayIn.length-1; i++){
            if(arrayIn[i]==numIn){
                arrayIn[i]=0;
            }
        }
    }

    /**
     * Run after game is finished will be called almost last in the runSimulation class 
     * returns sets the points variable to the remaining numbers up
     */
    public void sumOfUnflipped()
    {
        for(int i=0; i<numUpH.length; i++)
        {
            pointsH+=numUpH[i];
        }
        for(int i=0; i<numUpL.length; i++)
        {
            pointsL+=numUpL[i];
        }
    }

    /**
     * This method tests which numbers are flipped down and adds it up to return
     * a score
     *
     */
    public void numFlippedDown()
    {
        for(int i = 0; i < numUpH.length; i++){
            if(numUpH[i]==0){
                int num = numUpC[i];
                flippedDownH = flippedDownH+num;
            }
        }
        for(int i = 0; i < numUpL.length; i++){
            if(numUpL[i]==0){
                int num = numUpC[i];
                flippedDownL = flippedDownL+num;
            }
        }
    }

    /**
     * Takes all the numbers down and concats them to each other then returns
     * the string representing this value
     *
     */
    public void concatUnflipped()
    {
        for(int i = 0; i < numUpH.length; i++){
            if(numUpH[i]!=0){
                concatNumH = concatNumH+numUpH[i];
            }
        }
        for(int i = 0; i < numUpL.length; i++){
            if(numUpL[i]!=0){
                concatNumL = concatNumL+numUpL[i];
            }
        }
    }

    /**
     * This class checks for two things, the first is whether or not the game can continue
     * based on the available options left, the second is if all the numbers are down.... it is run 
     * at the end of the loop in run simulation and the loop breaks if this decides game over
     * 
     */
    public void possibilities(int[] arrayIn)
    {
        int anyUp = 0;
        for(int i = 0; i<arrayIn.length; i++){
            anyUp += arrayIn[i];
        }
        if(anyUp==0){continueGame=false;}

        continueGame = sumOfSubset(arrayIn, arrayIn.length, roll);
    }

    /**
     * Tests whether or not the game can continue by testing all elements of the set (in the array)
     * and finding a subset that may add up to the roll, thus allowing the game to continue
     */
    private boolean sumOfSubset(int numUpIn[], int n, int sum) 
    { 
        if(sum==0){
            return true;
        }
        if(n==0&&sum!=0){
            return false;
        }
        if(numUpIn[n-1]>sum){
            return sumOfSubset(numUpIn, n-1, sum);
        }

        return sumOfSubset(numUpIn, n-1, sum)||sumOfSubset(numUpIn, n-1, sum-numUpIn[n-1]);

    }

    /** for both this and the following algorithm, they need to be able to choose
     *   more than one number (possibly) OR if it cannot choose more than one number
     *   than it needs to check when it is not possible for the algorithm to 
     *   continue because the dice roll is too big to reach
     */
    /**
     * A method that when run, instead of allowing user input, will try to choose
     * the HIGHEST number possible to fulfill the dice roll requirements
     *
     * @return an int that represents the highest chosen number
     */
    public void chooseHigh()
    {
        int highest = 9;
        for(int i = numUpH.length-1; i >= 0; i--){
            if(numUpH[i] != 0 && roll-numUpH[i]==0){
                highest = numUpH[i];
                numUpH[i]=0;
                break;
            }
        }
    }

    /**
     * A method that when run, instead of allowing user input, will try to choose
     * the LOWEST number possible to fulfill the dice roll requirements
     *
     * @return an int that represents the lowest chosen number
     */
    public void chooseLow()
    {
        int lowest = 0;
        for(int i = 0; i < numUpL.length; i++){
            if(numUpL[i]!=0 && roll-numUpL[i]==0){
                lowest = numUpL[i];
                numUpL[i]=0;
                break;
            }
        }
    }

    /**
     * Prints out the numUp array so that the user can visualize it
     * 
     */
    public void curNumString(int[] arrayIn)
    {
        for(int i = 0; i<arrayIn.length-1; i++){
            if(arrayIn[i]!=0){
                System.out.print(arrayIn[i]+" ");
            }
        }
        System.out.println();
    }

    /**
     * Method that sums up the values for each of the methods of scoring
     *
     * @param  y  a sample parameter for a method
     * @return    the sum of x and y
     */
    public void sumOfEverything()
    {
        sumOfUnflipped();
        numFlippedDown();
        concatUnflipped();

        int cctNH = Integer.parseInt(concatNumH);
        int cctNL = Integer.parseInt(concatNumL);

        pointsSumH += pointsH;
        flippedDownSumH += flippedDownH;
        concatNumSumH += cctNH;

        pointsSumL += pointsL;
        flippedDownSumL += flippedDownL;
        concatNumSumL += cctNL;

        //run each of the methods each time this is called to sum everything up
        //may need some instance variables that represent the sums
        //returns the values to the instance variables

    }

    /**
     * Main Simulation class that will be called to create new simulations from main
     * 
     * Also manages the interactive text part of the game, the user interface
     */
    public void runSimulation()
    {
        Scanner input = new Scanner(System.in);
        System.out.print("How many simulations? ");
        int simulations = input.nextInt();
        for(int games = 0; games < simulations; games++){
            while(continueGame){
                // curNumString(numUpH);  (DEBUGGING)
                rollDice();
                possibilities(numUpH);
                possibilities(numUpL);
                chooseHigh();
                chooseLow();
                sumOfEverything();
            }
        }

        System.out.println("Highest Choice");
        System.out.println("Method 1"+pointsSumH);
        System.out.println("Method 2 "+flippedDownH);
        System.out.println("Method 3"+concatNumSumH);

        System.out.println("Lowest Choice");
        System.out.println("Method 1"+pointsSumL);
        System.out.println("Method 2 "+flippedDownL);
        System.out.println("Method 3"+concatNumSumL);
    }
}
