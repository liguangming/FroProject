
package fro.org.froproject.app.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.os.StatFs;
import android.text.TextUtils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.zip.CRC32;
import java.util.zip.CheckedInputStream;
import java.util.zip.Checksum;

public class FileUtils {
    /**
     * 获取文件后缀名
     *
     * @param filename
     * @return 不含"."的小写后缀名，如：png 如果filename为null或空，返回null
     */
    public static String getFileSuffix(String filename) {
        if (TextUtils.isEmpty(filename)) {
            return null;
        }

        int idx = filename.lastIndexOf(".") + 1;
        if (idx <= 0 || idx == filename.length()) {
            return null;
        }

        return filename.substring(idx).toLowerCase();
    }

    public static boolean isExternalStorageAvailable(long size) {
        String state = Environment.getExternalStorageState();

        if (Environment.MEDIA_MOUNTED.equals(state)) {
            File externalStorage = Environment.getExternalStorageDirectory();

            long freeSpace = getFreeSpace(externalStorage.getPath());

            if (freeSpace > size) {
                return true;
            }
        }

        return false;
    }

    public static boolean isInternalStorageAvailable(Context context, long size) {
        File internalStorage = context.getFilesDir();
        long freeSpace = getFreeSpace(internalStorage.getPath());

        return freeSpace > size;

    }

    public static long getFreeSpace(String dir) {

        StatFs statFs = new StatFs(dir);

        // 获取block的SIZE
        long blockSize = statFs.getBlockSize();

        // 可使用的Block的数量
        long availableBlock = statFs.getAvailableBlocks();

        return (availableBlock * blockSize);
    }

    /**
     * Computes the checksum of a file using the CRC32 checksum routine. The
     * value of the checksum is returned.
     *
     * @param file the file to checksum, must not be <code>null</code>
     * @return the checksum value
     * @throws NullPointerException     if the file or checksum is <code>null</code>
     * @throws IllegalArgumentException if the file is a directory
     * @throws IOException              if an IO error occurs reading the file
     * @since Commons IO 1.3
     */
    public static long checksumCRC32(File file) throws IOException {
        CRC32 crc = new CRC32();
        checksum(file, crc);
        return crc.getValue();
    }

    /**
     * Computes the checksum of a file using the specified checksum object.
     * Multiple files may be checked using one <code>Checksum</code> instance if
     * desired simply by reusing the same checksum object. For example:
     * <p>
     * <pre>
     * long csum = FileUtils.checksum(file, new CRC32()).getValue();
     * </pre>
     *
     * @param file     the file to checksum, must not be <code>null</code>
     * @param checksum the checksum object to be used, must not be
     *                 <code>null</code>
     * @return the checksum specified, updated with the content of the file
     * @throws NullPointerException     if the file or checksum is <code>null</code>
     * @throws IllegalArgumentException if the file is a directory
     * @throws IOException              if an IO error occurs reading the file
     * @since Commons IO 1.3
     */
    public static Checksum checksum(File file, Checksum checksum) throws IOException {
        if (file.isDirectory()) {
            throw new IllegalArgumentException("Checksum can't be computed on directories");
        }
        InputStream in = null;
        try {
            in = new CheckedInputStream(new FileInputStream(file), checksum);
            IOUtils.copy(in, new NullOutputStream());
        } finally {
            IOUtils.closeQuietly(in);
        }
        return checksum;
    }

    /**
     * Opens a {@link FileOutputStream} for the specified file, checking and
     * creating the parent directory if it does not exist.
     * <p>
     * At the end of the method either the stream will be successfully opened,
     * or an exception will have been thrown.
     * <p>
     * The parent directory will be created if it does not exist. The file will
     * be created if it does not exist. An exception is thrown if the file
     * object exists but is a directory. An exception is thrown if the file
     * exists but cannot be written to. An exception is thrown if the parent
     * directory cannot be created.
     *
     * @param file the file to open for output, must not be <code>null</code>
     * @return a new {@link FileOutputStream} for the specified file
     * @throws IOException if the file object is a directory
     * @throws IOException if the file cannot be written to
     * @throws IOException if a parent directory needs creating but that fails
     * @since Commons IO 1.3
     */
    public static FileOutputStream openOutputStream(File file) throws IOException {
        if (file.exists()) {
            if (file.isDirectory()) {
                throw new IOException("File '" + file + "' exists but is a directory");
            }
            if (!file.canWrite()) {
                throw new IOException("File '" + file + "' cannot be written to");
            }
        } else {
            File parent = file.getParentFile();
            if (parent != null && !parent.exists()) {
                if (!parent.mkdirs()) {
                    throw new IOException("File '" + file + "' could not be created");
                }
            }
        }
        return new FileOutputStream(file);
    }

    /**
     * This OutputStream writes all data to the famous <b>/dev/null</b>.
     * <p>
     * This output stream has no destination (file/socket etc.) and all bytes
     * written to it are ignored and lost.
     *
     * @author Jeremias Maerki
     * @version $Id: NullOutputStream.java 659817 2008-05-24 13:23:10Z niallp $
     */
    private static class NullOutputStream extends OutputStream {
        /**
         * Does nothing - output to <code>/dev/null</code>.
         *
         * @param b   The bytes to write
         * @param off The start offset
         * @param len The number of bytes to write
         */
        @Override
        public void write(byte[] b, int off, int len) {
            // to /dev/null
        }

        /**
         * Does nothing - output to <code>/dev/null</code>.
         *
         * @param b The byte to write
         */
        @Override
        public void write(int b) {
            // to /dev/null
        }

        /**
         * Does nothing - output to <code>/dev/null</code>.
         *
         * @param b The bytes to write
         * @throws IOException never
         */
        @Override
        public void write(byte[] b) throws IOException {
            // to /dev/null
        }

    }

    /**
     * Deletes a file, never throwing an exception. If file is a directory,
     * delete it and all sub-directories.
     * <p>
     * The difference between File.delete() and this method are:
     * <ul>
     * <li>A directory to be deleted does not have to be empty.</li>
     * <li>No exceptions are thrown when a file or directory cannot be deleted.</li>
     * </ul>
     *
     * @param file file or directory to delete, can be <code>null</code>
     * @return <code>true</code> if the file or directory was deleted, otherwise
     * <code>false</code>
     * @since Commons IO 1.4
     */
    public static boolean deleteQuietly(File file) {
        if (file == null) {
            return false;
        }
        try {
            if (file.isDirectory()) {
                cleanDirectory(file);
            }
        } catch (Exception ignored) {
        }

        try {
            return file.delete();
        } catch (Exception ignored) {
            return false;
        }
    }

    /**
     * Cleans a directory without deleting it.
     *
     * @param directory directory to clean
     * @throws IOException in case cleaning is unsuccessful
     */
    public static void cleanDirectory(File directory) throws IOException {
        if (!directory.exists()) {
            String message = directory + " does not exist";
            throw new IllegalArgumentException(message);
        }

        if (!directory.isDirectory()) {
            String message = directory + " is not a directory";
            throw new IllegalArgumentException(message);
        }

        File[] files = directory.listFiles();
        if (files == null) { // null if security restricted
            throw new IOException("Failed to list contents of " + directory);
        }

        IOException exception = null;
        for (File file : files) {
            try {
                forceDelete(file);
            } catch (IOException ioe) {
                exception = ioe;
            }
        }

        if (null != exception) {
            throw exception;
        }
    }

    /**
     * Deletes a file. If file is a directory, delete it and all
     * sub-directories.
     * <p>
     * The difference between File.delete() and this method are:
     * <ul>
     * <li>A directory to be deleted does not have to be empty.</li>
     * <li>You get exceptions when a file or directory cannot be deleted.
     * (java.io.File methods returns a boolean)</li>
     * </ul>
     *
     * @param file file or directory to delete, must not be <code>null</code>
     * @throws NullPointerException  if the directory is <code>null</code>
     * @throws FileNotFoundException if the file was not found
     * @throws IOException           in case deletion is unsuccessful
     */
    public static void forceDelete(File file) throws IOException {
        if (file.isDirectory()) {
            deleteDirectory(file);
        } else {
            boolean filePresent = file.exists();
            if (!file.delete()) {
                if (!filePresent) {
                    throw new FileNotFoundException("File does not exist: " + file);
                }
                String message =
                        "Unable to delete file: " + file;
                throw new IOException(message);
            }
        }
    }

    /**
     * Deletes a directory recursively.
     *
     * @param directory directory to delete
     * @throws IOException in case deletion is unsuccessful
     */
    public static void deleteDirectory(File directory) throws IOException {
        if (!directory.exists()) {
            return;
        }

        if (!isSymlink(directory)) {
            cleanDirectory(directory);
        }

        if (!directory.delete()) {
            String message =
                    "Unable to delete directory " + directory + ".";
            throw new IOException(message);
        }
    }

    /**
     * Determines whether the specified file is a Symbolic Link rather than an
     * actual file.
     * <p>
     * Will not return true if there is a Symbolic Link anywhere in the path,
     * only if the specific file is.
     *
     * @param file the file to check
     * @return true if the file is a Symbolic Link
     * @throws IOException if an IO error occurs while checking the file
     * @since Commons IO 2.0
     */
    public static boolean isSymlink(File file) throws IOException {
        if (file == null) {
            throw new NullPointerException("File must not be null");
        }
        if (FilenameUtils.isSystemWindows()) {
            return false;
        }
        File fileInCanonicalDir;
        if (file.getParent() == null) {
            fileInCanonicalDir = file;
        } else {
            File canonicalDir = file.getParentFile().getCanonicalFile();
            fileInCanonicalDir = new File(canonicalDir, file.getName());
        }

        return !fileInCanonicalDir.getCanonicalFile().equals(fileInCanonicalDir.getAbsoluteFile());
    }

    /**
     * Copies bytes from an {@link InputStream} <code>source</code> to a file
     * <code>destination</code>. The directories up to <code>destination</code>
     * will be created if they don't already exist. <code>destination</code>
     * will be overwritten if it already exists.
     *
     * @param source      the <code>InputStream</code> to copy bytes from, must not
     *                    be <code>null</code>
     * @param destination the non-directory <code>File</code> to write bytes to
     *                    (possibly overwriting), must not be <code>null</code>
     * @throws IOException if <code>destination</code> is a directory
     * @throws IOException if <code>destination</code> cannot be written
     * @throws IOException if <code>destination</code> needs creating but can't
     *                     be
     * @throws IOException if an IO error occurs during copying
     * @since Commons IO 2.0
     */
    public static void copyInputStreamToFile(InputStream source, File destination)
            throws IOException {
        try {
            FileOutputStream output = openOutputStream(destination);
            try {
                IOUtils.copy(source, output);
            } finally {
                IOUtils.closeQuietly(output);
            }
        } finally {
            IOUtils.closeQuietly(source);
        }
    }

    public static void mkdirIfNeed(String dir) {
        File file = new File(dir);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    /**
     * 取上一级路径
     *
     * @param path
     * @return
     */
    public static String getParentPath(String path) {
        if (path != null && path.length() > 0) {
            // 去掉最后一个字符 ， 以兼容以“/” 结尾的路径
            path = path.substring(0, path.length() - 1);
            return path.substring(0, path.lastIndexOf("/") + 1);
        } else {
            return "";
        }
    }

    public static long getFileSize(String path) {
        File file = new File(path);
        if (!file.exists()) {
            return 0;
        }

        return getFileSize(file);
    }

    public static long getFileSize(File path) {
        long folderSize = 0;

        if (path.isDirectory()) {
            File[] fileList = path.listFiles();
            for (File aFileList : fileList) folderSize = folderSize + getFileSize(aFileList);
            return folderSize;
        } else
            return path.length();
    }

    /**
     * @param path
     * @return 获取路径下的所有文件
     */
    public List<File> getFile(String path) {
        ArrayList<File> list = new ArrayList<>();
        try {
            File file = new File(path);
            if (file.exists() && file.isDirectory()) {// 检查path是否存在,并且是一个目录
                File[] files = file.listFiles();
                if (files != null) {
                    Collections.addAll(list, files);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public static boolean isFileMatchChecksum(File file, String checkSum) {
        if (checkSum != null) {
            Long generated = generateFileChecksum(file);
            return generated != null
                    && (checkSum.equals(generated.toString())
                    || Long.toHexString(generated).equalsIgnoreCase(checkSum));
        }
        return true;
    }

    public static Long generateFileChecksum(File file) {
        try {
            return FileUtils.checksumCRC32(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static File getFile(Context context, String name) {
        return new File(getDiskCacheDir(context), name);
    }

    public static boolean deleteFile(Context context, String name) {
        File file = new File(getDiskCacheDir(context), name);
        return file.delete();
    }

    /**
     * 复制单个文件
     * 该方法为单线程文件复制最快的方法(文件越大该方法越有优势，一般比常用方法快30+%):
     * 注：应该确保目标文件所在的目录存在，不存在应该target.getParentFile().mkdirs()
     *
     * @param source 源文件
     * @param target 目标文件
     */
    public static void copyFile(File source, File target) {
        FileChannel in = null;
        FileChannel out = null;
        FileInputStream inStream = null;
        FileOutputStream outStream = null;
        try {
            inStream = new FileInputStream(source);
            outStream = new FileOutputStream(target);
            in = inStream.getChannel();
            out = outStream.getChannel();
            in.transferTo(0, in.size(), out);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(inStream);
            IOUtils.closeQuietly(in);
            IOUtils.closeQuietly(outStream);
            IOUtils.closeQuietly(out);
        }
    }

    public static boolean hasFile(Context context, String name) {
        File file = new File(getDiskCacheDir(context), name);
        return file.exists();
    }

    private static String getDiskCacheDir(Context context) {
        String cachePath;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            if (context.getExternalCacheDir() != null) {
                cachePath = context.getExternalCacheDir().getPath();
            } else {
                cachePath = context.getCacheDir().getPath();
            }
        } else {
            cachePath = context.getCacheDir().getPath();
        }
        return cachePath;
    }

    /**
     * 获取sdcard目录下的文件
     * 没有则创建
     *
     * @param dir      文件目录
     * @param filename 文件名
     * @return
     * @throws IOException
     */
    public static File getSDCardFile(String dir, String filename) throws IOException {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            File file = new File(dir + filename);
            if (!file.exists()) {
                File fileDir = new File(dir);
                if (!fileDir.exists()) {
                    if (!fileDir.mkdirs()) {
                        throw new IOException("cannot create dir=" + fileDir);
                    }
                }
                if (!file.exists()) {
                    if (!file.createNewFile()) {
                        throw new IOException("cannot create file=" + file.getPath());
                    }
                }
            }
            return file;
        } else {
            throw new IOException("sdcard un mounted state");
        }
    }

    /**
     * 保存文件
     *
     * @param bm
     * @param fileName
     * @throws IOException
     */
    public static File saveFile(Context context,Bitmap bm, String fileName) throws IOException {
        File file = new File(getDiskCacheDir(context)+fileName);
        if (file.exists()) {
            file.delete();
        }
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
        bm.compress(Bitmap.CompressFormat.JPEG, 80, bos);
        bos.flush();
        bos.close();
        file.exists();
        return file;
    }

}
