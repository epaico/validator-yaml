import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.error.YAMLException;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class FiletUtil {

    public static boolean existsFilesYaml(Path path) {
        try (Stream<Path> files = Files.list(path)) {
            var filesYml = files
                    .filter(Files::isRegularFile)
                    .filter(FiletUtil::isYaml)
                    .toList();
            return !filesYml.isEmpty();
        } catch (IOException e) {
            return false;
        }
    }

    public static boolean isYaml(Path path) {
        String nameFile = path.getFileName().toString().toLowerCase();
        return Files.isRegularFile(path) &&
                (nameFile.endsWith(".yaml") || nameFile.endsWith(".yml"));
    }

    public static ValidationResult validateYaml(Path path) {
        String fileName = path.getFileName().toString();
        List<String> errors = new ArrayList<>();
        try (InputStream inputStream = Files.newInputStream(path)) {
            Yaml yaml = new Yaml();
            Object object = yaml.load(inputStream);
            if (object == null) {
                errors.add("❌ YAML no válido. " + fileName);
            } else {
                validateNode(object, "", errors);
            }
            return new ValidationResult(errors);
        } catch (YAMLException e) {
            errors.add("❌ Error de validación en " + fileName + ":\n" + e.getMessage());
            return new ValidationResult(errors);
        } catch (IOException e) {
            errors.add("❌ Error al leer el archivo " + fileName + ":\n" + e.getMessage());
            return new ValidationResult(errors);
        } catch (Exception e) {
            errors.add("❌ Error al procesar el archivo " + fileName + ":\n" + e.getMessage());
            return new ValidationResult(errors);
        }

    }

    private static void validateNode(Object nodo, String path, List<String> errors) {
        if (nodo == null) {
            errors.add("❌ Clave sin valor en: " + path);
            return;
        }

        if (nodo instanceof Map<?, ?> map) {
            if (map.isEmpty()) {
                errors.add("❌ Objeto vacío en: " + path);
                return;
            }
            for (Map.Entry<?, ?> entry : map.entrySet()) {
                String newPath = path + "." + entry.getKey();
                validateNode(entry.getValue(), newPath, errors);
            }
        } else if (nodo instanceof List<?> list) {
            if (list.isEmpty()) {
                errors.add("❌ Lista vacía en: " + path);
                return;
            }
            for (int i = 0; i < list.size(); i++) {
                String newPath = path + "[" + i + "]";
                validateNode(list.get(i), newPath, errors);
            }
        }

    }
}
