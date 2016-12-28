package zaliczeniepz.wmsgpsapp;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Lenovo on 2016-12-28.
 */

public class Job {
    public int id;
    public String name;
    public ArrayList<Double> boundingBox;

    public Job (int initId, String initName){
        this.id = initId;
        this.name = initName;
    }
}
