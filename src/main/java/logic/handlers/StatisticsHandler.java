package logic.handlers;

import database.dao.ProglangDao;
import database.dao.StatisticsDao;
import database.models.Proglang;
import database.models.Statistics;
import database.models.User;
import database.models.types.State;
import database.services.UserService;
import logic.Response;

import java.util.ArrayList;
import java.util.List;

/**
 * Отвечает за статистику и лидерборд по квизам
 */
public class StatisticsHandler {
    private final StatisticsDao statisticsDao;
    private final ProglangDao proglangDao;
    private final UserService userService;
    private final List<String> keyboardMessagesIdle;

    public StatisticsHandler() {
        this.statisticsDao = new StatisticsDao();
        this.proglangDao = new ProglangDao();
        this.keyboardMessagesIdle = new ArrayList<>();
        this.userService = new UserService();
        keyboardMessagesIdle.add("/help");
        keyboardMessagesIdle.add("/quiz");
        keyboardMessagesIdle.add("/mystats");
        keyboardMessagesIdle.add("/leaderboard");
    }

    /**
     * Конструктор для тестов
     */
    public StatisticsHandler(StatisticsDao statisticsDao, ProglangDao proglangDao, UserService userService) {
        this.statisticsDao = statisticsDao;
        this.proglangDao = proglangDao;
        this.keyboardMessagesIdle = new ArrayList<>();
        this.userService = userService;
        keyboardMessagesIdle.add("/help");
        keyboardMessagesIdle.add("/quiz");
        keyboardMessagesIdle.add("/mystats");
        keyboardMessagesIdle.add("/leaderboard");
    }

    /**
     * Выведение статистики пользователя
     * @param currentUser пользователь
     * @return статистика пользователя
     */
    public Response getUserStatistic(User currentUser) {
        String responseMessage;
        List<Statistics> userStatistics = statisticsDao.findAllByUserId(currentUser.getId());

        if (!userStatistics.isEmpty()) {
            responseMessage = "Ваша статистика:\n";
            int sumUser = 0;
            long sumTotal = 0L;
            for(Statistics stat : userStatistics) {
                Proglang proglang = stat.getProglang();
                int userQuestions = stat.getScore();
                long totalQuestions = proglangDao.countProgquizzesByProglangId(proglang.getId());

                sumUser += userQuestions;
                sumTotal += totalQuestions;
                responseMessage += proglang.getName() + ": " + userQuestions + "/" + totalQuestions + "\n";
            }
            responseMessage += "Общая: " + sumUser + "/" + sumTotal + "\n";
        } else {
            responseMessage = "Статистика не найдена";
        }
        return  new Response(responseMessage, keyboardMessagesIdle);
    }

    /**
     * Выведение ТОП-10 по выбранному ЯП
     * @param currentUser пользователь
     * @param message сообщение пользователя
     * @return ответ на сообщение пользователя (лидерборд)
     */
    public Response getLeaderboard(User currentUser, String message) {
        List<String> keyboardMessages = proglangDao.getAllNames();
        if (message.equals("/leaderboard")) {
            return new Response("Выберите ЯП:", keyboardMessages);
        } else if (keyboardMessages.contains(message)) {
            String responseMessage;
            long proglangId = proglangDao.getIdByName(message.toLowerCase());
            List<Statistics> stats = statisticsDao.findAllByProglangId(proglangId);
            if (stats.isEmpty()) {
                currentUser.setState(State.IDLE);
                userService.update(currentUser);
                return new Response("Нет статистики по выбранному ЯП", keyboardMessagesIdle);
            }
            long totalQuestions = proglangDao.countProgquizzesByProglangId(proglangId);

            responseMessage = "Топ по языку " + message + "\n";
            for (int i = 0; i < stats.size(); i++) {
                String username = stats.get(i).getUser().getPlathform_username();
                responseMessage += (i + 1) + ". " + username + " " + stats.get(i).getScore() + "/"
                        + totalQuestions + "\n";
            }
            currentUser.setState(State.IDLE);
            userService.update(currentUser);
            return new Response(responseMessage, keyboardMessagesIdle);
        }
        return new Response("Язык не найден", keyboardMessages);
    }
}
