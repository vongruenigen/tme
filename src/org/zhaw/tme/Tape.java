package org.zhaw.tme;

import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;

public class Tape {
    public final static int TAPE_SIZE = 50;

    private int cursorPosition;
    private String blankSymbol;
    private Alphabet alphabet;
    private ArrayList<String> content;

    public Tape(Alphabet alphabet, String blankSymbol) {
        this.alphabet = alphabet;
        this.blankSymbol = blankSymbol;
        this.content = new ArrayList<String>();
    }

    public void setContent(String content) {
      if (!this.alphabet.isCompatible(content)) {
        System.out.println("ERROR: Word '" + content + "' is not compatible!");
        return;
      }

      this.content = new ArrayList<String>(Arrays.asList(content.split("(?!^)")));
      this.adjust();

      for (int i = 0; i < this.content.size(); i++) {
        if (this.content.get(i) != this.blankSymbol) {
          this.cursorPosition = i;
          break;
        }
      }
    }

    public ArrayList<String> getContent() {
      return this.content;
    }

    public void write(String symbol) {
      this.content.set(this.cursorPosition, symbol);
      this.adjust();
    }

    public String getCurrentSymbol() {
      return this.content.get(this.cursorPosition);
    }

    public void output() {
      int lastIndex = this.content.size() - 1;
      System.out.print("      |");

      for (int i = 0; i < this.content.size(); i++) {
        System.out.print(i == this.cursorPosition ? "V" : " ");
        if (i != lastIndex) System.out.print(" ");
      }

      System.out.print("|\n");

      for (int i = 0; i < this.content.size(); i++) {
        if (i == 0) {
          System.out.print("Tape: |");
        }

        System.out.format("%s|", this.content.get(i));
      }

      System.out.println();
    }

    public void moveHead(HeadDirection direction) {
      switch (direction) {
        case LEFT:  this.cursorPosition--; break;
        case RIGHT: this.cursorPosition++; break;
      }

      if (this.cursorPosition >= this.content.size()) {
        this.content.add(blankSymbol);
      }
    }

    private void adjust() {
      if (this.content.size() == 0) {
        for (int i = 0; i < TAPE_SIZE; i++) {
          this.content.add(this.blankSymbol);
        }
      } else {
        int remaining = TAPE_SIZE - this.content.size();

        if (remaining < 0) {
          return;
        } else {
          int blankSymbolCount = remaining / 2;

          for (int i = 0; i < blankSymbolCount; i++) {
            this.content.add(this.blankSymbol);
            this.content.add(0, this.blankSymbol);
          }

          if (remaining % 2 == 1) {
            this.content.add(this.blankSymbol);
          }
        }
      }
    }
}
