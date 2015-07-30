package com.benzino.game;

import java.io.Serializable;

/**
 * Created by Anas on 30/7/15.
 */
public class Test implements Serializable {

    private int id;
    private String username;
    private int ballTouched;
    private int totalTouches;
    //private boolean firstTime;
    private String createdAt;

    public Test() {
    }

    public Test(String username, int ballTouched, int totalTouches) {
        this.username = username;
        this.ballTouched = ballTouched;
        this.totalTouches = totalTouches;
        //this.firstTime = firstTime;
        this.createdAt = createdAt;
    }

    public Test(int id, String username, int ballTouched, int totalTouches, String createdAt) {
        this.id = id;
        this.username = username;
        this.ballTouched = ballTouched;
        this.totalTouches = totalTouches;
        //this.firstTime = firstTime;
        this.createdAt = createdAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getBallTouched() {
        return ballTouched;
    }

    public void setBallTouched(int ballTouched) {
        this.ballTouched = ballTouched;
    }

    public int getTotalTouches() {
        return totalTouches;
    }

    public void setTotalTouches(int totalTouches) {
        this.totalTouches = totalTouches;
    }
    /**
        public boolean getFirstTime() {
            return firstTime;
        }

        public void setFirstTime(boolean firstTime) {
            this.firstTime = firstTime;
        }
    **/
    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Test{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", ballTouched=" + ballTouched +
                ", totalTouches=" + totalTouches +
                //", firstTime=" + firstTime +
                ", createdAt='" + createdAt + '\'' +
                '}';
    }
}
