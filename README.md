# Flash Card Template (Student Friendly)

This small Java project is a beginner-friendly flash card program that uses
ArrayList for data storage and Swing for a simple graphical user interface.
It's designed for high-school students learning about ArrayLists and basic
event-driven programming in Java.

## What the program does
- Shows a question from the current flash card.
- A "Show Answer" button reveals the answer.
- "Back" and "Next" buttons navigate the list of cards (the answer is hidden on navigation).
- "Add card" lets the user create a new question and answer via dialog boxes.

The data model is in `FlashCard.java` (two ArrayLists: one for questions, one for answers).
The GUI and interactions are in `Main.java`.

## How to run (Windows — cmd.exe)
1. Open Command Prompt.
2. Change directory to the project folder:

```cmd
cd /d C:\Users\Kim\Desktop\Flash_Card_Template
```

3. Compile Java files:

```cmd
javac Main.java FlashCard.java
```

4. Run the program:

```cmd
java Main
```

You should see a small window titled "Flash Cards" showing the first question.

## Files to look at
- `FlashCard.java` — stores questions and answers using ArrayLists. Good file to study how lists work.
- `Main.java` — the Swing GUI. Contains the code to display questions, handle button clicks, and add new cards.

## Quick walkthrough (for students)
1. Open `FlashCard.java` and find where `questions` and `answers` are declared.
	- Notice they are `ArrayList<String>` objects.
	- Find `addQA`, `getQuestion`, and `getAnswer` methods — these show how we add and access items.

2. Open `Main.java` and scan from top to bottom:
	- `main()` creates a `FlashCard` object and adds two sample cards.
	- `createAndShowGUI()` builds the window and components (labels, buttons).
	- `updateUI` is a small function that writes the current question to the label.
	- Button listeners call `flashCard.getQuestion(index)` and `flashCard.getAnswer(index)`.

3. Try changing the sample cards at the top of `main()` and re-run the program.

## Small exercises (classroom)
1. Add three new sample cards in `main()` and ensure they appear when running the program.
2. Modify the program so the "Show Answer" button toggles between showing and hiding the answer (instead of disabling).
3. Add a "Shuffle" button that randomly rearranges the cards. Hint: use `Collections.shuffle(...)` on the underlying lists.
4. Save and load cards to/from a text file so cards persist between runs. (Advanced) Use `BufferedReader`/`BufferedWriter`.

## Teaching tips
- Walk students through `FlashCard.java` first (ArrayList basics), then show how `Main.java` uses that model.
- Encourage them to change text, add buttons, and print to the console to see the flow of data.

If you want, I can also add a simple README section with answers to exercises or convert the program to save/load cards from a file.

---
_Prepared for classroom use — feel free to ask me to add exercises, hints, or file persistence._
