import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.TreeSet;

public class Test {
    public static void main(String [] args) {
        Scanner sc = new Scanner(System.in);
        boolean finished = false;
        String correct, submission;
        do {
            correct = sc.nextLine();
            if (correct.equals(".")) finished = true;
            else {
                do {
                    submission = sc.nextLine();
                    if (!submission.equals(".")){
                        if (areEquivalents(correct, submission)) System.out.println("yes");
                        else System.out.println("no");
                    }

                } while (!submission.equals("."));
                System.out.println(".");
            }
        }while(!finished);
    }

    public static String clean(String s) {
        StringBuilder res = new StringBuilder();
        s = s.trim();
        for(int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == ' ' ){
                if(s.charAt(i+1) != ' '){
                    if (Character.isDigit(s.charAt(i + 1))) {
                        if (Character.isDigit(s.charAt(i - 1)))
                            res.append("*");
                    }
                }
            }
            else
                res.append(s.charAt(i));
        }
        return res.toString();
    }

    public static String replacePower(String s) {
        boolean finished_loop = false;
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) != '^') {
                if (!finished_loop)
                    res.append(s.charAt(i));
                else
                    finished_loop = false;
            }
            else {
                int power = (int) s.charAt(i+1) - 48;
                char ch = s.charAt(i-1);
                for(int j=0; j < power-1; j++)
                    res.append("*").append(ch);
                finished_loop = true;
            }
        }
        return res.toString();
    }

    public static String insertMul(String s) {
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < s.length() - 1; i++) {
            if ((Character.isDigit(s.charAt(i)) && Character.isLetter(s.charAt(i+1))) ||
                    Character.isLetter(s.charAt(i)) && Character.isDigit(s.charAt(i+1)) ||
                    Character.isLetter(s.charAt(i)) && Character.isLetter(s.charAt(i+1)))
                res.append(s.charAt(i)).append("*");
            else {
                if (s.charAt(i) == ')' && s.charAt(i+1) == '(')
                    res.append(s.charAt(i)).append("*");
                else
                    res.append(s.charAt(i));
            }

        }
        res.append(s.charAt(s.length()-1));
        return res.toString();
    }

    public static ArrayList<Character> getParameters(String s) {
        TreeSet<Character> res = new TreeSet<>();
        for(int i=0; i < s.length(); i++){
            if (Character.isLetter(s.charAt(i))){
                res.add(s.charAt(i));
            }
        }
        return new ArrayList<>(res);
    }

    public static String replaceParameters(String s, ArrayList<Character> parameters, ArrayList<Integer> testValues) {
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < s.length(); i++){
            if (parameters.contains(s.charAt(i)))
                res.append(testValues.get(parameters.indexOf(s.charAt(i))));
            else
                res.append(s.charAt(i));
        }
        return res.toString();
    }

    public static boolean areEquivalents(String original, String copy){
        String cleaned_original = insertMul(replacePower(clean(original)));
        String cleaned_copy = insertMul(replacePower(clean(copy)));
        ArrayList<Character> parameters = getParameters(cleaned_original);
        if (!parameters.equals(getParameters(cleaned_copy)))
            return false;
        int parameter_count = parameters.size();
        ArrayList<Integer> testValues = new ArrayList<>();
        for (int i = 0; i < parameter_count; i++) testValues.add(1);
        for (int i = 2; i < 10; i++) {
            if (EvaluateString.evaluate(replaceParameters(cleaned_copy, parameters, testValues))
                != EvaluateString.evaluate(replaceParameters(cleaned_original, parameters, testValues)))
                return false;
            Collections.replaceAll(testValues, testValues.get(0), i);
        }
        return true;
    }
}
