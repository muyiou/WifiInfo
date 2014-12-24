/**
 * Created by wuzhi on 14-12-23.
 */

public class WifiSpec  {
    private final MacAddress mac;

    public WifiSpec(MacAddress mac) {
        this.mac = mac;
    }


    public MacAddress getMac() {
        return mac;
    }

    @Override
    public String toString() {
        return "WifiSpec{" +
                "mac=" + mac +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        WifiSpec wifiSpec = (WifiSpec) o;

        if (mac != null ? !mac.equals(wifiSpec.mac) : wifiSpec.mac != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return mac != null ? mac.hashCode() : 0;
    }
}

