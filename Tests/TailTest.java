import org.junit.Assert;
import org.junit.Test;
//junit4

import java.io.File;
import java.io.FileInputStream;
import java.util.Scanner;

public class TailTest {
    private static final String source = "JarFileTail/SourceAndResult/";
    private static final String exitProgFile = "JarFileTail/ProgramResult/";

    private void assertContentFile(String expectedResult, String programRes) {
        try {
            File pathResult = new File(source + expectedResult);
            File expect = new File(exitProgFile + programRes);
            Scanner first = new Scanner(new FileInputStream(expect));
            Scanner second = new Scanner(new FileInputStream(pathResult));

            Assert.assertTrue(expect.length() == pathResult.length());
            while (first.hasNext()) {
                Assert.assertTrue(first.nextLine().equals(second.nextLine()));
            }
        } catch (Exception e) {
            System.out.print(e.toString());
            Assert.fail();
        }
    }

    @Test
    public void TailTestF1() {
        String[] param = {"-n", "3", "-o", exitProgFile + "test1program.txt", source + "test1.txt"};
        Tail.main(param);
        assertContentFile("test1result.txt", "test1program.txt");
    }

    @Test
    public void TailTestF2() {
        String[] param = {"-n", "3", "-o", exitProgFile + "test2program.txt", source + "test2.txt"};
        Tail.main(param);
        assertContentFile("test2result.txt", "test2program.txt");
    }

    @Test
    public void TailTestF3() {
        String[] param = {"-c", "21", "-o", exitProgFile + "test3program.txt", source + "test3.txt"};
        Tail.main(param);
        assertContentFile("test3result.txt", "test3program.txt");
    }

    @Test
    public void TailTestF4() {
        String[] param = {"-c", "21", "-o", exitProgFile + "test4program.txt", source + "test4.txt"};
        Tail.main(param);
        assertContentFile("test4result.txt", "test4program.txt");
    }

    @Test
    public void TailTestF5() {
        String[] param = {"-c", "21", "-o", exitProgFile + "test5program.txt", source + "test4.txt", source + "test3.txt"};
        Tail.main(param);
        assertContentFile("test5result.txt", "test5program.txt");
    }

    @Test
    public void TailTestF6() {
        String[] param = {"-o", exitProgFile + "test6program.txt", source + "test4.txt", source + "test6.txt"};
        Tail.main(param);
        assertContentFile("test6result.txt", "test6program.txt");
    }
}
