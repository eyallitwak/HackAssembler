import java.io.File;

public class HackAssembler {
    public static void firstPass(File sourceFile) {
        Parser parser = new Parser(sourceFile);
        SymbolTable table = new SymbolTable();

        while (parser.hasMoreLines()) {
            String type = parser.instructionType();
            if (type.charAt(0) == 'A' || type.charAt(0) == 'L') {
                String symbol = parser.symbol();
                table.addEntry(symbol);
            }
            parser.advance();
        }
        parser.close();
        for (String string : table.keySet()) {
            System.out.println(string + ": " + table.getAddress(string));
        }
    }

    public static void main(String[] args) {
        firstPass(new File("Prog.asm"));
    }
}
