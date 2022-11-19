package com.loancalculator.service;

import java.io.InputStream;

public interface ReportService {

    /**
     * Create PDF loan report
     *
     * @param id
     * @return InputStream for creating pdf report
     */
    InputStream createGeneralPdfReport(Long id);

    /**
     * Create Excel loan report
     *
     * @param id
     * @return InputStream for creating excel .xlsx report
     */
    InputStream createGeneralExcelReport(Long id);
}
