package ActiveObject;

public class Future {
    private volatile boolean isDone;

    public Future(){
        this.isDone = false;
    }

    public boolean queryRequest(){
        return this.isDone;
    }

    public void confirmRequest(){
        this.isDone = true;
    }
}
