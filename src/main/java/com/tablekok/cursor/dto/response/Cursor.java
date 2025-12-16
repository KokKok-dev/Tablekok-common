package com.tablekok.cursor.dto.response;

import java.util.List;
import java.util.function.Function;

/**
 *
 * @param totalCount 	전체 데이터 수
 *                   	null일 수 있음 (Count 쿼리 불필요한 경우)
 * @param content 		조회한 데이터들을 담는 리스트
 * @param nextCursor 	다음 커서 값
 * @param nextCursorId 	다음 커서 ID
 * @param hasNext
 * @param <T>
 * @param <K>
 */
public record Cursor<T, K>(
	Long totalCount,
	List<T> content,
	String nextCursor,
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
		return new Cursor<>(0L, List.of(), null, null, false);
	}

	public <R> Cursor<R, K> map(Function<T, R> mapper) {
		List<R> newContent = content.stream().map(mapper).toList();

		return new Cursor<>(totalCount, newContent, nextCursor, nextCursorId, hasNext);
	}
}
