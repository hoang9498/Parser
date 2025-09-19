package parser;
import java.util.*;
public class FirstFollowSet {
	private final Map<String, Set<String>> first = new HashMap<>();
    private final Map<String, Set<String>> follow = new HashMap<>();

    public FirstFollowSet() {
        initFirst();
        initFollow();
    }

    private void initFirst() {
        first.put("Program", Set.of("begin"));
        first.put("END", Set.of("end"));
        first.put("Declaration", Set.of("bool", "int"));
        first.put("DecTail", Set.of("=", "ε"));
        first.put("DecVal", Set.of("true", "false", "Num", "(", "Id"));
        first.put("Statements", Set.of("print", "bool", "int", "for", "Id", "do", "if", "ε"));
        first.put("Statement", Set.of("print", "bool", "int", "for", "Id", "do", "if"));
        first.put("Assignment", Set.of("Id"));
        first.put("AssignTail", Set.of("true", "false", "Num", "(", "Id","++", "--"));
        first.put("AssignVal", Set.of("true", "false", "Num", "(", "Id"));
        first.put("Block", Set.of("{"));
        first.put("ParenComp", Set.of("("));
        first.put("IfStatement", Set.of("if"));
        first.put("ElsePart", Set.of("else", "ε"));
        first.put("Loop", Set.of("for", "do"));
        first.put("DoWhile", Set.of("do"));
        first.put("ForLoop", Set.of("for"));
        first.put("ForInit", Set.of("Id", "true", "false"));
        first.put("ForCond", Set.of("Num", "(", "Id"));
        first.put("ForUpdate", Set.of("Id"));
        first.put("Print", Set.of("print"));
        first.put("Comparison", Set.of("Num", "(", "Id"));
        first.put("CompTail", Set.of("==", ">", ">=", "ε"));
        first.put("CompOp", Set.of("==", ">", ">="));
        first.put("GT", Set.of(">"));
        first.put("GE", Set.of(">="));
        first.put("EQ", Set.of("=="));
        first.put("Multiplication", Set.of("Num", "(", "Id"));
        first.put("MulTail", Set.of("*", "ε"));
        first.put("Factor", Set.of("Num", "(", "Id"));
        first.put("Type", Set.of("bool", "int"));
        first.put("Boolean", Set.of("true", "false"));
        first.put("Addition", Set.of("Num", "(", "Id"));
        first.put("AddTail", Set.of("+", "ε"));
        first.put("Change", Set.of("++", "--"));
        first.put("Increase", Set.of("++"));
        first.put("Decrease", Set.of("--"));
    }

    private void initFollow() {
        follow.put("Program", Set.of("$"));
        follow.put("END", Set.of("$"));
        follow.put("Declaration", Set.of(";"));
        follow.put("DecTail", Set.of(";"));
        follow.put("DecVal", Set.of(";"));
        follow.put("Statements", Set.of("}","end"));
        follow.put("Statement", Set.of("Id", "int", "bool", "print", "if", "do", "for", "}", "end"));
        follow.put("Assignment", Set.of(")", ";"));
        follow.put("AssignTail", Set.of(")", ";"));
        follow.put("AssignVal", Set.of(")", ";"));
        follow.put("Change", Set.of(")", ";"));
        follow.put("Increase", Set.of(")", ";"));
        follow.put("Decrease", Set.of(")", ";"));
        follow.put("IfStatement", Set.of("Id", "int", "bool", "print", "if", "do", "for", "}", "end"));
        follow.put("ParenComp", Set.of("then",";"));
        follow.put("ElsePart", Set.of("Id", "int", "bool", "print", "if", "do", "for", "}", "end"));
        follow.put("Loop", Set.of("Id", "int", "bool", "print", "if", "do", "for", "}", "end"));
        follow.put("DoWhile", Set.of("Id", "int", "bool", "print", "if", "do", "for", "}", "end"));
        follow.put("Block", Set.of("Id", "int", "bool", "print", "if", "do", "for", "}", "end", "else"));   //
        follow.put("ForLoop", Set.of("Id", "int", "bool", "print", "if", "do", "for", "}", "end"));
        follow.put("ForInit", Set.of("Num", "(", "Id"));
        follow.put("ForCond", Set.of("Id"));
        follow.put("ForUpdate", Set.of(")"));
        follow.put("Comparison", Set.of(")", ";"));
        follow.put("CompTail", Set.of(")", ";"));
        follow.put("CompOp", Set.of("Num", "(", "Id"));
        follow.put("GT", Set.of("Num", "(", "Id"));
        follow.put("GE", Set.of("Num", "(", "Id"));
        follow.put("EQ", Set.of("Num", "(", "Id"));
        follow.put("Addition", Set.of(")", ";", ">", ">=", "=="));
        follow.put("AddTail", Set.of(")", ";", ">", ">=", "=="));
        follow.put("Multiplication", Set.of(")", ";", "+", ">", ">=", "=="));
        follow.put("MulTail", Set.of(")", ";", "+", ">", ">=", "=="));
        follow.put("Factor", Set.of("*", ")", ";", "+", ">", ">=", "=="));
        follow.put("Print", Set.of(";"));
        follow.put("Boolean", Set.of(")", ";"));
        follow.put("Type", Set.of("Id"));


    }

    private Set<String> set(String... symbols) {
        return new HashSet<>(Arrays.asList(symbols));
    }

    public Set<String> getFirst(String nonTerminal) {
        return first.getOrDefault(nonTerminal, Collections.emptySet());
    }

    public Set<String> getFollow(String nonTerminal) {
        return follow.getOrDefault(nonTerminal, Collections.emptySet());
    }

    public Map<String, Set<String>> getAllFirst() {
        return Collections.unmodifiableMap(first);
    }

    public Map<String, Set<String>> getAllFollow() {
        return Collections.unmodifiableMap(follow);
    }
}
