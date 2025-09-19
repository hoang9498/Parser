package parser;
import java.util.*;
public class PredictiveParsingTable {
	private final Map<String, List<List<String>>> grammar;
    private final Map<String, Set<String>> first;
    private final Map<String, Set<String>> follow;
    private final Map<String, Map<String, List<String>>> table = new LinkedHashMap<>();

    public PredictiveParsingTable(Map<String, List<List<String>>> grammar,
                                  Map<String, Set<String>> first,
                                  Map<String, Set<String>> follow) {
        this.grammar = grammar;
        this.first = first;
        this.follow = follow;
        constructTable();
    }

    private void constructTable() {
        for (String A : grammar.keySet()) {
            table.put(A, new LinkedHashMap<>());

            for (List<String> production : grammar.get(A)) {
                Set<String> firstSet = new LinkedHashSet<>();
                if (!production.isEmpty()) {
                    String firstSymbol = production.getFirst();
                    if (Terminals.TERMINALS.contains(firstSymbol)) {
                        firstSet = Set.of(firstSymbol);
                    }
                    else{
                        firstSet = first.getOrDefault(firstSymbol, Set.of()); // first is initialize as
                    }                                                         

                } else {
                    System.out.println("The list is empty.");
                }
                for (String terminal : firstSet) {
                    if (!terminal.equals("ε")) {
                        table.get(A).put(terminal, production);
                    }
                }

                if (firstSet.contains("ε")) {
                    for (String b : follow.get(A)) {
                        table.get(A).put(b, production);
                    }
                }
            }

        }
        for (String A : table.keySet() ){
            // Error recovery: add sync entries
            for (String b : follow.get(A)) {
                table.get(A).putIfAbsent(b, List.of("sync"));
            }
        }
    }

    public void printTable() {
        System.out.println("\nPredictive Parsing Table (M[A, a]):");
        for (String A : table.keySet()) {
            for (String a : table.get(A).keySet()) {
                System.out.println("M[" + A + ", " + a + "] = " + A + " → "
                        + String.join(" ", table.get(A).get(a)));
            }
        }
    }

    public Map<String, Map<String, List<String>>> getTable() {
        return table;
    }

    public List<String> getCell(String nonter, String input) {
        List<String> production = table.get(nonter).get(input);
        return production;
    }

    // Example main for full pipeline
    public static void main(String[] args) {
        Map<String, List<List<String>>> grammar = new LinkedHashMap<>();
        FirstFollowSet ff = new FirstFollowSet();

        PredictiveParsingTable ppt = new PredictiveParsingTable(
                grammar,
                ff.getAllFirst(),
                ff.getAllFollow()
        );

        ppt.printTable();
    }
}
