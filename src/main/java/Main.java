import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by wuzhi on 14-12-24.
 */
public class Main {
    public static void main(String args[]) {

        Collection<WifiSpec> wifiSpecs = new ArrayList<WifiSpec>();
        String[] originMac = macs.split("\n");
        for (int i = 0; i < 1; i++) {
                System.out.println(originMac[i]);
            MacAddress macAddress = MacAddress.parse(originMac[i]);
            wifiSpecs.add(new WifiSpec(macAddress));
            AppleWifiLocationSource appleWifiLocationSource = new AppleWifiLocationSource();
            Collection<LocationSpec<WifiSpec>> locationSpecs = appleWifiLocationSource.retrieveLocation(wifiSpecs);
                for (LocationSpec<WifiSpec> wifiSpecLocationSource : locationSpecs) {
                        double[] latLng = gps2Mars(new double[]{wifiSpecLocationSource.getLatitude(), wifiSpecLocationSource.getLongitude()});
                        if (latLng != null && latLng.length > 0 && latLng[0] > 0 && latLng[1] > 0) {
                                System.out.println(wifiSpecLocationSource.getSource().getMac() + ";" + latLng[0] + "," + latLng[1] + ";");
                        }

                }
            wifiSpecs.clear();
        }
    }

        public static double[] gps2Mars(double[] locs) {
                double wgLat = locs[0];
                double wgLon = locs[1];
                if(outOfChina(wgLat, wgLon)) {
                        return null;
                } else {
                        double dLat = transformLat(wgLon - 105.0D, wgLat - 35.0D);
                        double dLon = transformLon(wgLon - 105.0D, wgLat - 35.0D);
                        double radLat = wgLat / 180.0D * 3.141592653589793D;
                        double magic = Math.sin(radLat);
                        magic = 1.0D - 0.006693421622965943D * magic * magic;
                        double sqrtMagic = Math.sqrt(magic);
                        dLat = dLat * 180.0D / (6335552.717000426D / (magic * sqrtMagic) * 3.141592653589793D);
                        dLon = dLon * 180.0D / (6378245.0D / sqrtMagic * Math.cos(radLat) * 3.141592653589793D);
                        locs[0] = wgLat + dLat;
                        locs[1] = wgLon + dLon;
                        return locs;
                }
        }

        private static final double PI = Math.PI;
        private static final double a = 6378245.0;
        private static final double ee = 0.00669342162296594323;

        private static boolean outOfChina(double lat, double lon) {
                return lon < 72.004 || lon > 137.8347 || lat < 0.8293 || lat > 55.8271;
        }

        private static double transformLat(double x, double y) {
                double ret = -100.0 + 2.0 * x + 3.0 * y + 0.2 * y * y + 0.1 * x * y + 0.2 * Math.sqrt(Math.abs(x));
                ret += (20.0 * Math.sin(6.0 * x * PI) + 20.0 * Math.sin(2.0 * x * PI)) * 2.0 / 3.0;
                ret += (20.0 * Math.sin(y * PI) + 40.0 * Math.sin(y / 3.0 * PI)) * 2.0 / 3.0;
                ret += (160.0 * Math.sin(y / 12.0 * PI) + 320 * Math.sin(y * PI / 30.0)) * 2.0 / 3.0;


                return ret;
        }

        private static double transformLon(double x, double y) {
                double ret = 300.0 + x + 2.0 * y + 0.1 * x * x + 0.1 * x * y + 0.1 * Math.sqrt(Math.abs(x));
                ret += (20.0 * Math.sin(6.0 * x * PI) + 20.0 * Math.sin(2.0 * x * PI)) * 2.0 / 3.0;
                ret += (20.0 * Math.sin(x * PI) + 40.0 * Math.sin(x / 3.0 * PI)) * 2.0 / 3.0;
                ret += (150.0 * Math.sin(x / 12.0 * PI) + 300.0 * Math.sin(x / 30.0 * PI)) * 2.0 / 3.0;

                return ret;
        }

    private static String macs = "D0:C7:C0:00:65:47\n" +
            "1C:FA:68:5B:84:24\n" +
            "00:36:76:30:F0:D0\n" +
            "D0:C7:C0:00:64:C2\n" +
            "B4:C7:99:E5:E6:60\n" +
            "84:24:8D:22:53:F0\n" +
            "74:1E:93:59:48:91\n" +
            "D0:C7:C0:00:64:BC\n" +
            "24:A4:3C:78:11:B1\n" +
            "84:24:8D:23:F4:00\n" +
            "C0:61:18:58:1B:42\n" +
            "24:A4:3C:78:12:06\n" +
            "04:46:65:84:42:F8\n" +
            "5C:0E:8B:4D:4A:F0\n" +
            "78:54:2E:57:2A:D2\n" +
            "14:E6:E4:75:F7:9A\n" +
            "14:E6:E4:3C:0F:72\n" +
            "B0:75:D5:78:1F:F5\n" +
            "84:24:8D:23:14:70\n" +
            "00:23:CD:7A:37:34\n" +
            "14:75:90:C0:66:A8\n" +
            "78:A1:06:7A:6A:38\n" +
            "E4:D3:32:D7:93:10\n" +
            "76:AE:27:DC:DD:D8\n" +
            "A8:57:4E:72:C6:62\n" +
            "78:A1:06:90:07:FE\n" +
            "E4:D3:32:94:5E:3E\n" +
            "84:24:8D:23:2A:D0\n" +
            "78:A1:06:76:5F:CA\n" +
            "9C:21:6A:91:E5:F4\n" +
            "00:3A:99:71:31:F1\n" +
            "00:3A:99:71:31:F0\n" +
            "D8:15:0D:9E:E0:06\n" +
            "84:24:8D:22:D7:60\n" +
            "1C:FA:68:12:84:04\n" +
            "00:0F:E2:DB:86:70\n" +
            "D0:C7:C0:59:8C:AE\n" +
            "B4:C7:99:E5:D3:D0\n" +
            "C0:61:18:F6:F7:3D\n" +
            "00:0F:E2:DB:71:50\n" +
            "28:37:37:15:82:71\n" +
            "B4:C7:99:EB:EC:60\n" +
            "84:24:8D:22:D6:10\n" +
            "84:24:8D:23:31:D0\n" +
            "38:22:D6:57:55:B0\n" +
            "84:24:8D:23:00:00\n" +
            "84:24:8D:23:32:A0\n" +
            "14:75:90:A1:83:EF\n" +
            "BC:D1:77:43:E0:0E\n" +
            "08:10:76:A7:3F:50\n" +
            "84:24:8D:22:E9:E0\n" +
            "84:24:8D:23:5A:60\n" +
            "84:24:8D:22:EA:B0\n" +
            "00:0F:E2:DB:85:60\n" +
            "84:24:8D:23:D2:F0\n" +
            "9C:21:6A:46:F8:34\n" +
            "A8:57:4E:0F:5B:84\n" +
            "C0:61:18:EC:2C:36\n" +
            "C8:3A:35:16:B4:10\n" +
            "84:24:8D:23:0B:D0\n" +
            "C8:3A:35:48:80:88\n" +
            "84:24:8D:22:E8:F0\n" +
            "84:24:8D:23:06:00\n" +
            "62:0A:C2:C0:D8:90\n" +
            "28:2C:B2:E3:46:36\n" +
            "B4:C7:99:B6:7E:62\n" +
            "B4:C7:99:B6:7E:63\n" +
            "70:62:B8:30:4E:E0\n" +
            "4C:AC:0A:24:89:AF\n" +
            "4C:AC:0A:24:89:AE\n" +
            "FE:9B:9C:AD:88:03\n" +
            "1C:FA:68:39:5B:18\n" +
            "B4:C7:99:B6:7E:61\n" +
            "84:24:8D:21:E1:90\n" +
            "84:24:8D:23:F3:B0\n" +
            "08:57:00:37:9E:B3\n" +
            "74:1E:93:59:48:90\n" +
            "1C:FA:68:12:81:9D\n" +
            "14:75:90:BD:8C:04\n" +
            "62:1E:93:4F:24:53\n" +
            "84:24:8D:23:51:10\n" +
            "84:24:8D:23:4B:D0\n" +
            "C4:A8:1D:8B:E2:24\n" +
            "84:24:8D:22:DB:D0\n" +
            "84:24:8D:23:BA:C0\n" +
            "84:24:8D:23:59:40\n" +
            "E0:05:C5:17:47:B4\n" +
            "84:24:8D:23:97:10\n" +
            "B0:75:D5:5B:B4:F3\n" +
            "84:24:8D:23:C8:70\n" +
            "84:24:8D:23:51:70\n" +
            "84:24:8D:22:A3:C0\n" +
            "B0:75:D5:5B:B4:F2\n" +
            "D4:EE:07:04:19:24\n" +
            "D6:EE:07:04:19:24\n" +
            "84:24:8D:23:0B:E0\n" +
            "84:24:8D:23:0D:40\n" +
            "84:24:8D:23:15:C0\n" +
            "0C:84:DC:90:E9:94\n" +
            "1C:FA:68:EA:3C:70\n";
}
