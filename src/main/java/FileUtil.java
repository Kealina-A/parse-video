import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

public class FileUtil {
    public static List<String> getFiles(String path) {
        List<File> files = Arrays.asList(new File(path).listFiles());
        files.sort((o1, o2) -> {
            if (o1.isDirectory() && o2.isFile()) {
                return -1;
            }
            if (o1.isFile() && o2.isDirectory()) {
                return 1;
            }
            Integer f = f(o1.getName());
            Integer f2 = f(o2.getName());
            return Integer.compare(f, f2);
//            return o1.getName().compareTo(o2.getName());

        });
        return files.stream().map(File::toString).collect(Collectors.toList());
    }

    static Integer f(String filename) {
        int x = filename.indexOf(".");
        String string2 = filename.substring(0, x);
        char[] cs = string2.toCharArray();
        StringBuilder builder = new StringBuilder();
        for (char c : cs) {
            if (Character.isDigit(c)) {
                builder.append(c);
            }
        }
        return Integer.parseInt(builder.toString());
    }
}
