package delgoh;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    
    public static void main(String[] args) throws IOException {

        if (args.length != 1) {
            System.out.println("Please input <directory_name> as argument.");
            System.exit(0);
        }

        String filePath = args[0];
        File currentPath = new File(filePath);
        if (!currentPath.exists()) {
            System.out.println("Directory/File does not exist");
            System.exit(0);
        }

        List<File> textFiles = getAllTextFiles(currentPath);
        if (textFiles.isEmpty()) {
            System.out.println("Directory is empty");
            System.exit(0);
        }

        Map<String, Map<String, Integer>> wordsCount = new HashMap<>();
        String dataStr = "";
        for (File textFile : textFiles) {
            dataStr = readFileAsString(textFile);
            wordsCount = addStrToMap(dataStr, wordsCount);
        }
        if (dataStr.equals("")) {
            System.out.println("No words found within file(s).");
            System.exit(0);
        }

        printDistribution(wordsCount);
    }

    // recursively collect and return all files within directory
    // **NOTE: it is assumed that all files within the directory/sub-directories are text/plain format
    public static List<File> getAllTextFiles(File path) throws IOException {
        List<File> textFiles = new ArrayList<>();

        if (path.isFile()) {
            textFiles.add(path);

        } else if (path.isDirectory()) {
            for (File subDir : path.listFiles()) {
                textFiles.addAll(getAllTextFiles(subDir));
            }
        }

        return textFiles;
    }

    // reads file and returns a text string (lines separated by single space)
    public static String readFileAsString(File file) throws IOException {
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);

        String fullText = "";
        String line = "";
        while ((line = br.readLine()) != null)
            fullText += line + " ";

        br.close();
        fr.close();

        return fullText;
    }

    // returns an updated word-count Map based on the given String input
    public static Map<String, Map<String, Integer>> addStrToMap(String text, Map<String, Map<String, Integer>> wordsCount) {
        text = text.replaceAll("\\p{Punct}", " ").toLowerCase();
        String[] wordsArr = text.split("\\s+");

        int oldCount = 0;
        for (int i = 0; i < wordsArr.length - 1; i++) {
            if (wordsCount.containsKey(wordsArr[i])) {
                if (wordsCount.get(wordsArr[i]).containsKey(wordsArr[i + 1])) {
                    oldCount = wordsCount.get(wordsArr[i]).get(wordsArr[i + 1]);
                    wordsCount.get(wordsArr[i]).put(wordsArr[i + 1], oldCount + 1);
                } else {
                    wordsCount.get(wordsArr[i]).put(wordsArr[i + 1], 1);
                }
            } else {
                Map<String, Integer> newMap = new HashMap<>();
                newMap.put(wordsArr[i + 1], 1);
                wordsCount.put(wordsArr[i], newMap);
            }
        }

        return wordsCount;
    }

    // prints word probability distribution from a given Map input
    public static void printDistribution(Map<String, Map<String, Integer>> wordsCount) {

        int countSum;
        float probability;
        String pStr;
        for (String firstWord : wordsCount.keySet()) {
            System.out.println(firstWord);

            countSum = 0;
            for (String secondWord : wordsCount.get(firstWord).keySet()) {
                countSum += wordsCount.get(firstWord).get(secondWord);
            }

            for (String secondWord : wordsCount.get(firstWord).keySet()) {
                System.out.printf("\t");
                System.out.printf(secondWord + " ");
                probability = (float) wordsCount.get(firstWord).get(secondWord) / countSum;
                pStr = String.valueOf(probability);
                pStr = pStr.contains(".") ? pStr.replaceAll("0*$","").replaceAll("\\.$","") : pStr;
                System.out.printf(pStr + "\n");
            }
        }

    }

}
