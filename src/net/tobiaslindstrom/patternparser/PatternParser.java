package net.tobiaslindstrom.patternparser;

import net.tobiaslindstrom.patternparser.exceptions.InvalidTokenParserException;
import net.tobiaslindstrom.patternparser.exceptions.TokenParseMismatchException;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Tobias on 10/27/2016.
 */
public class PatternParser {

    private static Map<String, TokenParsingFunction> tokenParsers = new HashMap<>();
    private static boolean initialized = false;
    private static boolean loadDefaultParsers = true;

    public static void shouldLoadDefaultParsers(boolean val) {
        loadDefaultParsers = val;
    }

    public static void addTokenParser(String tokenType, TokenParsingFunction parsingFunction) throws InvalidTokenParserException {
        if (!tokenType.equals("EndMarker")) {
            if (tokenParsers.containsKey(tokenType)) {
                tokenParsers.replace(tokenType, parsingFunction);
                throw new InvalidTokenParserException("Warning: Deafult token parser og type \" + tokenType + \" has ben overwritten. Parser may behave unexpectedly!");
            } else {
                tokenParsers.put(tokenType, parsingFunction);
            }
        } else {
            throw new InvalidTokenParserException("Error: A token parser of type \"EndMarker\" can not be overwritten as it is a required token for the parser!");
        }
    }

    public static ResultPacket parse(String stringToParse, TokenPattern tokenPattern) throws TokenParseMismatchException, InvalidTokenParserException {
        return parse(stringToParse, tokenPattern, true);
    }

    public static ResultPacket parse(String stringToParse, TokenPattern tokenPattern, boolean trunc) throws TokenParseMismatchException, InvalidTokenParserException {
        if (!initialized && loadDefaultParsers) {
            addDefaultTokenParsers();
            initialized = true;
        }

        ResultPacket resultPacket = new ResultPacket();

        for (Token token : tokenPattern.getTokens()) {
            if (token.getType().equals("EndMarker")) {
                if (stringToParse.length() > 0 && !trunc) {
                    throw new TokenParseMismatchException("Error: Finished parsing the with the token pattern but input string still has more data!");
                }
            } else {
                int parsedValueLength;

                TokenParsingFunction tokenParser = tokenParsers.get(token.getType());
                if (tokenParser != null) {
                    parsedValueLength = tokenParser.tokenParse(stringToParse, token, resultPacket);
                    stringToParse = stringToParse.substring(parsedValueLength);
                } else {
                    throw new InvalidTokenParserException("Error: No token parser was found for token type: \"" + token.getType() + "\"!");
                }
            }
        }

        return resultPacket;
    }

    private static void addDefaultTokenParsers() {
        try {
            addTokenParser("Digit", (TokenParsers::parseDigit));
            addTokenParser("Number", (TokenParsers::parseInteger));
            addTokenParser("Char", (TokenParsers::parseChar));
        } catch (InvalidTokenParserException e) {
            e.printStackTrace();
        }
    }

}
