package com.sample.service;

import com.sample.exception.impl.NoCompanyException;
import com.sample.model.Company;
import com.sample.model.Dividend;
import com.sample.model.ScrapedResult;
import com.sample.model.constants.CacheKey;
import com.sample.persist.CompanyRepository;
import com.sample.persist.DividendRepository;
import com.sample.persist.entity.CompanyEntity;
import com.sample.persist.entity.DividendEntity;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class FinanceService {

    private final CompanyRepository companyRepository;
    private final DividendRepository dividendRepository;

    @Cacheable(key = "#companyName", value = CacheKey.KEY_FINANCE)
    public ScrapedResult getDividendByCompanyName(String companyName){
        log.info("search company -> " + companyName);

        // 1. 회사명 기준으로 회사 정보를 조회
        CompanyEntity company = companyRepository.findByName(companyName)
                .orElseThrow(() -> new NoCompanyException());

        // 2. 조회된 회사 ID로 배당금 정보 조회
        List<DividendEntity> dividendEntities = dividendRepository.findAllByCompanyId(company.getId());

        // 3. 결과 조합 후 반환
//        List<Dividend> dividends = new ArrayList<>();
//        for (var entity : dividendEntities){
//            dividends.add(Dividend.builder()
//                    .date(entity.getDate())
//                    .dividend(entity.getDividend())
//                    .build());
//        }

        // 위 코드와 동일한 역할을 함
        // stream().map(e -> ) 경우 e가 'dividendEntities 리스트'의 각각 데이터에 해당된다
        List<Dividend> dividends = dividendEntities.stream()
                .map(e -> new Dividend(e.getDate(),e.getDividend()))
                .collect(Collectors.toList());


        return new ScrapedResult(new Company(company.getTicker(), company.getName()),
                                    dividends);
    }
}
