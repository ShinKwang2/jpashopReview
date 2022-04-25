package jpabook.jpashop.domains.item.service;

public class NotEnoughStockException extends RuntimeException {

    public NotEnoughStockException() {
        super("해당 상품의 재고가 부족합니다.");
    }

    public NotEnoughStockException(String message) {
        super(message);
    }

    public NotEnoughStockException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotEnoughStockException(Throwable cause) {
        super(cause);
    }

    protected NotEnoughStockException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
