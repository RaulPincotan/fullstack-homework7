package ro.fasttrackit.fullstackhomework7.service;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class PageInfo {
    int totalPages;
    int totalElements;
    int currentPage;
    int pageSize;
}
