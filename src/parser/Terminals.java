package parser;
import java.util.*;
public class Terminals {
	public static final Set<String> TERMINALS = Set.of(
            "begin", "end",  "ε","Num", "(", ")", "Id", "=", "print", "bool", "int",
            "for", "do", "while", "if", "then","else", "==", ">", ">=", "*","+",
            "true", "false",   "{", "}", ";",
               "++", "--","$"
    );
}
