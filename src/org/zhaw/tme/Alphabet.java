package org.zhaw.tme;

import java.util.regex.Pattern;

public class Alphabet {
    private String[] symbols;

    public Alphabet(String[] symbols) {
        this.symbols = symbols;
    }

    public boolean isCompatible(String word) {
        // To prevent modifications on the original String object
        String copiedWord = word.toString();

        for (String symbol : symbols) {
            String quotedSymbol = Pattern.quote(symbol);
            copiedWord = copiedWord.replaceAll(quotedSymbol, "");
        }

        return copiedWord.length() == 0;
    }

    public String getDescription() {
        String desc = "";
        int lastIndex = this.symbols.length - 1;

        for (int i = 0; i < this.symbols.length; i++) {
            desc += "'" + this.symbols[i] + "'";
            if (i != lastIndex) desc += ", ";
        }

        return desc;
    }
}
