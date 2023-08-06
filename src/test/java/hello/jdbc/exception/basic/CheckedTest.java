package hello.jdbc.exception.basic;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
public class CheckedTest {


    @Test
    void checked_catch() {
        Service service = new Service();
        service.callCatch();
    }

    @Test
    void checked_throw() {
        Service service = new Service();
        // service.callThrow() 를 호출하면 MyCheckedException.class 의 인스턴스인지 확인
        Assertions.assertThatThrownBy(()-> service.callThrow())
                .isInstanceOf(MyCheckedException.class);
    }
    /**
     * Exception 을 상속받는 예외는 체크 예외가 된다
     */
    static class MyCheckedException extends Exception {
        public MyCheckedException(String message) {
            super(message);
        }
    }

    /**
     * 체크예외는
     * 예외를 잡아서 처리하거나 던지거나 둘중 하나를 필수로 선택해야함
     *
     */
    static class Service {
        Repository repository = new Repository();
        /**
         * 예외를 잡아서 처리하는 코드
         */

        public void callCatch() {
            try {
                repository.call();
            } catch (MyCheckedException e) {
                // 예외처리 로직
                log.info("예외처리, message={}" , e.getMessage(), e);
                // 예외를 잡았기 때문에 정상 리턴이됨
            }
        }

        /**
         * 체크예외를 밖으로 던지는 코드
         * 체크예외는 예외를 잡지 않고 밖으로 던지려면 throws 예외를 메서드를 필수로 선언해야함
         * @throws MyCheckedException
         */
        public void callThrow() throws MyCheckedException {
            repository.call();
        }

    }

    static class Repository {
        public void call() throws MyCheckedException {
            throw new MyCheckedException("ex");
        }
    }

}
