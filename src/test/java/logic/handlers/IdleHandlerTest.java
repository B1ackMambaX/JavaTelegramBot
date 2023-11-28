//package logic.handlers;
//
//import logic.Response;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * Тестирование обработчика состояния IDLE
// */
//class IdleHandlerTest {
//    private IdleHandler idleHandler;
//    private List<String> keyboardCommands = new ArrayList<>();
//    @BeforeEach
//    void setUp() {
//        idleHandler = new IdleHandler();
//        keyboardCommands.add("/help");
//        keyboardCommands.add("/quiz");
//    }
//
//    /**
//     * Команда /start
//     */
//    @Test
//    void start() {
//        Response response = idleHandler.getResponse("/start");
//        Assertions.assertEquals("""
//                    Привет, я помогу тебе поднять теорию по языкам программирования!
//                    Пока я умею не так много, но могу задать тебе вопросы по JavaSctipt.\s
//                    В ответ на вопрос присылай мне одно слово.
//                    Для начала введи /quiz
//                    Чтобы остановить тест введи /stop""", response.message());
//        Assertions.assertEquals(keyboardCommands, response.keyboardMessages());
//    }
//
//    /**
//     * Команда /help
//     */
//    @Test
//    void help() {
//        Response response = idleHandler.getResponse("/help");
//        Assertions.assertEquals("""
//                    Привет, я помогу тебе поднять теорию по языкам программирования!
//                    Пока я умею не так много, но могу задать тебе вопросы по JavaSctipt.\s
//                    В ответ на вопрос присылай мне одно слово.
//                    Для начала введи /quiz
//                    Чтобы остановить тест введи /stop""", response.message());
//        Assertions.assertEquals(keyboardCommands, response.keyboardMessages());
//    }
//
//    /**
//     * Неизвестная команда
//     */
//    @Test
//    void unknownCommand() {
//        Response response = idleHandler.getResponse("/fhdhfjdshfd");
//        Assertions.assertEquals("Я не понимаю вас, посмотреть список доступных комманд можно с помощью /help",
//                response.message());
//        Assertions.assertEquals(keyboardCommands, response.keyboardMessages());
//    }
//}
