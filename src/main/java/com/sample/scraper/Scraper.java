package com.sample.scraper;

import com.sample.model.Company;
import com.sample.model.ScrapedResult;

public interface Scraper {
    Company scrapCompanyByTicker(String ticker);
    ScrapedResult scrap(Company company);
}
