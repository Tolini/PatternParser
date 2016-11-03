package net.tobiaslindstrom.patternparser;

import net.tobiaslindstrom.patternparser.exceptions.TokenParseMismatchException;

/**
 * Created by Tobias on 10/27/2016.
 */
public interface TokenParsingFunction {

    int tokenParse(String stringToParse, Token token, ResultPacket resultPacket) throws TokenParseMismatchException;

}
