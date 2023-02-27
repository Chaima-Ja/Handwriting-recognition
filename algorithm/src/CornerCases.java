public class CornerCases {
    public static void main(String [] args) {
        String correct = "4ab";
        String submission = "2 a 2 b";
        System.out.println(Test.insertMul(Test.replacePower(Test.clean(correct))));
        System.out.println(Test.insertMul(Test.replacePower(Test.clean(submission))));
    }
}
