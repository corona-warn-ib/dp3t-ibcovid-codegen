package es.caib.dp3t.ibcovid.codegen.service.task;

import es.caib.dp3t.ibcovid.codegen.common.utils.DateUtils;
import es.caib.dp3t.ibcovid.codegen.data.ExposedAccessCodeRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Log4j2
public class DeleteExposedAccessCodesTask {

    private final ExposedAccessCodeRepository exposedAccessCodeRepository;

    public DeleteExposedAccessCodesTask(final ExposedAccessCodeRepository exposedAccessCodeRepository) {
        this.exposedAccessCodeRepository = exposedAccessCodeRepository;
    }

    //@Scheduled(cron = "${dp3t.ibcovid.codegen.tasks.delete-exposed-access-codes.cron}")
    @Transactional
    public void run() {
        log.info("START DeleteExposedAccessCodesTask");

        final int rowsDeleted = exposedAccessCodeRepository.deleteExpiredAccessCodes(
                DateUtils.currentUTCLocalDateTime());

        log.info("DeleteExposedAccessCodesTask - {} rows deleted", rowsDeleted);
        log.info("END DeleteExposedAccessCodesTask");
    }

}
