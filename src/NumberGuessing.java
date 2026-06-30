import java.util.*;

public class NumberGuessing {
    private static final Scanner sc = new Scanner(System.in);
    private static final Random random = new Random();
    private static final int EASY_RANGE = 10;
    private static final int EASY_ATTEMPTS = 5;
    private static final int MEDIUM_RANGE = 50;
    private static final int MEDIUM_ATTEMPTS = 7;
    private static final int HARD_RANGE = 100;
    private static final int HARD_ATTEMPTS = 9;

    private static int gamesPlayed = 0;
    private static int gamesWon = 0;
    private static int totalAttemptsUsed = 0;
    private static int bestScore = Integer.MAX_VALUE;

    static class Difficulty {
        final int range;
        final int attempts;

        Difficulty(int range, int attempts) {
            this.range = range;
            this.attempts = attempts;
        }
    }

    public static void main(String[] args) {
        boolean playAgain = true;

        while (playAgain) {
            displayMenu();
            Difficulty d = chooseDifficulty();
            playGame(d.range, d.attempts);
            playAgain = askToPlayAgain();
        }

        displayScoreboard();
        sc.close();
    }


    public static void displayMenu() {
        System.out.println("==============================");
        System.out.println("      NUMBER GUESSING GAME");
        System.out.println("==============================");
        System.out.println("Choose Difficulty");
        System.out.println("1. Easy   (1-10 | 5 Attempts)");
        System.out.println("2. Medium (1-50 | 7 Attempts)");
        System.out.println("3. Hard   (1-100 | 9 Attempts)");
        System.out.print("Enter your choice: ");
    }

    public static Difficulty chooseDifficulty() {
        int choice = 0;
        while (true) {
            try {
                choice = sc.nextInt();
                if (choice == 1) return new Difficulty(EASY_RANGE, EASY_ATTEMPTS);
                if (choice == 2) return new Difficulty(MEDIUM_RANGE, MEDIUM_ATTEMPTS);
                if (choice == 3) return new Difficulty(HARD_RANGE, HARD_ATTEMPTS);
                System.out.println("Invalid choice! Please enter 1, 2, or 3.");
            } catch (InputMismatchException e) {
                System.out.println("Invalid input type! Enter only numbers.");
                sc.nextLine();
            }
        }
    }

    public static void playGame(int range, int attemptsAllowed) {
        int number = random.nextInt(range) + 1;
        gamesPlayed++;
        int guess = 0;
        int countOfAttempts = 0;

        System.out.println("Guess the number between 1-" + range);

        while (guess != number) {
            if (countOfAttempts < attemptsAllowed) {
                while (true) {
                    try {
                        System.out.print("Enter your guess: ");
                        guess = sc.nextInt();
                        break;
                    } catch (InputMismatchException e) {
                        System.out.println("Please enter a valid number.");
                        sc.nextLine();
                    }
                }
                countOfAttempts++;

                if (guess < 1 || guess > range) {
                    System.out.println("Out of range!");
                } else if (guess < number) {
                    System.out.println("Too low!");
                } else if (guess > number) {
                    System.out.println("Too high!");
                } else {
                    System.out.println("Congratulations!");
                    System.out.println("You guessed the number in " + countOfAttempts + " attempts.");
                    gamesWon++;
                    totalAttemptsUsed += countOfAttempts;
                    if (countOfAttempts < bestScore) bestScore = countOfAttempts;
                }
            } else {
                System.out.println("No attempts remaining!");
                System.out.println("The correct number was " + number);
                break;
            }
        }

        if (gamesWon > 0) {
            System.out.println("Best Score (Least attempts): " + bestScore);
            System.out.printf("Average Attempts per Win: %.2f%n", (double) totalAttemptsUsed / gamesWon);
        }
    }

    public static boolean askToPlayAgain() {
        while (true) {
            System.out.print("Want to play again? (y/n): ");
            char response = sc.next().toLowerCase().charAt(0);
            if (response == 'y') return true;
            if (response == 'n') return false;
            System.out.println("Enter y or n.");
        }
    }

    public static void displayScoreboard() {
        System.out.println("==============================");
        System.out.println("         SCOREBOARD");
        System.out.println("==============================");
        System.out.println("Games Played : " + gamesPlayed);
        System.out.println("Games Won    : " + gamesWon);

        double winRate = ((double) gamesWon / gamesPlayed) * 100;
        System.out.printf("Win Rate     : %.2f%%%n", winRate);

        if (gamesWon > 0) {
            System.out.println("Best Score   : " + bestScore + " attempts");
            System.out.printf("Average Win  : %.2f attempts%n", (double) totalAttemptsUsed / gamesWon);
        }
        System.out.println("==============================");
        System.out.println("Thanks for playing!");
        System.out.println("See you next time.");
    }
}
