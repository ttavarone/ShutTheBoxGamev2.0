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
    private int[] numUpH, numUpL;
    private int pointsH, pointsL, roll, errNum, flippedDownH, flippedDownL;
    private double pointsSumH, pointsSumL, flippedDownSumH, flippedDownSumL, concatNumSumH, concatNumSumL;
    private boolean continueGame, numError, lowFound, highFound;
    private Random r = new Random();
    private double concatNumH;
    private double concatNumL;

    /**
     * Main constructor, initialises all the variables to default values
     */
    public ShutTheBox()
    {
        numUpH = new int[10]; // contains all the numbers that are still up and 0's for the ones that are down
        numUpL = new int[10];
        for(int i=0; i<9; i++){ // initialises the numbers up as what is up
            numUpH[i] = i+1;
            numUpL[i] = i+1;
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
        pointsSumH =0.000;
        pointsSumL = 0.000;
        flippedDownSumH = 0.000;
        flippedDownSumL = 0.000;
        concatNumSumH = 0.000;
        concatNumSumL = 0.000;
        concatNumH = 0.000;
        concatNumL = 0.000;
    }

    /**
     * A dice roll simulator, simulates rolling two dice and summing it up
     * so values can be anywhere from 1-9
     * 
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
                int num = numUpH[i];
                flippedDownH = flippedDownH+num;
            }
        }
        for(int i = 0; i < numUpL.length; i++){
            if(numUpL[i]==0){
                int num = numUpL[i];
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
        String cctNH = new String();
        String cctNL = new String();
        for(int i = 0; i < numUpH.length; i++){
            if(numUpH[i]!=0){
                cctNH = cctNH+numUpH[i];
            }
        }
        for(int i = 0; i < numUpL.length; i++){
            if(numUpL[i]!=0){
                cctNL = cctNL+numUpL[i];
            }
        }
        concatNumH = Integer.parseInt(cctNH);
        concatNumL = Integer.parseInt(cctNL);

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

        continueGame = subsetSum(arrayIn, arrayIn.length, roll);
    }

    /**
     * Tests whether or not the game can continue by testing all elements of the set (in the array)
     * and finding a subset that may add up to the roll, thus allowing the game to continue
     */
    private boolean subsetSum(int numUpIn[], int n, int sum) 
    { 
        if(sum==0){
            return true;
        }
        if(n==0&&sum!=0){
            return false;
        }
        if(numUpIn[n-1]>sum){
            return subsetSum(numUpIn, n-1, sum);
        }

        return subsetSum(numUpIn, n-1, sum)||subsetSum(numUpIn, n-1, sum-numUpIn[n-1]);

    }

    /**
     * A method that when run, instead of allowing user input, will try to choose
     * the HIGHEST number possible to fulfill the dice roll requirements
     *
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
     * @param arrayIn an int array representing the current working array
     */
    private void arrayString(int[] arrayIn)
    {
        for(int i = 0; i<arrayIn.length-1; i++){
            if(arrayIn[i]!=0){
                System.out.print(arrayIn[i]+" ");
            }
        }
        System.out.println();
    }

    /**
     * Method that sums up the values for each of the methods of scoring after running each method of scoring
     *
     */
    public void sumOfEverything()
    {
        sumOfUnflipped();
        numFlippedDown();
        concatUnflipped();

        pointsSumH += pointsH;
        flippedDownSumH += flippedDownH;
        concatNumSumH += concatNumH;

        pointsSumL += pointsL;
        flippedDownSumL += flippedDownL;
        concatNumSumL += concatNumL;
    }

    /**
     * Main Simulation class that will be called to create new simulations from main
     * 
     * Also manages the interactive text part of the game, the user interface
     */
    public void runSimulation()
    {
        Scanner input = new Scanner(System.in); // getting user input
        System.out.print("How many simulations? ");
        int simulations = input.nextInt(); // gathering user input as an int value representing num of games
        for(int games = 0; games < simulations; games++){
            while(continueGame){
                rollDice();
                possibilities(numUpH);
                chooseHigh();
                
            }
            while(continueGame){
                rollDice();
                possibilities(numUpL);
                chooseLow();
                
            }
            sumOfEverything();
        }
        input.close();
        
        System.out.println("Highest Choice");
        System.out.println("**************");
        System.out.println("Method 1: "+pointsSumH/simulations);
        System.out.println("Method 2: "+flippedDownH/simulations);
        System.out.println("Method 3: "+concatNumSumH/simulations);

        System.out.println();
        System.out.println("Lowest Choice");
        System.out.println("*************");
        System.out.println("Method 1: "+pointsSumL/simulations);
        System.out.println("Method 2: "+flippedDownL/simulations);
        System.out.println("Method 3: "+concatNumSumL/simulations);
    }
}
