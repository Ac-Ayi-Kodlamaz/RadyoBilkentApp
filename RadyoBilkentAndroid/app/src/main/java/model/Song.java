package model;

import java.util.ArrayList;

public class Song extends Content {

    private final static String SONG_KEY = "songs";

    public int MAX_SIZE = 10^6;
    public String album;
    public String genre;
    public ArrayList<Integer> points;

    public Song() {
        key = SONG_KEY;
    }

    /**
     * Adding the recent points to the song for statistical and voting purposes
     * @param point : User's points to be added
     */
    public void addVote( int point)
    {
        //TODO
        points.add(point);
    }

    /**
     * Returning last ten points for statistical purposes
     * @return long for last ten points
     */
    public long getLastTen()
    {
        long sum = 0;
        for ( int i = 0 ; i < points.size();i++)
            sum = sum + points.get(i);
        return sum;
    }


    /**
     * Returning all points for statistical purposes
     * @return long for all points
     */
    public long getPoints()
    {
        return points.stream().mapToLong(value -> value).sum();
    }



}