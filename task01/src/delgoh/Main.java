package delgoh;

import java.math.BigDecimal;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        System.out.printf("Welcome.\n>");

        Scanner scan = new Scanner(System.in);
        String userInput = scan.nextLine();
        String[] inputParams;
        float num1 = 0, num2 = 0;

        float saved = 0;
        while (!userInput.equals("exit")) {

            inputParams = userInput.split(" ");

            if (inputParams.length != 3) {
                System.out.printf("Invalid syntax! Please key in: <num1> <op> <num2>\n>");
                userInput = scan.nextLine();
                continue;
            }

            if (!"+-*/".contains(inputParams[1])) {
                System.out.printf("Invalid operator keyed in\n>");
                userInput = scan.nextLine();
                continue;
            }

            if (inputParams[0].equals("$last"))
                inputParams[0] = String.valueOf(saved);
            if (inputParams[2].equals("$last"))
                inputParams[2] = String.valueOf(saved);   

            try {
                num1 = Float.parseFloat(inputParams[0]);
                num2 = Float.parseFloat(inputParams[2]);
            } catch (NumberFormatException nfe) {
                System.out.printf("Invalid number(s) keyed in\n>");
                userInput = scan.nextLine();
                continue;
            }
            
            float result = 0;
            if (inputParams[1].equals("+"))
                result = Float.parseFloat(inputParams[0]) + Float.parseFloat(inputParams[2]);
            else if (inputParams[1].equals("-"))
                result = Float.parseFloat(inputParams[0]) - Float.parseFloat(inputParams[2]);
            else if (inputParams[1].equals("*"))
                result = Float.parseFloat(inputParams[0]) * Float.parseFloat(inputParams[2]);
            else if (inputParams[1].equals("/")) {
                if (Float.parseFloat(inputParams[2]) == 0) {
                    System.out.printf("Error: Divide by 0\n>");
                    userInput = scan.nextLine();
                    continue;
                }
                result = Float.parseFloat(inputParams[0]) / Float.parseFloat(inputParams[2]);
            }

            saved = result;

            String resultStr = String.valueOf(result);
            resultStr = resultStr.contains(".") ? resultStr.replaceAll("0*$","").replaceAll("\\.$","") : resultStr;
            System.out.println(resultStr);

            System.out.printf(">");
            userInput = scan.nextLine();
        }

        System.out.println("Bye bye");
        scan.close();
    }

}