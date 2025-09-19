package parser;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class Lexer {
	private static final String TOKEN_REGEX =
            "/\\*([\\s\\S]*?)\\*/|" +        // COMMENT_BLOCK
                    "//[^\r\n]*|" +                  // COMMENT_LINE
                    "\\r?\\n|" +                         // NEWLINE
                    "\\s+|" +                         // WHITESPACE
                    "\\bbegin\\b|\\bend\\b|" +        // BEGIN, END
                    "\\bint\\b|\\bbool\\b|" +         // INT, BOOL
                    "\\bif\\b|\\bthen\\b|\\belse\\b|" +  // IF, THEN, ELSE
                    "\\bdo\\b|\\bwhile\\b|\\bfor\\b|" + // DO, WHILE, FOR
                    "\\bprint\\b|" +                 // PRINT
                    ">=?|==|\\+|\\*|=|;|,|\\(|\\)|\\{|\\}|" + // Operators and symbols
                    "\\btrue\\b|\\bfalse\\b|" +       // BOOL_LITERAL
                    "[a-zA-Z]+[0-9]*|" +         // ID
                    "\\d+";                           // NUMBER

    private static final Pattern TOKEN_PATTERN = Pattern.compile(TOKEN_REGEX);

    private final String input;
    private final List<Token> tokens = new ArrayList<>();

    public Lexer(String input) {
        this.input = input;
    }

    public List<Token> tokenize() {
        Matcher matcher = TOKEN_PATTERN.matcher(input);

        while (matcher.find()) {
            String value = matcher.group();
            TokenType type = identifyTokenType(value);
            //Token token = new Token(type, value);
            if (type == TokenType.WHITESPACE) {
                continue;
            }
            //System.out.println(token);
             tokens.add(new Token(type, value));
        }

        return tokens;
    }

    public static TokenType identifyTokenType(String value) {
        if (value.matches("/\\*([\\s\\S]*?)\\*/")) return TokenType.COMMENT_BLOCK;
        if (value.matches("//[^\r\n]*")) return TokenType.COMMENT_LINE;
        if (value.matches("\\r?\\n")) return TokenType.NEWLINE;
        if (value.matches("\\s+")) return TokenType.WHITESPACE;
        if (value.matches("\\bbegin\\b")) return TokenType.BEGIN;
        if (value.matches("\\bend\\b")) return TokenType.END;
        if (value.matches("\\bint\\b")) return TokenType.INT;
        if (value.matches("\\bbool\\b")) return TokenType.BOOL;
        if (value.matches("\\bif\\b")) return TokenType.IF;
        if (value.matches("\\bthen\\b")) return TokenType.THEN;
        if (value.matches("\\belse\\b")) return TokenType.ELSE;
        if (value.matches("\\bdo\\b")) return TokenType.DO;
        if (value.matches("\\bwhile\\b")) return TokenType.WHILE;
        if (value.matches("\\bfor\\b")) return TokenType.FOR;
        if (value.matches("\\bprint\\b")) return TokenType.PRINT;
        if (value.matches(">=")) return TokenType.GTE;
        if (value.matches(">")) return TokenType.GT;
        if (value.matches("==")) return TokenType.EQ;
        if (value.matches("\\+")) return TokenType.PLUS;
        if (value.matches("\\*")) return TokenType.MULT;
        if (value.matches("=")) return TokenType.ASSIGN;
        if (value.matches(";")) return TokenType.SEMI;
        if (value.matches(",")) return TokenType.COMMA;
        if (value.matches("\\(")) return TokenType.LPAREN;
        if (value.matches("\\)")) return TokenType.RPAREN;
        if (value.matches("\\{")) return TokenType.LBRACE;
        if (value.matches("\\}")) return TokenType.RBRACE;
        if (value.matches("\\btrue\\b|\\bfalse\\b")) return TokenType.BOOL_LITERAL;
        if (value.matches("[a-zA-Z]+[0-9]*")) return TokenType.Id;
        if (value.matches("\\d+")) return TokenType.Num;
        return TokenType.UNKNOWN;
    }

    public static void main(String[] args) {
        try {
            InputStream inputStream = Main.class.getClassLoader().getResourceAsStream("code.upl");

            if (inputStream == null) {
                System.err.println("File don't exist");
                return;
            }

            String content = new String(inputStream.readAllBytes());

            Lexer lexer = new Lexer(content);
            List<Token> tokens = lexer.tokenize();

           for (Token token : tokens) {
                System.out.println(token);
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
