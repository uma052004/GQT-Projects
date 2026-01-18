import java.util.*;

public class QuizGameKBC {

    // ANSI Colors
    static final String GREEN = "\u001B[32m";
    static final String RED = "\u001B[31m";
    static final String YELLOW = "\u001B[33m";
    static final String CYAN = "\u001B[36m";
    static final String RESET = "\u001B[0m";

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        // ===================== DETAILS PAGE =====================
        System.out.println("======================================");
        System.out.println("          QUIZ GAME        ");
        System.out.println("======================================");

        System.out.println("\n--- DETAILS PAGE ---");
        System.out.print("Enter your name: ");
        String name = sc.nextLine();

        System.out.print("Enter your age: ");
        int age = sc.nextInt();
        sc.nextLine(); // consume newline

        System.out.print("Enter your city: ");
        String city = sc.nextLine();

        System.out.println("\n" + CYAN + "Welcome " + name + " (" + age + "), from " + city + "!" + RESET);
        System.out.println("Press Enter to Start the Quiz...");
        sc.nextLine();

        // ===================== QUESTIONS =====================
        String[] questions = {
                "1) Which keyword is used to create an object in Java?",
                "2) Which data type is used to store decimal values?",
                "3) Which loop is guaranteed to run at least once?",
                "4) What is the size of int in Java?",
                "5) Which method is the entry point of a Java program?",
                "6) Which collection does NOT allow duplicate elements?",
                "7) Which operator is used for logical AND?",
                "8) Which exception occurs when dividing by zero?",
                "9) Which keyword is used to inherit a class in Java?",
                "10) Which of these is NOT an OOP concept?"
        };

        String[][] options = {
                {"1) new", "2) create", "3) object", "4) class"},
                {"1) int", "2) double", "3) char", "4) boolean"},
                {"1) for", "2) while", "3) do-while", "4) foreach"},
                {"1) 2 bytes", "2) 4 bytes", "3) 8 bytes", "4) 16 bytes"},
                {"1) start()", "2) run()", "3) main()", "4) execute()"},
                {"1) ArrayList", "2) HashSet", "3) LinkedList", "4) Vector"},
                {"1) &&", "2) ||", "3) !", "4) &"},
                {"1) NullPointerException", "2) ArithmeticException", "3) IOException", "4) IndexOutOfBoundsException"},
                {"1) this", "2) extends", "3) super", "4) implements"},
                {"1) Encapsulation", "2) Inheritance", "3) Compilation", "4) Polymorphism"}
        };

        int[] correct = {1, 2, 3, 2, 3, 2, 1, 2, 2, 3};

        int[] reward = {1000, 2000, 5000, 10000, 20000, 40000, 80000, 160000, 320000, 1000000};

        // ===================== RULES =====================
        System.out.println("======================================");
        System.out.println("Rules:");
        System.out.println("- 10 Questions, level by level.");
        System.out.println("- Correct -> win reward + next question.");
        System.out.println("- Wrong -> eliminated (game ends).");
        System.out.println("- Option 5 = Lifeline (Only once in entire game).");
        System.out.println("- Lifeline has: Audience Poll OR 50-50.");
        System.out.println("- Lifeline not allowed for Question 10.");
        System.out.println("======================================\n");

        boolean lifelineAvailable = true; // ONLY ONCE in entire game
        int totalMoney = 0;

        // ===================== QUIZ LOOP =====================
        for (int i = 0; i < 10; i++) {

            System.out.println(YELLOW + "--------------------------------------" + RESET);
            System.out.println(CYAN + questions[i] + RESET);

            for (int j = 0; j < 4; j++) {
                System.out.println(options[i][j]);
            }

            // show lifeline option for Q1-Q9 only
            if (i < 9) {
                System.out.println("5) Lifeline");
            }

            while (true) {
                System.out.print("Select option (1-4" + (i < 9 ? " or 5" : "") + "): ");
                int choice = sc.nextInt();

                // Lifeline not allowed for Q10
                if (i == 9 && choice == 5) {
                    System.out.println(RED + "❌ Lifeline is not allowed for 10th question!" + RESET);
                    continue;
                }

                // Lifeline selection
                if (choice == 5 && i < 9) {

                    if (!lifelineAvailable) {
                        System.out.println(RED + "❌ Lifeline already used! Choose 1-4." + RESET);
                        continue;
                    }

                    System.out.println("\n--- LIFELINE MENU ---");
                    System.out.println("1) Audience Poll");
                    System.out.println("2) 50-50");
                    System.out.print("Choose lifeline (1/2): ");
                    int lifeChoice = sc.nextInt();

                    if (lifeChoice == 1) {
                        lifelineAvailable = false;

                        int correctOpt = correct[i];
                        System.out.println("\n📊 " + CYAN + "Audience Poll Results:" + RESET);

                        for (int op = 1; op <= 4; op++) {
                            if (op == correctOpt) {
                                System.out.println(op + " -> " + (60 - i) + "%");
                            } else {
                                System.out.println(op + " -> " + (10 + op * 5) + "%");
                            }
                        }
                        System.out.println();

                    } else if (lifeChoice == 2) {
                        lifelineAvailable = false;

                        int correctOpt = correct[i];
                        int wrongOpt = (correctOpt % 4) + 1; // any one wrong option

                        System.out.println("\n👤 " + CYAN + "50-50 Options:" + RESET);
                        System.out.println(correctOpt + ") " + options[i][correctOpt - 1].substring(3));
                        System.out.println(wrongOpt + ") " + options[i][wrongOpt - 1].substring(3));
                        System.out.println();

                    } else {
                        System.out.println(RED + "❌ Invalid lifeline choice. Try again." + RESET);
                    }

                    continue; // after lifeline, ask answer again
                }

                // Validate option
                if (choice < 1 || choice > 4) {
                    System.out.println(RED + "❌ Invalid option! Choose 1-4." + RESET);
                    continue;
                }

                // Check answer using if condition
                if (choice == correct[i]) {
                    totalMoney += reward[i];
                    System.out.println(GREEN + "✅ CORRECT ANSWER!" + RESET);
                    System.out.println(GREEN + "🎉 You won ₹" + reward[i] + RESET);
                    System.out.println(CYAN + "💰 Total Money: ₹" + totalMoney + RESET);

                    // move next question
                    break;

                } else {
                    System.out.println(RED + "❌ INCORRECT ANSWER!" + RESET);
                    System.out.println(RED + "✅ Correct option was: " + correct[i] + RESET);
                    System.out.println(RED + "🏁 Game Over! You won ₹" + totalMoney + RESET);
                    sc.close();
                    return;
                }
            }
        }

        System.out.println(GREEN + "\n🏆 AMAZING! You completed all 10 Questions!" + RESET);
        System.out.println(GREEN + "🎊 Final Winning Amount: ₹" + totalMoney + RESET);

        sc.close();
    }
}
