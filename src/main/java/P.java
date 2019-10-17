
import java.util.Arrays;
import java.util.Scanner;

public class P {
    // http://exercise.acmcoder.com/online/online_judge_ques?ques_id=3327&konwledgeId=155
    // 电话号码分身（小米2017秋招真题）
    static String[] tar = { "ZERO", "ONE", "TWO", "THREE", "FOUR", "FIVE",
            "SIX", "SEVEN", "EIGHT", "NINE" };
    // 0 2 4 6 8 7 5 3 9 1
    static char[] ch = { 'Z', 'W', 'U', 'X', 'G', 'S', 'F', 'H', 'I', 'O' };
    static int num[] = { 0, 2, 4, 6, 8, 7, 5, 3, 9, 1 };

//    public static void main(String[] args) {
//        sovle();
//    }

    private static void sovle() {
        Scanner scanner = new Scanner(System.in);
        int t = Integer.parseInt(scanner.nextLine());
        String tel;
        for (int i = 0; i < t; i++) {
            tel = scanner.nextLine();
            int cnt[] = new int[26];
            StringBuffer sb = new StringBuffer();
            for (int j = 0; j < tel.length(); j++) {
                cnt[tel.charAt(j) - 'A']++;
            }
            for (int j = 0; j < 10; j++) {
                int nc = cnt[ch[j] - 'A'];
                for (int k = 0; k < nc; k++) {
                    sb.append((num[j] + 2) % 10);
                }
                String ta = tar[num[j]];
                for (int k = 0; k < ta.length(); k++) {
                    cnt[ta.charAt(k) - 'A'] -= nc;
                }
            }
            char[] cs = sb.toString().toCharArray();
            Arrays.sort(cs);
            System.out.println(cs);
        }
    }
}



