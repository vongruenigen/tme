ArrayList<State> states = new ArrayList<State>();
ArrayList<State> acceptingStates = new ArrayList<State>();

String blankSymbol = "_";

Alphabet alphabet = new Alphabet(new String[] {"0", "1"});
Alphabet tapeAlphabet = new Alphabet(new String[] {"0", "1", blankSymbol});

State q0 = new State("q0");
State q1 = new State("q1");
State q2 = new State("q2");
State q3 = new State("q3");
State q4 = new State("q4");
State q5 = new State("q5");
State q6 = new State("q6");
State q7 = new State("q7");
State q8 = new State("q8");
State q9 = new State("q9");

q0.addTransition(q0, "0", "0", HeadDirection.RIGHT);
q0.addTransition(q0, "1", "1", HeadDirection.RIGHT);
q0.addTransition(q1, blankSymbol, "1", HeadDirection.LEFT);

q1.addTransition(q1, "0", "0", HeadDirection.LEFT);
q1.addTransition(q1, "1", "1", HeadDirection.LEFT);
q1.addTransition(q2, blankSymbol, blankSymbol, HeadDirection.RIGHT);

q2.addTransition(q3, "0", blankSymbol, HeadDirection.RIGHT);
q2.addTransition(q8, "1", blankSymbol, HeadDirection.RIGHT);

q3.addTransition(q3, "0", "0", HeadDirection.RIGHT);
q3.addTransition(q4, "1", "1", HeadDirection.RIGHT);

q4.addTransition(q5, "0", blankSymbol, HeadDirection.RIGHT);
q4.addTransition(q7, blankSymbol, blankSymbol, HeadDirection.LEFT);
q4.addTransition(q7, "1", "1", HeadDirection.LEFT);

q5.addTransition(q5, "0", "0", HeadDirection.RIGHT);
q5.addTransition(q5, "1", "1", HeadDirection.RIGHT);
q5.addTransition(q6, blankSymbol, "0", HeadDirection.LEFT);

q6.addTransition(q6, "0", "0", HeadDirection.LEFT);
q6.addTransition(q6, "1", "1", HeadDirection.LEFT);
q6.addTransition(q4, blankSymbol, "0", HeadDirection.RIGHT);

q7.addTransition(q7, "0", "0", HeadDirection.LEFT);
q7.addTransition(q7, "1", "1", HeadDirection.LEFT);
q7.addTransition(q2, blankSymbol, blankSymbol, HeadDirection.RIGHT);

q8.addTransition(q8, "0", blankSymbol, HeadDirection.RIGHT);
q8.addTransition(q9, "1", blankSymbol, HeadDirection.NONE);

states.add(q0);
states.add(q1);
states.add(q2);
states.add(q3);
states.add(q4);
states.add(q5);
states.add(q6);
states.add(q7);
states.add(q8);
states.add(q9);

acceptingStates.add(q9);

Machine tm = new Machine(states, alphabet, tapeAlphabet, q0, blankSymbol, acceptingStates);

tm.run("000100", true, true);