package com.example.kalkulator;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Piotr Borczyk on 17.03.2017.
 */

public class Tokenizer {

    public static List<String> tokenize(String input) throws Exception {
        List<String> tokens = new ArrayList<>();
        Pattern pattern = Pattern.compile("((\\d*\\.\\d+)|(\\d+)|([\\^+\\-*/()]))");
        Matcher matcher = pattern.matcher(input);
        while (matcher.find()) {
            tokens.add(matcher.group(1));
        }
        zipOperators(tokens);
        validate(tokens);
        return tokens;
    }

    private static void validate(List<String> tokens) throws Exception {
        for(String token : tokens) {
            if (!Parser.isNumeric(token) && !Parser.isOperator(token)) throw new Exception();
            if(token.charAt(0) == '.') throw new Exception();
        }
    }

    private static void zipOperators(List<String> tokens) {
        for (int i = 0; i < tokens.size(); i++) {
            String token = tokens.get(i);
            if (token.equals(Operator.SUBTRACTION.symbol)) {
                if (i == 0 || Parser.isOperator(tokens.get(i - 1))) {
                    String nextToken = tokens.get(i + 1);
                    nextToken = "-" + nextToken;
                    tokens.set(i + 1, nextToken);
                    tokens.remove(i);
                }
            }
        }
    }
}
