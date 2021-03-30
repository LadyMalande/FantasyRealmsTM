package sample;

public class SimplePlayer {
    private String name;
    private boolean playing;
    SimplePlayer(String name, boolean playing){
        this.name = name;
        this.playing = playing;
    }

    public String getName(){
        return name;
    }

    public boolean getPlaying(){
        return playing;
    }

    public void setPlaying(boolean playing){
        this.playing = playing;
    }
}
