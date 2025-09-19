package parser;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
public class Main {
	public static void main(String[] args) {
		Map<String, List<List<String>>> grammar = new LinkedHashMap<>();
        //1
        grammar.put("Program", List.of(
                List.of("begin", "Statements", "END")
        ));
        grammar.put("END", List.of(
                List.of("end")
        ));
        //2
        grammar.put("Statements", List.of(
                List.of("Statement", "Statements"),
                List.of("ε")
        ));
        //3
        grammar.put("Statement", List.of(
                List.of("Assignment",";"),
                List.of("Print", ";"),
                List.of("Declaration", ";"),
                List.of("IfStatement"),
                List.of("Loop")
        ));
        //4
        grammar.put("Declaration", List.of(
                List.of("Type", "Id", "DecTail")
        ));
        //5
        grammar.put("DecTail", List.of(
                List.of("=", "DecVal"),
                List.of("ε")
        ));
        //6
        grammar.put("DecVal", List.of(
                List.of("Comparison"),
                List.of("Boolean")
        ));

        //7
        grammar.put("Assignment", List.of(
                List.of("Id","AssignTail")
        ));
        //8
        grammar.put("AssignTail", List.of(
                List.of("=","AssignVal"),
                List.of("Change")
        ));
        //9
        grammar.put("AssignVal", List.of(
                List.of("Comparison"),
                List.of("Boolean")
        ));
        //10
        grammar.put("Change", List.of(
                List.of("Increase"),
                List.of("Decrease")
        ));
        //11
        grammar.put("Increase", List.of(
                List.of("++")
        ));
        //12
        grammar.put("Decrease", List.of(
                List.of("--")
        ));
        //13
        grammar.put("Block", List.of(
                List.of("{", "Statements", "}")
        ));
        //14
        grammar.put("ParenComp", List.of(
                List.of("(", "Comparison", ")")
        ));
        //15
        grammar.put("IfStatement",
                List.of(
                List.of("if", "ParenComp","then", "Block", "ElsePart")
        ));
        //16
        grammar.put("ElsePart", List.of(
                List.of("else", "Block"),
                List.of("ε")
        ));
        //17
        grammar.put("Loop", List.of(
                List.of("DoWhile"),
                List.of("ForLoop")
        ));
        //18
        grammar.put("DoWhile", List.of(
                List.of("do", "Block", "while", "ParenComp",";")
        ));
        //19
        grammar.put("ForLoop", List.of(
                List.of("for", "(", "ForInit", "ForCond", "ForUpdate", ")", "Block")
        ));
        //20
        grammar.put("ForInit", List.of(
                List.of("Assignment", ";"),
                List.of("Declaration", ";")
        ));
        //21
        grammar.put("ForCond", List.of(
                List.of("Comparison",";")
        ));
        //22
        grammar.put("ForUpdate", List.of(
                List.of("Assignment")
        ));

        //23
        grammar.put("Print", List.of(
                List.of("print", "(", "Comparison", ")")
        ));
        //24
        grammar.put("Comparison", List.of(
                List.of("Addition", "CompTail")
        ));
        //25
        grammar.put("CompTail", List.of(
                List.of("CompOp", "Addition"),
                List.of("ε")
        ));
        //26
        grammar.put("CompOp", List.of(
                List.of("GT"),
                List.of("GE"),
                List.of("EQ")
        ));
        //27
        grammar.put("GT", List.of(
                List.of(">")
        ));
        //28
        grammar.put("GE", List.of(
                List.of(">=")
        ));
        //29
        grammar.put("EQ", List.of(
                List.of("==")
        ));
        //30
        grammar.put("Addition", List.of(
                List.of("Multiplication", "AddTail")
        ));
        //31
        grammar.put("AddTail", List.of(
                List.of("+", "Multiplication", "AddTail"),
                List.of("ε")
        ));
        //32
        grammar.put("Multiplication", List.of(
                List.of("Factor", "MulTail")
        ));
        //33
        grammar.put("MulTail", List.of(
                List.of("*", "Factor", "MulTail"),
                List.of("ε")
        ));
        //34
        grammar.put("Factor", List.of(
                List.of("(", "Addition", ")"),
                List.of("Id"),
                List.of("Num")
        ));
        //35
        grammar.put("Type", List.of(
                List.of("int"),
                List.of("bool")
        ));
        //36
        grammar.put("Boolean", List.of(
                List.of("true"),
                List.of("false")
        ));

        FirstFollowSet ff = new FirstFollowSet();

        PredictiveParsingTable ppt = new PredictiveParsingTable(
                grammar,
                ff.getAllFirst(),
                ff.getAllFollow()
        );
        LL1Parser parser = new LL1Parser(ppt,Terminals.TERMINALS,"Program");
        
        try {
            InputStream inputStream = Main.class.getClassLoader().getResourceAsStream("code.txt");

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
            parser.parse(tokens);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
	}
}
