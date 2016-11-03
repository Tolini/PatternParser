package net.tobiaslindstrom.patternparser;

import net.tobiaslindstrom.patternparser.exceptions.InvalidTokenParserException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tobias on 10/27/2016.
 */
public class TokenPattern {

    private List<Token> tokens = new ArrayList<>();

    List<Token> getTokens() {
        return this.tokens;
    }

    public TokenPattern(String pattern) {
        List<List<Integer>> tokenDelimiters = this.getTokenDelimiters(pattern);

        if(tokenDelimiters == null) {
            try {
                throw new InvalidTokenParserException("Error: Invalid pattern syntax, missing either an opening or closing token delimiter '}'!");
            } catch (InvalidTokenParserException e) {
                e.printStackTrace();
            }
        } else {
            for(int i = 0; i < tokenDelimiters.size(); i++) {
                List<Integer> delimiters = tokenDelimiters.get(i);

                String tokenName = pattern.substring(delimiters.get(0) + 1, delimiters.get(1));
                String tokenType = pattern.substring(delimiters.get(1) + 1, delimiters.get(2));
                String endMarker = "";

                if(i + 1 < tokenDelimiters.size()) {
                    endMarker = pattern.substring(delimiters.get(2) + 1, tokenDelimiters.get(i + 1).get(0));
                }
                tokens.add(new Token(tokenName, tokenType, endMarker));
            }
            tokens.add(new Token("EndMarker", "EndMarker", ""));
        }
    }

    private List<List<Integer>> getTokenDelimiters(String pattern) {
        List<List<Integer>> tokenDelimiters = new ArrayList<>();
        int startDelimiterCount = 0;
        int tokenDividerCount = 0;
        int endDelimiterCount = 0;
        int charPos = 0;

        for(char c : pattern.toCharArray()) {
            if(c == '{') {
                if (tokenDelimiters.size() == startDelimiterCount) {
                    tokenDelimiters.add(new ArrayList<>());
                }

                tokenDelimiters.get(startDelimiterCount).add(0, charPos);
                startDelimiterCount++;
            } else if(c == ':') {
                if (tokenDelimiters.size() == tokenDividerCount) {
                    tokenDelimiters.add(new ArrayList<>());
                }

                tokenDelimiters.get(tokenDividerCount).add(1, charPos);
                tokenDividerCount++;
            } else if(c == '}') {
                if(tokenDelimiters.size() == endDelimiterCount) {
                    tokenDelimiters.add(new ArrayList<>());
                }

                tokenDelimiters.get(endDelimiterCount).add(2, charPos);
                endDelimiterCount++;
            }
            charPos++;
        }

        return startDelimiterCount == tokenDividerCount && tokenDividerCount == endDelimiterCount ? tokenDelimiters : null;
    }

}
