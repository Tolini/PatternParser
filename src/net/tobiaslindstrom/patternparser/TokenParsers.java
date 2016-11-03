package net.tobiaslindstrom.patternparser;

import net.tobiaslindstrom.patternparser.exceptions.TokenParseMismatchException;

/**
 * Created by Tobias on 11/3/2016.
 */
public class TokenParsers {

    static int parseDigit(String stringToParse, Token token, ResultPacket resultPacket) throws TokenParseMismatchException {
        char parsedDigit;
        int parsedLength;

        if (token.getEndMarker().equals("")) {
            parsedDigit = stringToParse.charAt(0);
            if (Character.isDigit(parsedDigit)) {
                resultPacket.addInt(token.getName(), parsedDigit - 48); //Subtract 48 since string.charAt gives ascii
                parsedLength = 1;
            } else {
                throw new TokenParseMismatchException("Error: Expected single digit integer but got: '" + parsedDigit + "'!");
            }
        } else {
            int endMarkerIndex = stringToParse.indexOf(token.getEndMarker());
            if (endMarkerIndex == 1) {
                String temp = stringToParse.substring(0, endMarkerIndex);
                if (Character.isDigit(temp.charAt(0))) {
                    resultPacket.addInt(token.getName(), temp.charAt(0) - 48);
                    parsedLength = 1 + token.getEndMarker().length();
                } else {
                    throw new TokenParseMismatchException("Error: Expected single digit integer but got: '" + temp + "'!");
                }
            } else {
                throw new TokenParseMismatchException("Error: The expected endmarker: '" + token.getEndMarker() + "' was not found!");
            }
        }

        return parsedLength;
    }

    static int parseInteger(String stringToParse, Token token, ResultPacket resultPacket) throws TokenParseMismatchException {
        int parsedInteger;
        int parsedLength;

        if (token.getEndMarker().equals("")) {
            int endIndex = stringToParse.length() - 1;
            int i = 0;

            for (char c : stringToParse.toCharArray()) {
                if (!Character.isDigit(c)) {
                    endIndex = endIndex > i ? i : endIndex;
                }
                i++;
            }

            if (endIndex > 1) {
                parsedInteger = Integer.parseInt(stringToParse.substring(0, endIndex));
                resultPacket.addInt(token.getName(), parsedInteger);
                parsedLength = endIndex;
            } else {
                throw new TokenParseMismatchException("Error: Expected to find an integer but found \"" + stringToParse.charAt(endIndex) + "\" instead!");
            }
        } else {
            int endMarkerIndex = stringToParse.indexOf(token.getEndMarker());
            if (endMarkerIndex > 1) {
                String temp = stringToParse.substring(0, endMarkerIndex);
                try {
                    parsedInteger = Integer.parseInt(temp);
                    resultPacket.addInt(token.getName(), parsedInteger);
                    parsedLength = temp.length() + token.getEndMarker().length();
                } catch (NumberFormatException e) {
                    throw new TokenParseMismatchException("Error: Expected to find integer but found \"" + temp + "\" instead!");
                }
            } else {
                throw new TokenParseMismatchException("Error: The expected endmarker: '" + token.getEndMarker() + "' was not found!");
            }
        }

        return parsedLength;
    }

    static int parseChar(String stringToParse, Token token, ResultPacket resultPacket) throws TokenParseMismatchException {
        int parsedLength;

        if (token.getEndMarker().equals("")) {
            resultPacket.addChar(token.getName(), stringToParse.charAt(0));
            parsedLength = 1;
        } else {
            int endMarkerIndex = stringToParse.indexOf(token.getEndMarker());
            if (endMarkerIndex > 0) {
                String temp = stringToParse.substring(0, endMarkerIndex);
                resultPacket.addChar(token.getName(), temp.charAt(0));
                parsedLength = 1 + token.getEndMarker().length();
            } else {
                throw new TokenParseMismatchException("Error: The expected endmarker: " + token.getEndMarker() + " was not found!");
            }
        }

        return parsedLength;
    }

}
