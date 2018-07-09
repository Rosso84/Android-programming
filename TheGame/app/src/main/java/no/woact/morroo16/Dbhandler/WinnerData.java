package no.woact.morroo16.Dbhandler;

/* By Roosbeh Morandi*/


public class WinnerData {


    private long id;
    private int countWin;
    private String name;
    private String time;

    public WinnerData() {
    }

    public void setCountWin(int countWin){ this.countWin = countWin; }

    public long getCountWin() { return countWin; }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {return time;}

    public void setTime(String time) {this.time = time;}

    public long getId() { return id; }

    public void setId(long id) { this.id = id; }

    @Override
    public String toString() {
        return   "id: " + id + "          name: " + name +
                "        time: " + time +
                "            " + countWin + " wins" ;
    }

}