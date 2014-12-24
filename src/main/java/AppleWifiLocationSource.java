import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;

public class AppleWifiLocationSource {

    public static final float LATLON_WIRE = 1E8F;
    private static final String TAG = "nlp.AppleWifiLocationSource";
    private final LocationRetriever locationRetriever = new LocationRetriever();


    public static String niceMac(String mac) {
        mac = mac.toLowerCase(Locale.getDefault());
        final StringBuilder builder = new StringBuilder();
        final String[] arr = mac.split(":");
        for (int i = 0; i < arr.length; i++) {
            if (arr[i].length() == 1) {
                builder.append("0");
            }
            builder.append(arr[i]);
            if (i < arr.length - 1) {
                builder.append(":");
            }
        }
        return builder.toString();
    }

    public Collection<LocationSpec<WifiSpec>> retrieveLocation(Collection<WifiSpec> specs) {
        Collection<LocationSpec<WifiSpec>> locationSpecs = new ArrayList<LocationSpec<WifiSpec>>();
        Collection<String> macs = new ArrayList<String>();
        for (WifiSpec spec : specs) {
            macs.add(niceMac(spec.getMac().toString()));
        }

        try {
            Response response = locationRetriever.retrieveLocations(macs);
            if ((response == null) || (response.wifis == null) || response.wifis.isEmpty()) {
                return locationSpecs;
            }
            for (Response.ResponseWifi responseWifi : response.wifis) {
                try {
                    WifiSpec wifiSpec = new WifiSpec(MacAddress.parse(responseWifi.mac));
                    if ((responseWifi.location.altitude != null) && (responseWifi.location.altitude > -500)) {
                        locationSpecs
                                .add(new LocationSpec<WifiSpec>(wifiSpec,responseWifi.location.latitude / LATLON_WIRE,
                                        responseWifi.location.longitude / LATLON_WIRE,
                                        responseWifi.location.accuracy,
                                        responseWifi.location.altitude));
                    } else {
                        locationSpecs
                                .add(new LocationSpec<WifiSpec>(wifiSpec,responseWifi.location.latitude / LATLON_WIRE,
                                        responseWifi.location.longitude / LATLON_WIRE,
                                        responseWifi.location.accuracy));
                    }
                } catch (Exception e) {
                    System.out.println(e.getLocalizedMessage());
                }
            }

        } catch (IOException e) {
            System.out.println(e.getLocalizedMessage());
        }
        return locationSpecs;
    }
}
