import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Program requires exactly 1 <path>/file.asm as input.");
            return;
        }
        String filePath = args[0];

        if (!filePath.endsWith(".asm")) {
            System.out.println("Please input a .asm file.");
            return;
        }

        File file = new File(filePath);

        if (!file.isFile()) {
            System.out.println("Error: input file doesn't exist or is a directory.");
            return;
        }

        System.out.println(file.getAbsolutePath());
        String name = file.getAbsolutePath().substring(0, file.getAbsolutePath().lastIndexOf("."));
        name = name.concat(".hack");
        File destFile =new File(name);
        try {
            destFile.createNewFile();
        } catch (IOException e) {
            System.out.println("OOOF");
            e.printStackTrace();
        }

        System.out.println(name);
    }
}
