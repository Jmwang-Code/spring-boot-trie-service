import java.util.*;

public class Member implements Comparable<Member> {
    private String _address;
    private int _port;
    private long _heartbeat;
    private long _localTime;
    private boolean _failed;

    public Member() {
        _heartbeat = 0;
        _failed = false;
    }

    public Member(String address, int port) {
        _address = address;
    	_port = port;
    	_heartbeat = 0;
        _failed = false;
    }

    public Member(String address, int port, long heartbeat) {
        _address = address;
        _port = port;
        _heartbeat = heartbeat;
    }

    public void setAddress(String address) {
	   _address = address;
    }

    public String getAddress() {
	   return _address;
    }

    public void setPort(int port) {
	   _port = port;
    }

    public int getPort() {
	   return _port;
    }

    public void incrementHeartbeat() {
	   _heartbeat++;
    }

    public long getHeartbeat() {
	   return _heartbeat;
    }

    public void setHeartbeat(long heartbeat) {
       _heartbeat = heartbeat;
    }

    public void setLocalTime(long localTime) {
	   _localTime = localTime;
    }

    public long  getLocalTime() {
	   return _localTime;
    }

    public String toString() {
        return _address + ":" + String.valueOf(_port) + ":" + String.valueOf(_heartbeat);
    }

    public String toStringWithLocalTime() {
        return _address + ":" + String.valueOf(_port) + ":" + String.valueOf(_heartbeat) + ":" + String.valueOf(_localTime);   
    }

    public boolean isFailed() {
        return _failed;
    }

    public void setFail(boolean isFailed) {
        _failed = isFailed;
    }

    public int compareTo(Member m)
    {
        //compare address
        String[]address1 = this.getAddress().split("\\.");
        String[]address2 = m.getAddress().split("\\.");

        for (int i = 0; i < 4; i++) {
            if (Integer.parseInt(address1[i]) > Integer.parseInt(address2[i])) {
                return 1;
            } else if (Integer.parseInt(address1[i]) < Integer.parseInt(address2[i])) {
                return -1;
            }
        }

        //if address is the same, compare port
        if (this.getPort() > m.getPort()) {
            return 1;
        } else if (this.getPort() < m.getPort()) {
            return -1;
        } else {
            return 0;
        }
    }


}