package com.tablekok.cursor.dto.request;

public record CursorRequest<T>(

	String cursor,

	/**
	 * UUID, Long등의 타입을 받을 수 있도록 제네릭 사용
	 */
	T cursorId,
	Integer size
) {
	public static final int DEFAULT_SIZE = 10;

	public CursorRequest {
		if (size == null) size = DEFAULT_SIZE;
	}

	public boolean hasKey() {
		return cursor != null && cursorId != null;
	}

	/**
	 * 다음 페이지 조회를 위한 size + 1 반환 편의 메서드
	 * @return
	 */
	public int getLimit() {
		return size + 1;
	}
}
