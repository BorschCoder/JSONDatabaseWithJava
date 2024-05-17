package server;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class FileHandler {
    private final String fileName;
    private final Gson gson;
    private final ReentrantReadWriteLock lock;


    public FileHandler() {

        this.fileName = "database/db.json";
        this.lock = new ReentrantReadWriteLock();
        this.gson = new Gson();

        try {
            File file = new File(fileName);
            if (file.getParentFile() != null) {
                file.getParentFile().mkdirs();
            }
            FileWriter fileWriter = new FileWriter(fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public LinkedList<TaskFile> read() {
        LinkedList<TaskFile> taskFiles = new LinkedList<>();
        Type type = new TypeToken<LinkedList<TaskFile>>() {
        }.getType();
        lock.readLock().lock();
        try {
            BufferedReader reader = Files.newBufferedReader(Paths.get(fileName));
            taskFiles = gson.fromJson(reader, type);
        } catch (IOException e) {
            e.printStackTrace();
        }
        lock.readLock().unlock();
        return taskFiles;
    }

    public void save(LinkedList<TaskFile> list) {
        lock.writeLock().lock();

        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(fileName))) {
            gson.toJson(list, TaskFile.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        lock.writeLock().unlock();
    }

}
