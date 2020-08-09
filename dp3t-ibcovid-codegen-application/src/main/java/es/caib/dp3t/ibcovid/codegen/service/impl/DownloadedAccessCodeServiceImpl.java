package es.caib.dp3t.ibcovid.codegen.service.impl;

import es.caib.dp3t.ibcovid.codegen.data.model.DownloadedAccessCode;
import es.caib.dp3t.ibcovid.codegen.service.DownloadedAccessCodeService;
import es.caib.dp3t.ibcovid.codegen.service.mapper.DownloadAccesCodeSrvDtoMapper;
import es.caib.dp3t.ibcovid.codegen.service.model.DownloadedAccessCodeSrvDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Log4j2
public class DownloadedAccessCodeServiceImpl implements DownloadedAccessCodeService {
    //private final DownloadedAccessCodeRepository downloadedAccessCodeRepository;
    private final EntityManager entityManager;

    public DownloadedAccessCodeServiceImpl(EntityManager entityManager) {
        //this.downloadedAccessCodeRepository = downloadedAccessCodeRepository;
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public DownloadedAccessCodeSrvDto getDownloadedAccessCode(){
        log.debug("BEGIN - createExposedAccessCode.");
        DownloadedAccessCodeSrvDto result = null;
        DownloadedAccessCode code = this.findCodeWithLock();
        if(code != null){
            code.setActivatedAt(LocalDateTime.now());
            this.entityManager.merge(code);
            result = DownloadAccesCodeSrvDtoMapper.INSTANCE.entityToSrvDto(code);
        }
        log.debug("END - createExposedAccessCode.");
        return result;
    }

    private DownloadedAccessCode findCodeWithLock(){
        Query query = entityManager
                .createQuery("select code from DownloadedAccessCode code " +
                        "where code.activatedAt is null order by code.createdAt asc");
        query.setLockMode(LockModeType.PESSIMISTIC_WRITE);
        final List<DownloadedAccessCode> codes = query.setMaxResults(1).getResultList();
        if(!codes.isEmpty()){
            return codes.get(0);
        }
        return null;
    }

}
