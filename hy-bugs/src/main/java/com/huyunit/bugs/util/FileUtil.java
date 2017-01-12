package com.huyunit.bugs.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Environment;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;


@SuppressLint("NewApi")
public class FileUtil {
    //
    public static String SDCARD_DIR = "hy_bugs";
    // 存储搜索缓存文件到指定的文件夹file中。
    public static String SDCARD_FILE_DIR = SDCARD_DIR + File.separator + "file";
    // 存储追踪Bug日志文件到指定的文件夹errorlog中。
    public static String SDCARD_ERROR_DIR = SDCARD_DIR + File.separator + "errorlog";

    public static final String TYPE_TXT = ".txt";

    /**
     * 判断sdcard是否存在，并返回sdcard路径。
     *
     * @return 返回sdcard路径
     */
    public static String getSDPath() {
        File sdDir = null;
        boolean sdCardExist = Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);// 判断sd卡是否存在
        if (sdCardExist) {
            sdDir = Environment.getExternalStorageDirectory();// 获取跟目录
        }
        if (sdDir == null) {
            return null;
        }
        return sdDir.toString();
    }

    public static String getExtSDCard() {
        try {
            Runtime runtime = Runtime.getRuntime();
            Process proc = runtime.exec("mount");
            InputStream is = proc.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            String line;
            String mount = new String();
            BufferedReader br = new BufferedReader(isr);
            while ((line = br.readLine()) != null) {
                if (line.contains("secure"))
                    continue;
                if (line.contains("asec"))
                    continue;

                if (line.contains("fat")) {
                    String columns[] = line.split(" ");
                    if (columns != null && columns.length > 1) {
                        mount = mount.concat(columns[1]);
                    }
                } /*
                 * else if (line.contains("fuse")) { String columns[] =
				 * line.split(" "); if (columns != null && columns.length > 1) {
				 * mount = mount.concat(columns[1] + "\n"); } }
				 */
            }
            return mount;
        } catch (FileNotFoundException e) {
            LogUtil.e(e.getMessage(), e);
        } catch (IOException e) {
            LogUtil.e(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 创建文件夹
     *
     * @return 返回文件夹路径
     */
    public static String createSDCardFolder(String dirFolder) {
        String extSdCard = getSDPath();
        if (StringUtils.isEmpty(extSdCard)) {
            extSdCard = getExtSDCard();
        }
        File folder = new File(extSdCard + File.separator + dirFolder);
        if (folder.exists() == false) {
            folder.mkdirs(); // 创建文件夹完整路径
        }
        return folder.toString();
    }

    /**
     * 创建文件
     *
     * @return 返回文件路径
     */
    public static String createFile(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {// 文件存在返回false
            try {
                file.createNewFile();// 创建文件
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file.toString();
    }

    /**
     * 判断指定是否文件存在
     *
     * @param filePath
     * @return
     */
    public static boolean isExistFile(String filePath) {
        if (StringUtils.isEmpty(filePath)) {
            return false;
        }
        boolean flag = false;
        try {
            File file = new File(filePath);
            if (file.exists()) {
                flag = true; // 指定文件存在
            } else {
                flag = false;
            }
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }

    /**
     * 清除指定缓存文件
     *
     * @param filePath
     * @return
     */
    public static boolean deleteFile(String filePath) {
        boolean flag = false;
        try {
            File file = new File(filePath);
            if (file.exists()) {
                flag = file.delete(); // 删除指定文件
            }
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }

    /**
     * 删除存在多级目录中的文件
     *
     * @param filepath
     * @throws IOException
     */
    public static boolean delFolders(String filepath) {
        boolean flag = false;
        try {
            File f = new File(filepath);// 定义文件路径
            if (f.exists() && f.isDirectory()) {// 判断是文件还是目录
                if (f.listFiles().length == 0) {// 若目录下没有文件则直接删除
                    f.delete();
                } else {// 若有则把文件放进数组，并判断是否有下级目录
                    File delFile[] = f.listFiles();
                    int i = f.listFiles().length;
                    for (int j = 0; j < i; j++) {
                        if (delFile[j].isDirectory()) {
                            delFolders(delFile[j].getAbsolutePath());// 递归调用del方法并取得子目录路径
                        }
                        flag = delFile[j].delete();// 删除文件
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return flag;
    }

    /**
     * 单个文件拷贝。
     *
     * @param srcFile   源文件
     * @param destFile  目标文件
     * @param overwrite 是否覆盖目的文件
     * @throws IOException
     */
    public static void copyFile(File srcFile, File destFile, boolean overwrite) throws IOException {
        // 首先判断源文件是否存在
        if (!srcFile.exists()) {
            throw new FileNotFoundException("Cannot find the source file: " + srcFile.getAbsolutePath());
        }
        // 判断源文件是否可读
        if (!srcFile.canRead()) {
            throw new IOException("Cannot read the source file: " + srcFile.getAbsolutePath());
        }

        if (overwrite == false) {
            // 目标文件存在就不覆盖
            if (destFile.exists())
                return;
        } else {
            // 如果要覆盖已经存在的目标文件，首先判断是否目标文件可写。
            if (destFile.exists()) {
                if (!destFile.canWrite()) {
                    throw new IOException("Cannot write the destination file: " + destFile.getAbsolutePath());
                }
            } else {
                // 不存在就创建一个新的空文件。
                if (!destFile.createNewFile()) {
                    throw new IOException("Cannot write the destination file: " + destFile.getAbsolutePath());
                }
            }
        }

        BufferedInputStream inputStream = null;
        BufferedOutputStream outputStream = null;
        byte[] block = new byte[1024];
        try {
            inputStream = new BufferedInputStream(new FileInputStream(srcFile));
            outputStream = new BufferedOutputStream(new FileOutputStream(
                    destFile));
            while (true) {
                int readLength = inputStream.read(block);
                if (readLength == -1)
                    break;// end of file
                outputStream.write(block, 0, readLength);
            }
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException ex) {
                    // just ignore
                }
            }
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException ex) {
                    // just ignore
                }
            }
        }
    }

    /**
     * 拷贝文件，从源文件夹拷贝文件到目的文件夹。 <br>
     * 参数源文件夹和目的文件夹，最后都不要带文件路径符号，例如：c:/aa正确，c:/aa/错误。
     *
     * @param srcDirName  源文件夹名称 ,例如：c:/test/aa 或者c://test//aa
     * @param destDirName 目的文件夹名称,例如：c:/test/aa 或者c://test//aa
     * @param overwrite   是否覆盖目的文件夹下面的文件。
     * @throws IOException
     */
    public static void copyFiles(String srcDirName, String destDirName, boolean overwrite) throws IOException {
        File srcDir = new File(srcDirName);// 声明源文件夹
        // 首先判断源文件夹是否存在
        if (!srcDir.exists()) {
            throw new FileNotFoundException("Cannot find the source directory: " + srcDir.getAbsolutePath());
        }

        File destDir = new File(destDirName);
        if (overwrite == false) {
            if (destDir.exists()) {
                // do nothing
            } else {
                if (destDir.mkdirs() == false) {
                    throw new IOException("Cannot create the destination directories = " + destDir);
                }
            }
        } else {
            // 覆盖存在的目的文件夹
            if (destDir.exists()) {
                // do nothing
            } else {
                // create a new directory
                if (destDir.mkdirs() == false) {
                    throw new IOException("Cannot create the destination directories = " + destDir);
                }
            }
        }

        // 循环查找源文件夹目录下面的文件（屏蔽子文件夹），然后将其拷贝到指定的目的文件夹下面。
        File[] srcFiles = srcDir.listFiles();
        if (srcFiles == null || srcFiles.length < 1) {
            // throw new IOException ("Cannot find any file from source
            // directory!!!");
            return;// do nothing
        }

        // 开始复制文件
        int SRCLEN = srcFiles.length;

        for (int i = 0; i < SRCLEN; i++) {
            File destFile = new File(destDirName + File.separator + srcFiles[i].getName());
            // 注意构造文件对象时候，文件名字符串中不能包含文件路径分隔符";".
            // log.debug(destFile);
            if (srcFiles[i].isFile()) {
                copyFile(srcFiles[i], destFile, overwrite);
            } else {
                // 在这里进行递归调用，就可以实现子文件夹的拷贝
                copyFiles(srcFiles[i].getAbsolutePath(), destDirName + File.separator + srcFiles[i].getName(), overwrite);
            }
        }
    }

}
