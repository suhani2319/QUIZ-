import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class QuizApp {
    static class Question {
        String question;
        String[] options;
        int correctAnswer;

        Question(String question, String[] options, int correctAnswer) {
            this.question = question;
            this.options = options;
            this.correctAnswer = correctAnswer;
        }
    }

    static Question[] questions = {
            new Question("What is the capital of France?", new String[]{"1. Berlin", "2. Madrid", "3. Paris", "4. Rome"}, 3),
            new Question("Which planet is known as the Red Planet?", new String[]{"1. Earth", "2. Mars", "3. Jupiter", "4. Saturn"}, 2),
            new Question("What is the largest ocean on Earth?", new String[]{"1. Atlantic", "2. Indian", "3. Arctic", "4. Pacific"}, 4)
    };

    static int score = 0;
    static int currentQuestion = 0;
    static Timer timer;
    static boolean answered = false;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        for (Question question : questions) {
            answered = false;
            displayQuestion(question);
            startTimer();

            while (!answered) {
                if (scanner.hasNextInt()) {
                    int answer = scanner.nextInt();
                    if (answer >= 1 && answer <= 4) {
                        answered = true;
                        timer.cancel();
                        checkAnswer(question, answer);
                    } else {
                        System.out.println("Invalid option. Please select a number between 1 and 4.");
                    }
                }
            }
        }

        displayResult();
    }

    public static void displayQuestion(Question question) {
        System.out.println(question.question);
        for (String option : question.options) {
            System.out.println(option);
        }
        System.out.println("Please select your answer (1-4): ");
    }

    public static void startTimer() {
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (!answered) {
                    System.out.println("Time's up!");
                    answered = true;
                    checkAnswer(questions[currentQuestion], -1);
                }
            }
        }, 10000); // 10 seconds for each question
    }

    public static void checkAnswer(Question question, int answer) {
        if (answer == question.correctAnswer) {
            System.out.println("Correct!");
            score++;
        } else {
            System.out.println("Incorrect. The correct answer was: " + question.options[question.correctAnswer - 1]);
        }
        currentQuestion++;
    }

    public static void displayResult() {
        System.out.println("Quiz Over!");
        System.out.println("Your final score is: " + score + "/" + questions.length);

        for (int i = 0; i < questions.length; i++) {
            System.out.println("Question " + (i + 1) + ": " + questions[i].question);
            System.out.println("Your answer: " + (questions[i].correctAnswer == -1 ? "No answer" : questions[i].options[questions[i].correctAnswer - 1]));
            System.out.println("Correct answer: " + questions[i].options[questions[i].correctAnswer - 1]);
        }
    }
}