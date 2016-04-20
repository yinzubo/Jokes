package com.bitech.joke.bean;

import java.io.Serializable;
import java.util.List;

/**
 * <p></p>
 * Created on 2016/4/6 14:49.
 *
 * @author Lucy
 */
public class JokeBean implements Serializable {

    public List<Joke> data;

    public class Joke {
        public String content;
        public String hashId;
        public long unixtime;
        public String updatetime;
        public String url;
    }
}
