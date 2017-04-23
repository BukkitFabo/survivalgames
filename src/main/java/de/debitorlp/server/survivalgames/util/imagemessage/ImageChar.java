package de.debitorlp.server.survivalgames.util.imagemessage;

/**
 * User: bobacadodl Date: 1/25/14 Time: 11:03 PM
 */
public enum ImageChar {
    BLOCK('\u2588'), DARK_SHADE('\u2593'), MEDIUM_SHADE('\u2592'), LIGHT_SHADE('\u2591');
    private char cha;

    ImageChar(char cha) {
        this.cha = cha;
    }

    public char getChar() {
        return cha;
    }
}
