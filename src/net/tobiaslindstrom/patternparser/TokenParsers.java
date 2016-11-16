package net.tobiaslindstrom.patternparser;

import net.tobiaslindstrom.patternparser.exceptions.TokenParseMismatchException;

public class TokenParsers {

    static int parseDigit(String stringToParse, Token token, ResultPacket resultPacket) throws TokenParseMismatchException {
        int parsedDigit;

        if (token.getEndMarker().equals("")) {
            int digit = stringToParse.charAt(0);
            if (Character.isDigit(digit)) {
                parsedDigit = digit - 48; //Subtract 48 since string.charAt gives ascii
            } else {
                throw new TokenParseMismatchException("Error: Expected single digit integer but got: '" + digit + "'!");
            }
        } else {
            int endMarkerIndex = stringToParse.indexOf(token.getEndMarker());
            if (endMarkerIndex == 1) {
                String temp = stringToParse.substring(0, endMarkerIndex);
                if (Character.isDigit(temp.charAt(0))) {
                    parsedDigit = temp.charAt(0) - 48;
                } else {
                    throw new TokenParseMismatchException("Error: Expected single digit integer but got: '" + temp + "'!");
                }
            } else {
                throw new TokenParseMismatchException("Error: The expected endmarker: '" + token.getEndMarker() + "' was not found!");
            }
        }

        resultPacket.addInt(token.getName(), parsedDigit);

        return 1;
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

            if (endIndex > 0) {
                parsedInteger = Integer.parseInt(stringToParse.substring(0, endIndex + 1));
                parsedLength = endIndex + 1;
            } else {
                throw new TokenParseMismatchException("Error: Expected to find an integer but found \"" + stringToParse.charAt(endIndex) + "\" instead!");
            }
        } else {
            int endMarkerIndex = stringToParse.indexOf(token.getEndMarker());
            if (endMarkerIndex > 1) {
                String temp = stringToParse.substring(0, endMarkerIndex);
                try {
                    parsedInteger = Integer.parseInt(temp);
                    parsedLength = temp.length();
                } catch (NumberFormatException e) {
                    throw new TokenParseMismatchException("Error: Expected to find integer but found \"" + temp + "\" instead!");
                }
            } else {
                throw new TokenParseMismatchException("Error: The expected endmarker: '" + token.getEndMarker() + "' was not found!");
            }
        }

        resultPacket.addInt(token.getName(), parsedInteger);

        return parsedLength;
    }

    public static int parseDouble(String stringToParse, Token token, ResultPacket resultPacket) throws TokenParseMismatchException {
        double parsedDouble;
        int parsedLength;

        if (token.getEndMarker().equals("")) {
            int endIndex = stringToParse.length() - 1;
            int i = 0;

            for (char c : stringToParse.toCharArray()) {
                if (!Character.isDigit(c) && c != '.') {
                    endIndex = endIndex > i ? i : endIndex;
                }
                i++;
            }

            if (endIndex > 0) {
                parsedDouble = Double.parseDouble(stringToParse.substring(0, endIndex + 1));
                parsedLength = endIndex + 1;
            } else {
                throw new TokenParseMismatchException("Error: Expected to find an integer but found \"" + stringToParse.charAt(endIndex) + "\" instead!");
            }
        } else {
            int endMarkerIndex = stringToParse.indexOf(token.getEndMarker());
            if (endMarkerIndex > 1) {
                String temp = stringToParse.substring(0, endMarkerIndex);
                try {
                    parsedDouble = Double.parseDouble(temp);
                    parsedLength = temp.length();
                } catch (NumberFormatException e) {
                    throw new TokenParseMismatchException("Error: Expected to find double but found \"" + temp + "\" instead!");
                }
            } else {
                throw new TokenParseMismatchException("Error: The expected endmarker: '" + token.getEndMarker() + "' was not found!");
            }
        }

        resultPacket.addDouble(token.getName(), parsedDouble);

        return parsedLength;
    }

    static int parseChar(String stringToParse, Token token, ResultPacket resultPacket) throws TokenParseMismatchException {
        char parsedChar;

        if (token.getEndMarker().equals("")) {
            parsedChar = stringToParse.charAt(0);
        } else {
            int endMarkerIndex = stringToParse.indexOf(token.getEndMarker());
            if (endMarkerIndex > 0) {
                String temp = stringToParse.substring(0, endMarkerIndex);
                parsedChar = temp.charAt(0);
            } else {
                throw new TokenParseMismatchException("Error: The expected endmarker: " + token.getEndMarker() + " was not found!");
            }
        }

        resultPacket.addChar(token.getName(), parsedChar);

        return 1;
    }

    public static int parseString(String stringToParse, Token token, ResultPacket resultPacket) throws TokenParseMismatchException {
        String parsedString;
        int parsedLength;

        if (token.getEndMarker().equals("")) {
            int beginQuoteIndex = stringToParse.indexOf('"');
            int endQuoteIndex = stringToParse.indexOf('"', beginQuoteIndex + 1);

            if (beginQuoteIndex == 0 && endQuoteIndex > -1) {
                parsedString = stringToParse.substring(1, endQuoteIndex);
                parsedLength = parsedString.length() + 2;
            } else {
                int spaceIndex = stringToParse.indexOf(' ');
                if (spaceIndex > -1) {
                    parsedString = stringToParse.substring(0, spaceIndex);
                    parsedLength = parsedString.length() + 1;
                } else {
                    throw new TokenParseMismatchException("Error: Expected to find quotation marks or a space to contain the string!");
                }
            }
        } else {
            int endMarkerIndex = stringToParse.indexOf(token.getEndMarker());
            if (endMarkerIndex > 0) {
                parsedString = stringToParse.substring(0, endMarkerIndex);
                parsedLength = parsedString.length();
            } else {
                throw new TokenParseMismatchException("Error: The expected endmarker: " + token.getEndMarker() + " was not found!");
            }
        }

        resultPacket.addString(token.getName(), parsedString);

        return parsedLength;
    }
}