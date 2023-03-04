package io.paradaux.util;

import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

import java.io.*;

public class IOUtils {

    // Credit to chatgpt writing this for me -- I was too lazy
    public static String getFileContentsAsString(String file) {
        // Get the path of the resource file
        InputStream inputStream = ConfigHandler.class.getClassLoader().getResourceAsStream(file);

        if (inputStream == null) {
            throw new IllegalStateException("File does not exist");
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            StringBuilder stringBuilder = new StringBuilder();
            String line;

            // Read the file content line by line and append it to the StringBuilder
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
                stringBuilder.append(System.getProperty("line.separator"));
            }

            // Convert the StringBuilder to a String and return
            return stringBuilder.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Copies the configuration file from the JAR to the current directory on first run.
     */
    public static void deployFiles(Logger logger) {
        String jarLocation = System.getProperty("user.dir");

        if (!new File("config.json").exists()) {
            try {
                exportResource("/config.json", jarLocation + "/config.json");
            } catch (Exception exception) {
                logger.error("Failed to deploy config.json\n", exception);
            }
        }
    }

    /**
     * Export a resource embedded into a Jar file to the local file path.
     *
     * @param inputPath ie.: "/configuration.yml" N.B / is a directory down in the "jar tree" been the jar the root of the tree
     */
    public static void exportResource(String inputPath, @Nullable String outputPath) {

        OutputStream resourceOutputStream;

        try (InputStream resourceStream = IOUtils.class.getResourceAsStream(inputPath)) {
            resourceOutputStream = new FileOutputStream(outputPath);

            if (resourceStream == null) {
                throw new FileNotFoundException("Cannot get resource \"" + inputPath + "\" from Jar file.");
            }

            int readBytes;
            byte[] buffer = new byte[4096];

            while ((readBytes = resourceStream.read(buffer)) > 0) {
                resourceOutputStream.write(buffer, 0, readBytes);
            }

            resourceOutputStream.close();
        } catch (IOException exception) {
            throw new RuntimeException("Failed to deploy resource : " + exception.getMessage());
        }
    }
}
