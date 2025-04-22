# 🧪 validator-yaml

Una utilidad desarrollada en Java 17 que permite validar archivos YAML (.yaml o .yml) de forma sencilla desde la línea de comandos. Ideal para entornos de CI/CD o para desarrolladores que quieran asegurarse de que sus archivos YAML están bien estructurados antes de su uso.

---

## 🚀 Características

- ✔️ Soporte para archivos `.yaml` y `.yml`
- ✔️ Validación rápida desde la terminal
- ✔️ Compatible con Java 17
- ✔️ Proyecto portable y fácil de integrar en flujos de desarrollo

---

## 🛠️ Requisitos

- Java 17 o superior
- Maven 3.8+ (solo si deseas compilar el proyecto)

---

## ⚙️ Compilación

Para compilar el proyecto y generar el archivo `.jar`, ejecuta:

```bash
mvn clean package
```
## Creación de directorio yaml
- Crea un directorio `yaml` en el mismo directorio donde se encuentra el .jar generado
- Dentro del directorio creado agrega los archivos `.yaml` y `.yml`

## Ejecución

```bash
java -jar validator-yaml-1.0-SNAPSHOT.jar

```
