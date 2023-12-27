import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class FlashcardApp {
    public static void main(String[] args) {
        // Create a list to store user-defined questions
        List<Question> userDefinedFlashcards = new ArrayList<>();

        // Create a Scanner for user input
        Scanner scanner = new Scanner(System.in);

        try {
            // Ask the user if they want to enter custom flashcards
            String response = "";
            while (!response.equalsIgnoreCase("yes") && !response.equalsIgnoreCase("no")) {
                if (!response.isEmpty()) {
                    System.out.println("That wasn't one of the options. Please enter 'yes' or 'no'.");
                }
                System.out.print("Do you want to create your own flashcards? (yes/no): ");
                response = scanner.nextLine();
            }

            // Allow the user to enter custom flashcards
            while (response.equalsIgnoreCase("yes")) {
                // Prompt the user for a question
                System.out.print("Enter a question: ");
                String userQuestion = scanner.nextLine();

                // Prompt the user for an answer
                System.out.print("Enter the answer: ");
                String userAnswer = scanner.nextLine();

                // Create a custom flashcard and add it to the list
                userDefinedFlashcards.add(new Question(userQuestion, userAnswer));

                // Ask if the user wants to add another flashcard
                response = "";
                while (!response.equalsIgnoreCase("yes") && !response.equalsIgnoreCase("no")) {
                    if (!response.isEmpty()) {
                        System.out.println("That wasn't one of the options. Please enter 'yes' or 'no'.");
                    }
                    System.out.print("Do you want to add another flashcard? (yes/no): ");
                    response = scanner.nextLine();
                }
            }

            // Ask the user how they want to view the flashcards (ordered or randomized)
            String viewOption = "";
            while (!viewOption.equalsIgnoreCase("order") && !viewOption.equalsIgnoreCase("random")) {
                if (!viewOption.isEmpty()) {
                    System.out.println("That wasn't one of the options. Please enter 'order' or 'random'.");
                }
                System.out.print("Do you want to see the flashcards in the order you created them or randomized? (order/random): ");
                viewOption = scanner.nextLine();
            }

            // Ask the user for the time limit per flashcard
            long timeLimitPerFlashcard = 0;
            while (timeLimitPerFlashcard <= 0) {
                try {
                    System.out.print("Enter the time limit for each flashcard in seconds (e.g., 15): ");
                    timeLimitPerFlashcard = Long.parseLong(scanner.nextLine()) * 1000; // Convert to milliseconds
                    if (timeLimitPerFlashcard <= 0) {
                        System.out.println("Please enter a positive number for the time limit.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a valid number for the time limit.");
                }
            }

            // Loop through flashcards based on user's choice
            List<Question> flashcardsToUse = userDefinedFlashcards;
            if (viewOption.equalsIgnoreCase("random")) {
                // Shuffle the flashcards if the user chose random
                Collections.shuffle(flashcardsToUse);
            }

            // Initialize the score
            int score = 0;

            // Loop through all flashcards
            for (Question flashcard : flashcardsToUse) {
                // Display the question
                System.out.println("Question: " + flashcard.getQuestion());

                // Start the timer
                long startTime = System.currentTimeMillis();

                // Prompt user for an answer
                System.out.print("Your answer: ");
                String userAnswer = scanner.nextLine();

                // Stop the timer
                long endTime = System.currentTimeMillis();

                // Check if the answer is correct and within the time limit
                if (userAnswer.equalsIgnoreCase(flashcard.getAnswer()) && (endTime - startTime) <= timeLimitPerFlashcard) {
                    System.out.println("Correct! You answered within the time limit.");
                    // Increment the score for correct and timely answers
                    score++;
                } else if (endTime - startTime > timeLimitPerFlashcard) {
                    System.out.println("Time's up! The correct answer is: " + flashcard.getAnswer());
                } else {
                    System.out.println("Wrong. The correct answer is: " + flashcard.getAnswer());
                }

                System.out.println(); // Add a newline for better readability
            }

            // Display the final score
            System.out.println("Your final score: " + score + " out of " + flashcardsToUse.size());
        } catch (Exception e) {
            System.err.println("An error occurred: " + e.getMessage());
        } finally {
            // Close the scanner
            if (scanner != null) {
                scanner.close();
            }
        }
    }
}