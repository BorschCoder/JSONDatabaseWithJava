package server;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class EntityDAO {
    private static final String DB_URL = "/Users" +
            "/mihail" +
            "/IdeaProjects" +
            "/JSON Database with Java" +
            "/JSON Database with Java" +
            "/task" +
            "/src" +
            "/server" +
            "/data" +
            "/db.json";
    ;
    private final Gson gson;
    private final ReentrantReadWriteLock lock;

    public EntityDAO() {
        this.lock = new ReentrantReadWriteLock();
        this.gson = new Gson().newBuilder().setPrettyPrinting().create();
    }

    public List<Entry> findAll() {
        List<Entry> entries = new ArrayList<>();

        Type type = new TypeToken<ArrayList<Entry>>(){}.getType();

        StringBuilder jsonString = new StringBuilder();
        lock.readLock().lock();
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(DB_URL))) {
            String line;
            while ((line = reader.readLine()) != null) {
                jsonString.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        lock.readLock().unlock();

        if (jsonString.isEmpty() || jsonString.length() == 2) {
            return entries;
        }
        entries = gson.fromJson(jsonString.toString(), type);

        return entries;
    }


    public void saveAll(List<Entry> entries) {
        lock.writeLock().lock();

        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(DB_URL))) {

            StringBuilder jsonString = new StringBuilder();
            jsonString.append(gson.toJson(entries, new TypeToken<List<Entity>>() {
            }.getType()));

            writer.write(jsonString.toString());

        } catch (IOException e) {
            e.printStackTrace();
        }
        lock.writeLock().unlock();
    }

}
