package com.statista.booking.service.departments.impl;

import com.statista.booking.service.departments.IDepartmentService;
import org.springframework.stereotype.Service;

@Service("marketingDepartmentService")
public class MarketingIDepartmentService implements IDepartmentService {

    @Override
    public String doBusiness() {

        return "MARKETING BUSINESS PERFORMED";
    }
}
