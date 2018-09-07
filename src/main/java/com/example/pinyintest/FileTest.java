package com.example.pinyintest;


import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class FileTest {
    public static void main(String[] args) throws IOException {
        fileOutPutStreamTest();
        fileChanelWriteTest();
        fileChanleReadTest();
    }


    private static void fileChanleReadTest() throws IOException {
        String fileName = "javafile2.txt";
        File file = new File(fileName);
        if (!file.exists()) {
            try {
                boolean newFile = file.createNewFile();
                if (newFile) {
                    System.out.println("创建成功");
                } else {
                    System.out.println("创建失败");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            FileChannel channel = fileInputStream.getChannel();
            long size = channel.size();
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            channel.read(buffer);
            buffer.flip();
            byte[] array = buffer.array();
            System.out.println(new String(array, 0, (int) size));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    private static void fileChanelWriteTest() throws IOException {
        String fileName = "javafile2.txt";
        String content = "hello world";
        File file = new File(fileName);
        if (!file.exists()) {
            try {
                boolean newFile = file.createNewFile();
                if (newFile) {
                    System.out.println("创建成功");
                } else {
                    System.out.println("创建失败");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        FileOutputStream fos = null;
        FileChannel fosChannel = null;
        try {
            fos = new FileOutputStream(file);
            fosChannel = fos.getChannel();
            // ByteBuffer buffer = ByteBuffer.allocate(1024);
            ByteBuffer wrap = ByteBuffer.wrap(content.getBytes());
            // buffer.put(content.getBytes());
            fosChannel.write(wrap);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                fos.close();
            }
            if (null != fosChannel) {
                fosChannel.close();
            }
        }
    }

    private static void fileOutPutStreamTest() throws IOException {
        String fileName = "javafile1.txt";
        String content = "hello world";
        File file = new File(fileName);
        if (!file.exists()) {
            try {
                boolean newFile = file.createNewFile();
                if (newFile) {
                    System.out.println("创建成功");
                } else {
                    System.out.println("创建失败");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        OutputStream out = null;
        BufferedOutputStream bos = null;
        try {
            out = new FileOutputStream(file);
            bos = new BufferedOutputStream(out);
            bos.write(content.getBytes());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (null != out && null != bos) {
                bos.close();
                out.close();
            }
        }
    }

    private static void fileWriterTest() {
        String fileName = "javafile.txt";
        String content = "hello world";
        File file = new File(fileName);
        if (!file.exists()) {
            try {
                boolean newFile = file.createNewFile();
                if (newFile) {
                    System.out.println("创建成功");
                } else {
                    System.out.println("创建失败");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        FileWriter fileWriter = null;
        BufferedWriter bufferedWriter = null;
        try {
            fileWriter = new FileWriter(file);
            bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != fileWriter && null != bufferedWriter) {
                    bufferedWriter.close();
                    fileWriter.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
