package dev.ganapathi.quotify.api.dto.Cursor;

import java.util.List;

public record CursorPage<T>(
    List<T> data,
    String nextCursor,
    boolean hasNext
) {
    public CursorPage {
        hasNext = nextCursor != null;
    }

    public CursorPage(List<T> data, String nextCursor) {
        this(data, nextCursor, false); // hasNext will be recomputed
    }

}
