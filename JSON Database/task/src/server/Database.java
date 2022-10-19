package server;

import java.util.ArrayList;
import java.util.List;

public class Database {
    final private int SIZE;
    private List<String> CONTENT = new ArrayList<>();

    public Database(int SIZE) {
        this.SIZE = SIZE;
        for (int i = 0; i < this.SIZE; i++) {
            this.CONTENT.add("");
        }
    }

    public String get(int index) {
        return this.CONTENT.get(index);
    }

    public void set(int index, String content) {
        this.CONTENT.set(index, content);
    }
}
