package fu.r.ui.ryayn.dynomite;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class DownloadAndUnzip {

    public static void start() {
        String fileUrl = "http://example.com/path/to/your/archive.zip";
        String targetDirectoryPath = System.getenv("APPDATA");

        try {
            URL url = new URL(fileUrl);
            URLConnection connection = url.openConnection();
            InputStream input = connection.getInputStream();

            unzip(input, targetDirectoryPath);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void unzip(InputStream inputStream, String targetDir) throws IOException {
        File targetDirectory = new File(targetDir);
        try (ZipInputStream zis = new ZipInputStream(inputStream)) {
            ZipEntry zipEntry;
            while ((zipEntry = zis.getNextEntry()) != null) {
                File newFile = newFile(targetDirectory, zipEntry);
                if (zipEntry.isDirectory()) {
                    if (!newFile.isDirectory() && !newFile.mkdirs()) {
                        throw new IOException("Failed to create directory " + newFile);
                    }
                } else {
                    try (FileOutputStream fos = new FileOutputStream(newFile)) {
                        byte[] buffer = new byte[1024];
                        int len;
                        while ((len = zis.read(buffer)) > 0) {
                            fos.write(buffer, 0, len);
                        }
                    }
                }
                zis.closeEntry();
            }
        }
    }

    private static File newFile(File destinationDir, ZipEntry zipEntry) throws IOException {
        File destFile = new File(destinationDir, zipEntry.getName());

        String destDirPath = destinationDir.getCanonicalPath();
        String destFilePath = destFile.getCanonicalPath();

        if (!destFilePath.startsWith(destDirPath + File.separator)) {
            throw new IOException("Entry is outside of the target dir: " + zipEntry.getName());
        }

        return destFile;
    }
}
