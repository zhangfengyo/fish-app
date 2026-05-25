package com.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * File helpers used by upload and AI attachment flows.
 */
@Slf4j
public class FileUtil {
    private static final String APP_HOME_ENV = "APP_HOME";
    private static final String APP_FILES_DIR_ENV = "APP_FILES_DIR";
    private static final String DEFAULT_FILES_DIR_NAME = "appdata";
    private static final String UPLOAD_DIR_NAME = "upload";
    private static final String OUTPUT_DIR_NAME = "output";

    public static byte[] FileToByte(File file) throws IOException {
        try (InputStream content = new FileInputStream(file);
             ByteArrayOutputStream swapStream = new ByteArrayOutputStream()) {
            byte[] buff = new byte[4096];
            int rc;
            while ((rc = content.read(buff)) > 0) {
                swapStream.write(buff, 0, rc);
            }
            return swapStream.toByteArray();
        }
    }

    public static String getUploadDir() {
        return ensureDirectoryExists(getFilesRoot().resolve(UPLOAD_DIR_NAME)).toString();
    }

    public static String getOutputDir() {
        return ensureDirectoryExists(getFilesRoot().resolve(OUTPUT_DIR_NAME)).toString();
    }

    public static void ensureDirectoryExists(String dirPath) {
        ensureDirectoryExists(Paths.get(dirPath));
    }

    public static String saveUploadFile(byte[] fileData, String fileName) throws IOException {
        String safeFileName = Paths.get(fileName == null ? "" : fileName).getFileName().toString();
        if (safeFileName.trim().isEmpty()) {
            throw new IllegalArgumentException("File name cannot be empty");
        }
        String uniqueFileName = System.currentTimeMillis() + "_" + safeFileName;
        Path filePath = getUploadPath(uniqueFileName);
        Files.write(filePath, fileData);
        return uniqueFileName;
    }

    public static boolean deleteFile(String filePath) {
        try {
            Path resolvedPath = resolveUploadPath(filePath);
            return resolvedPath != null && Files.deleteIfExists(resolvedPath);
        } catch (Exception e) {
            log.error("Failed to delete file: {}", filePath, e);
            return false;
        }
    }

    public static String normalizeUploadPath(String value) {
        if (value == null) {
            return null;
        }
        String path = value.trim();
        if (path.isEmpty() || path.startsWith("http://") || path.startsWith("https://")) {
            return path;
        }
        path = path.replace("\\", "/");
        path = path.replaceFirst("^/+", "");
        path = path.replaceFirst("^(upload/)+", "");
        return path;
    }

    public static String getFileExtension(String fileName) {
        if (fileName == null || !fileName.contains(".")) {
            return "";
        }
        return fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
    }

    public static boolean isImageFile(String fileName) {
        if (fileName == null) {
            return false;
        }
        String ext = getFileExtension(fileName);
        return "jpg".equals(ext) || "jpeg".equals(ext) || "png".equals(ext);
    }

    public static boolean isVideoFile(String fileName) {
        if (fileName == null) {
            return false;
        }
        String ext = getFileExtension(fileName);
        return "mp4".equals(ext) || "avi".equals(ext) || "mov".equals(ext);
    }

    public static File getFile(String fileName) {
        if (fileName == null || fileName.trim().isEmpty()) {
            return null;
        }

        String normalizedName = normalizeUploadPath(fileName);
        if (normalizedName == null || normalizedName.isEmpty() || isRemotePath(normalizedName)) {
            return null;
        }

        Path uploadFile = resolveUploadPath(normalizedName);
        if (uploadFile != null && Files.exists(uploadFile)) {
            return uploadFile.toFile();
        }

        return null;
    }

    public static Path getUploadPath(String fileName) {
        Path resolvedPath = resolveUploadPath(fileName);
        if (resolvedPath == null) {
            throw new IllegalArgumentException("Upload file name cannot be empty");
        }
        return resolvedPath;
    }

    public static Path getOutputPath(String fileName) {
        return getFilesRoot().resolve(OUTPUT_DIR_NAME).resolve(fileName).normalize();
    }

    public static Path getFilesRoot() {
        String explicitDir = System.getenv(APP_FILES_DIR_ENV);
        if (explicitDir != null && !explicitDir.trim().isEmpty()) {
            return ensureDirectoryExists(Paths.get(explicitDir.trim()).toAbsolutePath().normalize());
        }

        return ensureDirectoryExists(getAppHome().resolve(DEFAULT_FILES_DIR_NAME));
    }

    public static Path getAppHome() {
        String explicitHome = System.getenv(APP_HOME_ENV);
        if (explicitHome != null && !explicitHome.trim().isEmpty()) {
            return Paths.get(explicitHome.trim()).toAbsolutePath().normalize();
        }

        try {
            Path jarPath = Paths.get(FileUtil.class.getProtectionDomain().getCodeSource().getLocation().toURI());
            if (Files.isRegularFile(jarPath)) {
                return jarPath.getParent();
            }
            return jarPath;
        } catch (Exception e) {
            Path fallback = Paths.get("").toAbsolutePath().normalize();
            log.warn("Falling back to working directory for APP_HOME: {}", fallback, e);
            return fallback;
        }
    }

    private static Path ensureDirectoryExists(Path dirPath) {
        try {
            Files.createDirectories(dirPath);
        } catch (IOException e) {
            throw new IllegalStateException("Failed to create directory: " + dirPath, e);
        }
        return dirPath;
    }

    private static Path resolveUploadPath(String fileName) {
        String normalizedName = normalizeUploadPath(fileName);
        if (normalizedName == null || normalizedName.isEmpty() || isRemotePath(normalizedName)) {
            return null;
        }

        Path uploadRoot = ensureDirectoryExists(getFilesRoot().resolve(UPLOAD_DIR_NAME)).toAbsolutePath().normalize();
        Path resolvedPath = uploadRoot.resolve(normalizedName).normalize();
        if (!resolvedPath.startsWith(uploadRoot)) {
            throw new IllegalArgumentException("Upload path must stay within the upload directory");
        }
        return resolvedPath;
    }

    private static boolean isRemotePath(String path) {
        return path.startsWith("http://") || path.startsWith("https://");
    }
}
