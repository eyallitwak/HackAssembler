import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Parser {
    // private final File file;
    private BufferedReader reader;
    private String currentInstruction;

    public Parser(File file) {
        // this.file=file;
        try {
            this.reader = new BufferedReader(new FileReader(file));
            this.currentInstruction = this.reader.readLine();
        } catch (Exception e) {
            System.out.println("Error while initializing Parser");
            e.printStackTrace();
        }
    }

    public boolean hasMoreLines() {
        try {
            return this.reader.ready();
        } catch (IOException e) {
            System.out.println("Error while reading file");
            e.printStackTrace();
            return false;
        }
    }

    public boolean advance() {
        try {
            this.currentInstruction = this.reader.readLine();
            return true;
        } catch (Exception e) {
            System.out.println("Error while advancing the instruction");
            e.printStackTrace();
            return false;
        }
    }

    public final String instructionType() {
        char first = this.currentInstruction.charAt(0);
        switch (first) {
            case '@':
                return "A_INSTRUCTION";
            case '(':
                return "L_INSTRUCTION";
        }
        return "C_INSTRUCTION";
    }

    public String symbol() {
        final String inst = instructionType();
        if (inst.equals("A_INSTRUCTION")) {
            return this.currentInstruction.substring(1);
        } else {
            return this.currentInstruction.substring(1, currentInstruction.length() - 1);
        }
    }

    public String dest() {
        int equalIndex = this.currentInstruction.indexOf('=');
        if (equalIndex == -1) {
            return null;
        } else {
            return this.currentInstruction.substring(0, equalIndex);
        }
    }

    public String jump() {
        int semicolon = this.currentInstruction.indexOf(';');
        if (semicolon == -1) {
            return null;
        } else {
            return this.currentInstruction.substring(semicolon + 1);
        }
    }

    public String comp() {
        int equalIndex = this.currentInstruction.indexOf('=');
        String modifiedInstruction = (equalIndex == -1) ? currentInstruction
                : currentInstruction.substring(equalIndex + 1);
        int semicolon = modifiedInstruction.indexOf(';');
        modifiedInstruction = (semicolon == -1) ? modifiedInstruction : modifiedInstruction.substring(0, semicolon);

        return modifiedInstruction;
    }

    public void close() {
        try {
            this.reader.close();
        } catch (IOException e) {
            System.out.println("Error while closing Parser");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Parser parser = new Parser(new File("Prog.asm"));
        while (parser.hasMoreLines()) {
            System.out.println(parser.instructionType());
            parser.advance();
        }
        parser.close();
    }
}
