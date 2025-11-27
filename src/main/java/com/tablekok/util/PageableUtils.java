package com.tablekok.util;

import java.util.Set;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class PageableUtils {
    private static final Set<Integer> ALLOWED_SIZES = Set.of(10, 30, 50);
    private static final int DEFAULT_SIZE = 10;

    public static Pageable normalize(Pageable pageable) {
        int page = Math.max(0, pageable.getPageNumber());
        int size = pageable.getPageSize();
        if (!ALLOWED_SIZES.contains(size)) {
            size = DEFAULT_SIZE;
        }

        Sort sort = pageable.getSort();
        if (sort.isUnsorted()) {
            // 기본 정렬
            sort = Sort.by(Sort.Order.desc("createdAt"), Sort.Order.desc("updatedAt"));
        }

        return PageRequest.of(page, size, sort);
    }
}
