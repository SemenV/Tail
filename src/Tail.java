import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Tail {
    private static final String NEW_LINE = new String("\n");

    @Option(name = "-c", usage = "countSymbols", forbids = "-n")
    private int countSymbols = 0;

    @Option(name = "-n", usage = "countLines", forbids = "-c")
    private int countLines = 0;

    @Option(name = "-o", metaVar = "String", usage = "fileOutName")
    private String fileOut = "";

    @Argument(usage = "Files input", metaVar = "List<String>")
    private List<String> libInFile = new ArrayList<>();

    public static void main(String[] args) {
        new Tail().start(args);
    }

    private void start(String[] args) {
        CmdLineParser parser = new CmdLineParser(this);
        try {
            parser.parseArgument(args);
        } catch (CmdLineException e) {
            System.err.println(e.getMessage());
            System.err.println("java -jar TailJava.jar [-c num|-n num] [-o ofile] file0 file1 file2 …");
            parser.printUsage(System.out);
            System.exit(1);
        }
        try {
            StringBuilder res = new StringBuilder();
            InputStream in = System.in;
            switch (libInFile.size()) {
                case 0:
                    res.append(enterChoice(in));
                    break;
                case 1:
                    res.append(enterChoice(new FileInputStream(new File(libInFile.get(0)))));
                    break;
                default:
                    for (String e : libInFile) {
                        File file = new File(e);
                        in = new FileInputStream(file);
                        res.append(file.getName() + NEW_LINE + enterChoice(in));
                    }
                    break;
            }

            if (fileOut == "") System.out.println(res.toString());
            else writeInFile(fileOut, res.toString());
        } catch (IOException e) {
            e.getStackTrace();
        }
    }

    private String enterChoice(InputStream in) {
        if (countLines == 0 && countSymbols == 0) countLines = 10;
        if (countLines != 0) return lines(in);
        return symbols(in);
    }

    private void writeInFile(String str, String content) {
        try {
            File file = new File(str);
            FileWriter write = new FileWriter(file);
            write.write(content);
            write.close();
        } catch (Exception e) {
            System.out.print(e.toString());
        }
    }

    private String lines(InputStream in) {
        Scanner read = new Scanner(new InputStreamReader(in));
        List<String> lines = new LinkedList<>();
        while (read.hasNext()) {
            lines.add(read.nextLine() + NEW_LINE);
            if (lines.size() > countLines) lines.remove(0);
        }
        StringBuilder str = new StringBuilder();
        for (String line : lines) {
            str.append(line);
        }
        return str.toString();
    }

    private String symbols(InputStream in) {
        Scanner read = new Scanner(new InputStreamReader(in));
        StringBuilder stringSymbols = new StringBuilder();
        while (read.hasNext()) {
            stringSymbols.append(read.nextLine() + NEW_LINE);
            if (stringSymbols.length() > countSymbols)
                stringSymbols.delete(0, stringSymbols.length() - countSymbols);
        }
        return stringSymbols.toString();
    }
}