import java.io.File;
import java.io.FileWriter;

public class HackAssembler {
    public static SymbolTable table = new SymbolTable();

    public static void firstPass(File sourceFile) {
        Parser parser = new Parser(sourceFile);
        int lines = 0, symCount = 0;

        while (parser.hasMoreLines()) {
            String type = parser.instructionType();
            lines++;
            if (type.equals("L_INSTRUCTION")) {
                String symbol = parser.symbol();
                if (!table.contains(symbol)) {
                    symCount++;
                    table.addEntry(symbol, lines - symCount);
                }
            }
            parser.advance();
        }
        parser.close();
    }

    public static void secondPass(File sourceFile) {
        try {
            Parser parser = new Parser(sourceFile);
            // String fileName = sourceFile.getName();
            // fileName = fileName.substring(0, fileName.indexOf('.'));
            // fileName = fileName + ".hack";

            String name = sourceFile.getAbsolutePath().substring(0, sourceFile.getAbsolutePath().lastIndexOf("."));
            name = name.concat(".hack");

            File hackFile = new File(name);

            if (hackFile.exists()) {
                hackFile.delete();
            }
            hackFile.createNewFile();
            FileWriter writer = new FileWriter(hackFile);

            while (parser.hasMoreLines()) {
                String type = parser.instructionType();
                StringBuilder builder = new StringBuilder();
                String instruction = "";
                if (type.equals("C_INSTRUCTION")) {
                    String dest = parser.dest(), comp = parser.comp(), jump = parser.jump();
                    dest = Code.dest(dest);
                    comp = Code.comp(comp);
                    jump = Code.jump(jump);
                    builder.append("111");
                    builder.append(comp);
                    builder.append(dest);
                    builder.append(jump);

                    instruction = builder.toString();
                }
                if (type.equals("A_INSTRUCTION")) {
                    int numericValue;
                    try {
                        numericValue = Integer.parseInt(parser.symbol());
                    } catch (NumberFormatException e) {
                        numericValue = -1;
                    }

                    if (numericValue == -1) {
                        // instruction = Integer.toBinaryString(numericValue);
                        String symbol = parser.symbol();
                        if (!table.contains(symbol)) {
                            table.addEntry(symbol);
                        }
                        numericValue = table.getAddress(symbol);
                    }

                    instruction = Integer.toBinaryString(numericValue);
                    int length = instruction.length();
                    for (int i = 0; i < 16 - length; i++) {
                        instruction = "0" + instruction;
                    }
                }

                if (type.equals("A_INSTRUCTION") || type.equals("C_INSTRUCTION")) {
                    writer.write(instruction);
                    writer.write('\n');
                }

                parser.advance();
            }

            parser.close();
            writer.close();
        } catch (Exception e) {
            System.out.println("Error on second pass. Good luck.");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        File sourceFile = new File("MaxL.asm");
        firstPass(sourceFile);
        secondPass(sourceFile);

        for (String symbol : table.keySet()) {
            System.out.println(symbol + " : " + table.getAddress(symbol));
        }
    }
}
