package com.statista.booking.service.departments.factory;

import com.statista.booking.service.departments.IDepartmentService;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class DepartmentServiceFactory {

    private final Map<String, IDepartmentService> departmentServices;

    public IDepartmentService getDepartmentService(String departmentName) {
        IDepartmentService service = departmentServices.get(departmentName + "DepartmentService");
        if (service == null) {
            throw new IllegalArgumentException("Unknown department: " + departmentName);
        }
        return service;
    }

}
