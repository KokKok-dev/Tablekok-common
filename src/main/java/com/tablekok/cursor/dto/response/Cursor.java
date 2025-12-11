package com.tablekok.cursor.dto.response;

import java.util.List;
import java.util.function.Function;

public record Cursor<T, K>(

	/**
	 * 조회한 데이터들을 담는 리스트
	 */
	List<T> content,

	/**
	 * 다음 커서 값
	 */
	String nextCursor,

	/**
	 * 다음 커서 ID
	 */
	K nextCursorId,
	boolean hasNext
) {

	/**
	 * 빈 응답 생성
	 * @return
	 * @param <T>
	 * @param <K>
	 */
	public static <T, K> Cursor<T, K> empty() {
		return new Cursor<>(List.of(), null, null, false);
	}

	public <R> Cursor<R, K> map(Function<T, R> mapper) {
		List<R> newContent = content.stream().map(mapper).toList();

		return new Cursor<>(newContent, nextCursor, nextCursorId, hasNext);
	}
}
