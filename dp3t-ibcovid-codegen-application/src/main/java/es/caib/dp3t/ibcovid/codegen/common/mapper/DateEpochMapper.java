package es.caib.dp3t.ibcovid.codegen.common.mapper;

import es.caib.dp3t.ibcovid.codegen.common.utils.DateUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class DateEpochMapper {

    public static Long localDateToEpoch(final LocalDate localDate) {
        return DateUtils.toEpoch(localDate);
    }

    public static LocalDate epochToLocalDate(final Long epoch) {
        return DateUtils.toLocalDate(epoch);
    }

    public static Long localDateTimeToEpoch(final LocalDateTime localDateTime) {
        return DateUtils.toEpoch(localDateTime);
    }

    public static LocalDateTime epochToLocalDateTime(final Long epoch) {
        return DateUtils.toLocalDateTime(epoch);
    }

}
