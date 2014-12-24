import com.squareup.wire.Message;
import com.squareup.wire.ProtoField;

import java.util.Collections;
import java.util.List;

import static com.squareup.wire.Message.Datatype.*;
import static com.squareup.wire.Message.Label.REPEATED;

public final class Response extends Message {

    public static final List<ResponseWifi> DEFAULT_WIFIS = Collections.emptyList();

    @ProtoField(tag = 2, label = REPEATED)
    public final List<ResponseWifi> wifis;

    private Response(Builder builder) {
        super(builder);
        this.wifis = immutableCopyOf(builder.wifis);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) return true;
        if (!(other instanceof Response)) return false;
        return equals(wifis, ((Response) other).wifis);
    }

    @Override
    public int hashCode() {
        int result = hashCode;
        return result != 0 ? result : (hashCode = wifis != null ? wifis.hashCode() : 1);
    }

    public static final class Builder extends Message.Builder<Response> {

        public List<ResponseWifi> wifis;

        public Builder() {
        }

        public Builder(Response message) {
            super(message);
            if (message == null) return;
            this.wifis = copyOf(message.wifis);
        }

        public Builder wifis(List<ResponseWifi> wifis) {
            this.wifis = wifis;
            return this;
        }

        @Override
        public Response build() {
            return new Response(this);
        }
    }

    public static final class ResponseWifi extends Message {

        public static final String DEFAULT_MAC = "";
        public static final Integer DEFAULT_CHANNEL = 0;

        @ProtoField(tag = 1, type = STRING)
        public final String mac;

        @ProtoField(tag = 2)
        public final ResponseWifi.WifiLocation location;

        @ProtoField(tag = 21, type = INT32)
        public final Integer channel;

        private ResponseWifi(Builder builder) {
            super(builder);
            this.mac = builder.mac;
            this.location = builder.location;
            this.channel = builder.channel;
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) return true;
            if (!(other instanceof ResponseWifi)) return false;
            ResponseWifi o = (ResponseWifi) other;
            return equals(mac, o.mac)
                    && equals(location, o.location)
                    && equals(channel, o.channel);
        }

        @Override
        public int hashCode() {
            int result = hashCode;
            if (result == 0) {
                result = mac != null ? mac.hashCode() : 0;
                result = result * 37 + (location != null ? location.hashCode() : 0);
                result = result * 37 + (channel != null ? channel.hashCode() : 0);
                hashCode = result;
            }
            return result;
        }

        public static final class Builder extends Message.Builder<ResponseWifi> {

            public String mac;
            public ResponseWifi.WifiLocation location;
            public Integer channel;

            public Builder() {
            }

            public Builder(ResponseWifi message) {
                super(message);
                if (message == null) return;
                this.mac = message.mac;
                this.location = message.location;
                this.channel = message.channel;
            }

            public Builder mac(String mac) {
                this.mac = mac;
                return this;
            }

            public Builder location(ResponseWifi.WifiLocation location) {
                this.location = location;
                return this;
            }

            public Builder channel(Integer channel) {
                this.channel = channel;
                return this;
            }

            @Override
            public ResponseWifi build() {
                return new ResponseWifi(this);
            }
        }

        public static final class WifiLocation extends Message {

            public static final Long DEFAULT_LATITUDE = 0L;
            public static final Long DEFAULT_LONGITUDE = 0L;
            public static final Integer DEFAULT_ACCURACY = 0;
            public static final Integer DEFAULT_ZEROFIELD4 = 0;
            public static final Integer DEFAULT_ALTITUDE = 0;
            public static final Integer DEFAULT_ALTITUDEACCURACY = 0;
            public static final Integer DEFAULT_UNKNOWN11 = 0;
            public static final Integer DEFAULT_UNKNOWN12 = 0;

            @ProtoField(tag = 1, type = INT64)
            public final Long latitude;

            @ProtoField(tag = 2, type = INT64)
            public final Long longitude;

            @ProtoField(tag = 3, type = INT32)
            public final Integer accuracy;

            @ProtoField(tag = 4, type = INT32)
            public final Integer zeroField4;

            /**
             * always 0 - don't ask why
             */
            @ProtoField(tag = 5, type = INT32)
            public final Integer altitude;

            /**
             * -500 if unknown
             */
            @ProtoField(tag = 6, type = INT32)
            public final Integer altitudeAccuracy;

            /**
             * Not set if altitude=-500
             */
            @ProtoField(tag = 11, type = INT32)
            public final Integer unknown11;

            /**
             * 5..63 ?
             */
            @ProtoField(tag = 12, type = INT32)
            public final Integer unknown12;

            private WifiLocation(Builder builder) {
                super(builder);
                this.latitude = builder.latitude;
                this.longitude = builder.longitude;
                this.accuracy = builder.accuracy;
                this.zeroField4 = builder.zeroField4;
                this.altitude = builder.altitude;
                this.altitudeAccuracy = builder.altitudeAccuracy;
                this.unknown11 = builder.unknown11;
                this.unknown12 = builder.unknown12;
            }

            @Override
            public boolean equals(Object other) {
                if (other == this) return true;
                if (!(other instanceof WifiLocation)) return false;
                WifiLocation o = (WifiLocation) other;
                return equals(latitude, o.latitude)
                        && equals(longitude, o.longitude)
                        && equals(accuracy, o.accuracy)
                        && equals(zeroField4, o.zeroField4)
                        && equals(altitude, o.altitude)
                        && equals(altitudeAccuracy, o.altitudeAccuracy)
                        && equals(unknown11, o.unknown11)
                        && equals(unknown12, o.unknown12);
            }

            @Override
            public int hashCode() {
                int result = hashCode;
                if (result == 0) {
                    result = latitude != null ? latitude.hashCode() : 0;
                    result = result * 37 + (longitude != null ? longitude.hashCode() : 0);
                    result = result * 37 + (accuracy != null ? accuracy.hashCode() : 0);
                    result = result * 37 + (zeroField4 != null ? zeroField4.hashCode() : 0);
                    result = result * 37 + (altitude != null ? altitude.hashCode() : 0);
                    result = result * 37 + (altitudeAccuracy != null ? altitudeAccuracy.hashCode() : 0);
                    result = result * 37 + (unknown11 != null ? unknown11.hashCode() : 0);
                    result = result * 37 + (unknown12 != null ? unknown12.hashCode() : 0);
                    hashCode = result;
                }
                return result;
            }

            public static final class Builder extends Message.Builder<WifiLocation> {

                public Long latitude;
                public Long longitude;
                public Integer accuracy;
                public Integer zeroField4;
                public Integer altitude;
                public Integer altitudeAccuracy;
                public Integer unknown11;
                public Integer unknown12;

                public Builder() {
                }

                public Builder(WifiLocation message) {
                    super(message);
                    if (message == null) return;
                    this.latitude = message.latitude;
                    this.longitude = message.longitude;
                    this.accuracy = message.accuracy;
                    this.zeroField4 = message.zeroField4;
                    this.altitude = message.altitude;
                    this.altitudeAccuracy = message.altitudeAccuracy;
                    this.unknown11 = message.unknown11;
                    this.unknown12 = message.unknown12;
                }

                public Builder latitude(Long latitude) {
                    this.latitude = latitude;
                    return this;
                }

                public Builder longitude(Long longitude) {
                    this.longitude = longitude;
                    return this;
                }

                public Builder accuracy(Integer accuracy) {
                    this.accuracy = accuracy;
                    return this;
                }

                public Builder zeroField4(Integer zeroField4) {
                    this.zeroField4 = zeroField4;
                    return this;
                }

                /**
                 * always 0 - don't ask why
                 */
                public Builder altitude(Integer altitude) {
                    this.altitude = altitude;
                    return this;
                }

                /**
                 * -500 if unknown
                 */
                public Builder altitudeAccuracy(Integer altitudeAccuracy) {
                    this.altitudeAccuracy = altitudeAccuracy;
                    return this;
                }

                /**
                 * Not set if altitude=-500
                 */
                public Builder unknown11(Integer unknown11) {
                    this.unknown11 = unknown11;
                    return this;
                }

                /**
                 * 5..63 ?
                 */
                public Builder unknown12(Integer unknown12) {
                    this.unknown12 = unknown12;
                    return this;
                }

                @Override
                public WifiLocation build() {
                    return new WifiLocation(this);
                }
            }
        }
    }
}
