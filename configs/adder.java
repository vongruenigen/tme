ArrayList<State> states = new ArrayList<State>();
ArrayList<State> acceptingStates = new ArrayList<State>();

String blankSymbol = "_";

Alphabet alphabet = new Alphabet(new String[] {"0", "1"});
Alphabet tapeAlphabet = new Alphabet(new String[] {"0", "1", blankSymbol});

State q0 = new State("q0");
State q1 = new State("q1");
State q2 = new State("q2");

q0.addTransition(q0, "0", "0", HeadDirection.RIGHT);
q0.addTransition(q0, "1", "1", HeadDirection.RIGHT);
q0.addTransition(q1, blankSymbol, blankSymbol, HeadDirection.LEFT);

q1.addTransition(q1, "1", "0", HeadDirection.LEFT);
q1.addTransition(q2, "0", "1", HeadDirection.LEFT);
q1.addTransition(q2, blankSymbol, "1", HeadDirection.LEFT);

states.add(q0);
states.add(q1);
states.add(q2);

acceptingStates.add(q2);

Machine tm = new Machine(states, alphabet, tapeAlphabet, q0, blankSymbol, acceptingStates);

tm.run("1111", true, true);