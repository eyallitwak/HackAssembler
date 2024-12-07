import java.io.File;

public class Main {
    public static void main(String[] args) {
       //check for a single input
        if (args.length != 1) {
            System.out.println("Program requires exactly 1 <path>/file.asm as input.");
            return;
        }
        String filePath = args[0];

        //check that input has .asm suffix
        if (!filePath.endsWith(".asm")) {
            System.out.println("Please input a .asm file.");
            return;
        }

        File file = new File(filePath);

        //check that input file actually exists
        if (!file.isFile()) {
            System.out.println("Error: input file doesn't exist or is a directory.");
            return;
        }

        //get input's path and create a File object as <path>/filename.hack
        String name = file.getAbsolutePath().substring(0, file.getAbsolutePath().lastIndexOf("."));
        name = name.concat(".hack");
        File hackFile =new File(name);

    }
}
