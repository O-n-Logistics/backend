package on.logistics.productservice.global.pageable;

import on.logistics.productservice.global.exception.CustomException;

public class PageableException extends CustomException {

    public PageableException(PageableExceptionCode pageableExceptionCode) {
        super(pageableExceptionCode);
    }

    public static class InvalidPageSizeException extends PageableException {

        public InvalidPageSizeException() {
            super(PageableExceptionCode.INVALID_PAGE_SIZE);
        }
    }

    public static class InvalidSortByException extends PageableException {

        public InvalidSortByException() {
            super(PageableExceptionCode.INVALID_SORT_BY);
        }
    }

    public static class InvalidPageNumberException extends PageableException {

        public InvalidPageNumberException() {
            super(PageableExceptionCode.INVALID_PAGE_NUMBER);
        }
    }

}