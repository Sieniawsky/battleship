package core;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Loader {
    // Loads a data-set and parses lines, marshaling each into
    // an instance of Puzzle. The instance is then fed to Runner
    int index;
    int size;
    List<String> dataset;
    
    public Loader() {
        index = 0;
    }
    
    public boolean load(String filename) {

        try {
            FileReader reader = new FileReader(filename);
            BufferedReader bfr = new BufferedReader(reader);
            dataset = new ArrayList<String>();
            String line = null;
            while ((line = bfr.readLine()) != null) {
                dataset.add(line);
            }
            bfr.close();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        size = dataset.size();
        
        return false;
    }
    
    public Puzzle decode(String encoded) {
        return null;
    }
    
    public boolean hasNext() {
        return (index + 1) < size;
    }
    
    public Puzzle getNext() {
        
        if (index + 1 >= size) {
            return null;
        }
        
        Puzzle instance = decode(dataset.get(index));
        index++;
        
        return instance;
    }
}
