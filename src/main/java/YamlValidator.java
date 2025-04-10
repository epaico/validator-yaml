import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class YamlValidator {
    public static void main(String[] args) {

        Path path = Paths.get(Constants.URL_FOLDER);

        if (Files.exists(path)){
            System.out.println("si existe directorio");
        }else{
            System.out.println("no existe directorio");
        }
    }
}
