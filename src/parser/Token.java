package parser;

public class Token {
	TokenType type;
    String value;

    Token(TokenType type, String value) {
        this.type = type;
        this.value = value;
    }

    public String getValue() {
        return value;
    }
    public TokenType getType() {
        return type;
    }
    public String ChooseTokenPart() {
        String result;
        if (type == TokenType.COMMENT_BLOCK||type == TokenType.COMMENT_LINE||type == TokenType.NEWLINE
                ||type == TokenType.Id||type == TokenType.Num) {
            result =getType().toString();
        }else{
            result = getValue();
        }

        return result;
    }

    @Override
    public String toString() {
        return "Token{" + "type=" + type + ", value='" + value + "'}";
    }
}
