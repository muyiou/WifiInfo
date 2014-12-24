import com.squareup.wire.Wire;

import javax.net.ssl.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ProtocolException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class LocationRetriever {

    static {
        disableSslVerification();
    }

    private static void disableSslVerification() {
        try
        {
            // Create a trust manager that does not validate certificate chains
            TrustManager[] trustAllCerts = new TrustManager[] {new X509TrustManager() {
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
                public void checkClientTrusted(X509Certificate[] certs, String authType) {
                }
                public void checkServerTrusted(X509Certificate[] certs, String authType) {
                }
            }
            };

            // Install the all-trusting trust manager
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

            // Create all-trusting host name verifier
            HostnameVerifier allHostsValid = new HostnameVerifier() {
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            };

            // Install the all-trusting host verifier
            HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
    }

    public static final byte[] APPLE_MAGIC_BYTES =
            {0, 1, 0, 5, 101, 110, 95, 85, 83, 0, 0, 0, 11, 52, 46, 50, 46, 49, 46, 56, 67, 49, 52, 56, 0, 0, 0, 1, 0,
                    0, 0};
    private static final String SERVICE_URL = "https://iphone-services.apple.com/clls/wloc";
    private static final String HTTP_FIELD_CONTENT_TYPE = "Content-Type";
    private static final String HTTP_FIELD_CONTENT_LENGTH = "Content-Length";
    private static final String CONTENT_TYPE_URLENCODED = "application/x-www-form-urlencoded";
    private final Wire wire = new Wire();

    private static byte[] combineBytes(byte[] first, byte[] second, byte divider) {
        byte[] bytes = new byte[first.length + second.length + 1];
        System.arraycopy(first, 0, bytes, 0, first.length);
        bytes[first.length] = divider;
        System.arraycopy(second, 0, bytes, first.length + 1, second.length);
        return bytes;
    }

    private static HttpsURLConnection createConnection() throws IOException {
        return createConnection(SERVICE_URL);
    }

    private static HttpsURLConnection createConnection(String url) throws IOException {
        return createConnection(new URL(url));
    }

    private static HttpsURLConnection createConnection(URL url) throws IOException {
        return (HttpsURLConnection) url.openConnection();
    }

    private static Request createRequest(String... macs) {
        List<Request.RequestWifi> wifis = new ArrayList<Request.RequestWifi>();
        for (final String mac : macs) {
            wifis.add(new Request.RequestWifi.Builder().mac(mac).build());
        }
        return new Request.Builder().source("com.apple.maps").unknown3(0).unknown4(0).wifis(wifis).build();
    }

    private static void prepareConnection(HttpsURLConnection connection, int length) throws ProtocolException {
        connection.setRequestMethod("POST");
        connection.setDoInput(true);
        connection.setDoOutput(true);
        connection.setUseCaches(false);
        connection.setRequestProperty(HTTP_FIELD_CONTENT_TYPE, CONTENT_TYPE_URLENCODED);
        connection.setRequestProperty(HTTP_FIELD_CONTENT_LENGTH, String.valueOf(length));
    }

    //9c:1c:12:b7:02:c1
    public Response retrieveLocations(String... macs) throws IOException {
        Request request = createRequest(macs);
        byte[] byteb = request.toByteArray();
        byte[] bytes = combineBytes(APPLE_MAGIC_BYTES, byteb, (byte) byteb.length);
        HttpsURLConnection connection = createConnection();
        prepareConnection(connection, bytes.length);
        OutputStream out = connection.getOutputStream();
        out.write(bytes);
        out.flush();
        out.close();
        InputStream in = connection.getInputStream();
        in.skip(10);
        Response response = wire.parseFrom(readStreamToEnd(in), Response.class);
        in.close();
        return response;
    }

    public Response retrieveLocations(Collection<String> macs) throws IOException {
        return retrieveLocations(macs.toArray(new String[macs.size()]));
    }

    public static byte[] readStreamToEnd(InputStream is) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        if (is != null) {
            byte[] buff = new byte[1024];
            while (true) {
                int nb = is.read(buff);
                if (nb < 0) {
                    break;
                }
                bos.write(buff, 0, nb);
            }
            is.close();
        }
        return bos.toByteArray();
    }
}
