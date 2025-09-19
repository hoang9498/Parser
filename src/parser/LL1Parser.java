package parser;
import java.util.*;
public class LL1Parser {
	private final PredictiveParsingTable table;
    private final Set<String> terminals;
    private final String startSymbol;

    public LL1Parser(PredictiveParsingTable table, Set<String> terminals, String startSymbol) {
        this.table = table;
        this.terminals = terminals;
        this.startSymbol = startSymbol;
    }

    public void parse(List<Token> tokens) {

        Stack<String> stack = new Stack<>();
        stack.push("$");
        stack.push(startSymbol);

        Token EOF = new Token(TokenType.EOF, "$");
        tokens.add(EOF); // Append end marker
        int index = 0;
        int lineNumber = 1;
        FirstFollowSet ff = new FirstFollowSet(); // liet ke phan tu co the dang thieu
        System.out.println("\nLeftmost derivation:");

        while (!stack.isEmpty()) {
            if( index<tokens.size()) {
                String top = stack.peek();
                String currentToken = tokens.get(index).ChooseTokenPart();
                while (currentToken.equals("COMMENT_BLOCK")||currentToken.equals("COMMENT_LINE")
                        ||currentToken.equals("NEWLINE")) {    // while != "\n"
                    if (currentToken.equals("NEWLINE")) {
                        lineNumber++;
                    }
                    if (currentToken.equals("COMMENT_BLOCK")) {
                        lineNumber +=countNewLines(tokens.get(index).getValue());
                    }
                    index++;
                    currentToken = tokens.get(index).ChooseTokenPart();
                }

                if (top.equals(currentToken)) {
                    stack.pop();
                    index++;
                } else if (terminals.contains(top)) {
                    System.err.println("at line " + lineNumber + " Syntax error: expected '" + top 
                    		+ "', but found '"
                            + tokens.get(index).getValue() + "'");
                    stack.pop(); // Recover: skip terminal
                } else {
                    List<String> production = table.getCell(top, currentToken);
                    if (production == null) {
                        System.err.println("at line " + lineNumber + " Syntax error: illegal token '"
                                + tokens.get(index).getValue() + "' at nonterminal '" + top + "'");
                        System.err.println("May be you are missing one of the following keywords: ");
                        if (top.contains("Tail")) {
                            System.err.println(String.join(", ", ff.getFollow(top)));
                            int position = stack.search(";");
                            if (position != -1) {
                                // new Token
                                Token newToken = new Token(TokenType.SEMI, ";");
                                tokens.add(index, newToken);
                            }
                        } else {
                            System.err.println(String.join(", ", ff.getFirst(top)));
                            if(ff.getFirst(top).size()==1){
                                // new Token
                                Token newToken = new Token(Lexer.identifyTokenType(ff.getFirst(top).toString())
                                        , ff.getFirst(top).toString());
                                tokens.add(index, newToken);
                            }
                            index++; // Discard current token
                        }


                    } else if (production.size() == 1 && production.get(0).equals("sync")) {
                        System.err.println("Panic mode: skipping nonterminal '" + top + "'");
                        stack.pop(); // Discard nonterminal
                    } else if (production.size() == 1 && production.get(0).equals("ε")) {
                        stack.pop();
                        System.out.println(top + " → " + String.join(" ", production));
                    } else {
                        stack.pop();
                        // Print derivation step
                        System.out.println(top + " → " + String.join(" ", production));
                        // Push production in reverse order (right to left)
                        for (int i = production.size() - 1; i >= 0; i--) {
                            String symbol = production.get(i);
                            if (!symbol.equals("ε")) {
                                stack.push(symbol);
                            }
                        }
                    }
                }
            }else{
                break;
            }
        }


    }

    public  int countNewLines(String input) {
        int count = 0;
        for (char c : input.toCharArray()) {
            if (c == '\n') {
                count++;
            }
        }
        return count;
    }
}
