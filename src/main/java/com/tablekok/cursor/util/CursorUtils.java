package com.tablekok.cursor.util;

import java.util.List;
import java.util.function.Function;

import com.tablekok.cursor.dto.response.Cursor;

public class CursorUtils {
	/**
	 * @param totalCount		조회된 데이터 수
	 * @param content 			조회된 데이터 리스트
	 * @param size 				요청한 페이지 사이즈
	 * @param cursorExtractor 	entity에서 cursor 값을 추출하는 함수
	 * @param idExtractor 		entity에서 ID(K)를 추출하는 함수
	 * @param <T> 				데이터(DTO/Entity) 타입
	 * @param <K> 				ID 타입 (UUID, Long) 등
	 * @return 					CursorResponse 반환
	 */
	public static <T, K> Cursor<T, K> makeResponse(
		Long totalCount,
		List<T> content,
		int size,
		Function<T, String> cursorExtractor,
		Function<T, K> idExtractor
	) {
		boolean hasNext = content.size() > size;
		List<T> resultList = hasNext ? content.subList(0, size) : content;

		String nextCursor = null;
		K nextCursorId = null;

		if (!resultList.isEmpty()) {
			T lastElement = resultList.get(resultList.size() - 1);
			nextCursor = cursorExtractor.apply(lastElement);
			nextCursorId = idExtractor.apply(lastElement);
		}
		return new Cursor<>(totalCount, resultList, nextCursor, nextCursorId, hasNext);
	}
}
