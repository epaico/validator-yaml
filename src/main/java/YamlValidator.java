import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class YamlValidator {
    public static void main(String[] args) throws IOException {

        Path path = Paths.get(Constants.URL_FOLDER);

        if (!Files.exists(path) || !Files.isDirectory(path)) {
            System.out.println("No es un directorio");
            return;
        }

        if (!FiletUtil.existsFilesYaml(path)) {
            System.out.println("No existen yaml");
            return;
        }
        List<Path> yamlFiles = new ArrayList<>();
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(path)) {
            stream.forEach(p -> {
                if (FiletUtil.isYaml(p)) {
                    yamlFiles.add(p);
                }
            });

        }

        int validCount = 0;
        int invalidCount = 0;

        // Validar cada archivo encontrado
        for (Path p : yamlFiles) {
            String fileName = p.getFileName().toString();
            System.out.println("\n-- INICIO DE VALIDACIÓN ARCHIVO " + fileName + " --");
            ValidationResult result = FiletUtil.validateYaml(p);

            if (result.errors().isEmpty()) {
                validCount++;
                System.out.println("💚 Archivo Yaml Correcto ");
            } else {
                invalidCount++;
                result.errors().forEach(System.out::println);
            }
            System.out.println("-- FIN DE VALIDACIÓN ARCHIVO " + fileName + " --");
        }

        // Mostrar resumen
        System.out.println("\n=== Resumen de validación ===");
        System.out.println("Total de archivos procesados: " + yamlFiles.size());
        System.out.println("Archivos válidos: " + validCount);
        System.out.println("Archivos inválidos: " + invalidCount);

        // Código de salida: 0 si todos son válidos, 1 si hay algún error
        System.exit(invalidCount > 0 ? 1 : 0);
    }


}
