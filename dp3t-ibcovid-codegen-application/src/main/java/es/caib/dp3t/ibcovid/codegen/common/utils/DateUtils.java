package es.caib.dp3t.ibcovid.codegen.common.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

@NoArgsConstructor(access = AccessLevel.NONE)
public final class DateUtils {

    public static Instant currentUTCInstant() {
        return Instant.now(Clock.systemUTC());
    }

    public static LocalDateTime currentUTCLocalDateTime() {
        return LocalDateTime.now(Clock.systemUTC());
    }

    public static Date toDate(final Instant instant) {
        return Date.from(instant);
    }

    public static LocalDate toLocalDate(final Long epochInMilliseconds) {
        return (epochInMilliseconds != null)
               ? Instant.ofEpochMilli(epochInMilliseconds).atZone(ZoneOffset.UTC).toLocalDate()
               : null;
    }

    public static LocalDateTime toLocalDateTime(final Long epochInMilliseconds) {
        return (epochInMilliseconds != null)
               ? LocalDateTime.ofInstant(Instant.ofEpochMilli(epochInMilliseconds), ZoneOffset.UTC)
               : null;
    }

    public static Long toEpoch(final LocalDate localDate) {
        return (localDate != null)
               ? localDate.atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli()
               : null;
    }

    public static Long toEpoch(final LocalDateTime localDateTime) {
        return (localDateTime != null)
               ? localDateTime.atZone(ZoneOffset.UTC).toInstant().toEpochMilli()
               : null;
    }

    public static Instant toInstant(final LocalDateTime localDateTime) {
        return localDateTime.atZone(ZoneOffset.UTC).toInstant();
    }

    public static boolean isUTCMidnight(final long epochInMilliseconds) {
        return (Instant.ofEpochMilli(epochInMilliseconds).atOffset(ZoneOffset.UTC).getHour() == 0);
    }

}
