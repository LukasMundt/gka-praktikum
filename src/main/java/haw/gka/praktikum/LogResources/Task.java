package haw.gka.praktikum.LogResources;

public class Task {
    private final String _name;
    private final long _startTime;
    private final long _startBytes;

    public Task(String name, long startTime, long startBytes){
        _name = name;
        _startTime = startTime;
        _startBytes = startBytes;
    }

    public String getName() {
        return _name;
    }

    public long getStartTime() {
        return _startTime;
    }

    public long getStartBytes() {
        return _startBytes;
    }
}
