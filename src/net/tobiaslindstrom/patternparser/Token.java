package net.tobiaslindstrom.patternparser;

/**
 * Created by Tobias on 10/27/2016.
 *
 * Token class is datastructure used by the TokenPattern for storing information about the tokens in its pattern.
 */
class Token {

    private String type, name, endMarker;

    Token(String name, String type, String endMarker) {
        this.type = type;
        this.name = name;
        this.endMarker = endMarker;
    }

    String getEndMarker() {
        return this.endMarker;
    }

    String getType() {
        return this.type;
    }

    String getName() {
        return this.name;
    }

}
