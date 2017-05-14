package io.agora.custom;

/**
 * Created by sonnguyen on 5/14/17.
 */

public class Room {
    private  String Broadcaster,RoomID,Topic,TeacherName;
    private int Level;

    public String getBroadcaster() {
        return Broadcaster;
    }

    public String getTeacherName() {
        return TeacherName;
    }

    public void setTeacherName(String teacherName) {
        TeacherName = teacherName;
    }

    public void setBroadcaster(String broadcaster) {
        Broadcaster = broadcaster;
    }

    public String getRoomID() {
        return RoomID;
    }

    public void setRoomID(String roomID) {
        RoomID = roomID;
    }

    public String getTopic() {
        return Topic;
    }

    public void setTopic(String topic) {
        Topic = topic;
    }

    public int getLevel() {
        return Level;
    }

    public void setLevel(int level) {
        Level = level;
    }

    public Room() {

    }
}
