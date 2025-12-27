import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

/**
 * Flash Card GUI Exploration
 *
 * This is a simple flash card program with the following features:
 * - Shows the question for the current flash card
 * - "Show Answer" button reveals the answer
 * - "Back" and "Next" buttons navigate cards (answer hidden when navigating)
 * - "Add card" button prompts to add a new question and answer
 *
 * The FlashCard class (in FlashCard.java) stores questions and answers in two
 * ArrayLists. This GUI reads from and writes to that FlashCard object.
 */
public class Main {

    public static void main(String[] args) {
        // Create the data model and add a couple of sample cards.
        FlashCard flashCard = new FlashCard();
        flashCard.addQA("What is the capital of France?", "Paris");
        flashCard.addQA("What is 2 + 2?", "4");

        // Start the Swing GUI on the Event Dispatch Thread (EDT).
        //SwingUtilities.invokeLater(() -> createAndShowGUI(flashCard));
        
        createAndShowGUI(flashCard);
    }

    /**
     * Build and show the GUI. 
     */
    private static void createAndShowGUI(FlashCard flashCard) {
        // Top-level window
        JFrame frame = new JFrame("Flash Cards");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Main panel with padding
        JPanel main = new JPanel();
        main.setBorder(new EmptyBorder(12, 12, 12, 12));
        main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));

        // --- Card display ---
        // JLabel for the question (HTML allowed for automatic wrapping)
        JLabel questionLabel = new JLabel();
        questionLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        questionLabel.setFont(questionLabel.getFont().deriveFont(Font.PLAIN, 16f));

        // JLabel for the answer (hidden until Show Answer is pressed)
        JLabel answerLabel = new JLabel();
        answerLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        answerLabel.setFont(answerLabel.getFont().deriveFont(Font.ITALIC, 14f));
        answerLabel.setVisible(false);

        // --- Controls ---
        JPanel controls = new JPanel();
        controls.setLayout(new FlowLayout(FlowLayout.LEFT, 8, 0));
        controls.setAlignmentX(Component.LEFT_ALIGNMENT);

        JButton backBtn = new JButton("Back");
        JButton showBtn = new JButton("Show Answer");
        JButton nextBtn = new JButton("Next");
        JButton addBtn = new JButton("Add card");

        controls.add(backBtn);
        controls.add(showBtn);
        controls.add(nextBtn);

        // Status label (shows current card index)
        JLabel statusLabel = new JLabel();
        statusLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Keep current index in an array so inner classes can modify it
        final int[] currentIndex = {0};

        // Helper that updates question, hides answer, and updates buttons/status
        Runnable updateUI = () -> {
            int size = flashCard.getSize();
            if (size == 0) {
                questionLabel.setText("<html><b>(no cards)</b></html>");
                answerLabel.setVisible(false);
                backBtn.setEnabled(false);
                nextBtn.setEnabled(false);
                showBtn.setEnabled(false);
                statusLabel.setText("Card 0 / 0");
            } else {
                // Ensure currentIndex is inside bounds
                if (currentIndex[0] < 0) currentIndex[0] = 0;
                if (currentIndex[0] > size - 1) currentIndex[0] = size - 1;

                String q = flashCard.getQuestion(currentIndex[0]);
                questionLabel.setText("<html><b>Q: </b>" + escapeHtml(q) + "</html>");

                // Hide the answer until user asks for it
                answerLabel.setVisible(false);
                answerLabel.setText("");

                backBtn.setEnabled(currentIndex[0] > 0);
                nextBtn.setEnabled(currentIndex[0] < size - 1);
                showBtn.setEnabled(true);
                statusLabel.setText("Card " + (currentIndex[0] + 1) + " / " + size);
            }
            frame.pack();
        };

        // Show Answer action: display the answer for the current card
        showBtn.addActionListener(e -> {
            String a = flashCard.getAnswer(currentIndex[0]);
            answerLabel.setText("<html><b>A: </b>" + escapeHtml(a) + "</html>");
            answerLabel.setVisible(true);
            // disable show button so students can see that state change
            showBtn.setEnabled(false);
            frame.pack();
        });

        // Next and Back navigate cards and hide the answer
        nextBtn.addActionListener(e -> {
            if (currentIndex[0] < flashCard.getSize() - 1) {
                currentIndex[0]++;
                updateUI.run();
            }
        });

        backBtn.addActionListener(e -> {
            if (currentIndex[0] > 0) {
                currentIndex[0]--;
                updateUI.run();
            }
        });

        // Add card: ask for question and answer using simple dialogs
        addBtn.addActionListener(e -> {
            String newQ = JOptionPane.showInputDialog(frame, "Enter the question:");
            if (newQ == null || newQ.trim().isEmpty()) {
                // cancelled or empty -> do nothing
                return;
            }
            String newA = JOptionPane.showInputDialog(frame, "Enter the answer:");
            if (newA == null) return; // cancelled

            flashCard.addQA(newQ.trim(), newA);
            // move to the newly added card
            currentIndex[0] = flashCard.getSize() - 1;
            updateUI.run();
        });

        // Assemble components
        main.add(questionLabel);
        main.add(Box.createRigidArea(new Dimension(0, 8)));
        main.add(controls);
        main.add(Box.createRigidArea(new Dimension(0, 8)));
        main.add(answerLabel);
        main.add(Box.createRigidArea(new Dimension(0, 8)));
        main.add(statusLabel);
        main.add(Box.createRigidArea(new Dimension(0, 8)));
        // Place Add button at the very bottom under the other controls
        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        bottom.setAlignmentX(Component.LEFT_ALIGNMENT);
        bottom.add(addBtn);
        main.add(bottom);

        frame.getContentPane().add(main);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        // Final initial update to show first card
        updateUI.run();
    }

    // Simple helper to escape HTML reserved characters for safe display in JLabel
    private static String escapeHtml(String s) {
        if (s == null) return "";
        return s.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;");
    }
}