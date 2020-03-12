package com.yasai.flashcard;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lars on 10.02.2018.
 */

public class QuestionItem {

    private static String getQuestion(ListItem.ItemPair itemPair) {
        return  itemPair.left;
    }
    private static String getAnswer(ListItem.ItemPair itemPair) {
        return itemPair.right;
    }

    private static String getQuestionHeader(ListItem listItem) {
        return listItem.getLeftHeader();
    }
    private static String getAnswerHeader(ListItem listItem) {
        return listItem.getRightHeader();
    }

    public static List<QuestionItem> getQuestionItemList(ListItem listItem) {
        List<QuestionItem> questionItemList = new ArrayList<>();

        for(ListItem.ItemPair itemPair: listItem.getItemPairs()) {
            String filePath = listItem.getFilePath();

            // Extract the fileName without type ending from file path

            int fileNameStartIdx = filePath.lastIndexOf("/") + 1;
            if(fileNameStartIdx == -1) {
                fileNameStartIdx = 0;
            }

            int fileNameEndIdx = filePath.lastIndexOf(".");
            if(fileNameEndIdx == -1) {
                fileNameEndIdx = filePath.length();
            }

            String listName = filePath.substring(fileNameStartIdx, fileNameEndIdx);

            String question = getQuestion(itemPair);
            String rightAnswer = getAnswer(itemPair);

            List<String> potentialWrongAnswers = new ArrayList<>();
            for(ListItem.ItemPair ip: listItem.getItemPairs()) {
                if(!getQuestion(ip).equals(question) && !getAnswer(ip).equals(rightAnswer) && !potentialWrongAnswers.contains(getAnswer(ip))) {
                    potentialWrongAnswers.add(getAnswer(ip));
                }
            }

            questionItemList.add(new QuestionItem(getQuestionHeader(listItem), getAnswerHeader(listItem), listName, question, rightAnswer, potentialWrongAnswers));
        }

        return questionItemList;
    }

    public String questionHeader;
    public String answerHeader;
    public String listName;
    public String question;
    public String rightAnswer;
    public List<String> wrongAnswers;

    private QuestionItem(String questionHeader, String answerHeader, String listName, String question, String rightAnswer, List<String> wrongAnswers) {
        this.questionHeader = questionHeader;
        this.answerHeader = answerHeader;
        this.listName = listName;
        this.question = question;
        this.rightAnswer = rightAnswer;
        this.wrongAnswers = wrongAnswers;

        // Fill wrong answer lists to make sure the list contains enough items to fill all flash cards
        int numberOfMissingAnswers = GameActivity.NUMBER_OF_ANSWERS - wrongAnswers.size() - 1; // minus the correct answer
        for(int i = 0; i < numberOfMissingAnswers; i++) {
            wrongAnswers.add("");
        }
    }
}
