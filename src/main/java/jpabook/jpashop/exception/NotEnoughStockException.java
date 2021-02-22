package jpabook.jpashop.exception;

public class NotEnoughStockException  extends RuntimeException{

  //오버라이드 함
  //메시지 같은거 다 넘겨서 예외가 발생한 어떤 인셉션 같은 것을
  public NotEnoughStockException() {
    super();
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
}
