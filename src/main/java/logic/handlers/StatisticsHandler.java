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
        this(new StatisticsDao(), new ProglangDao(), new UserService());
    }

    /**
     * Конструктор для тестов
     */
    public StatisticsHandler(StatisticsDao statisticsDao, ProglangDao proglangDao, UserService userService) {
        this.statisticsDao = statisticsDao;
        this.proglangDao = proglangDao;
        this.userService = userService;
        keyboardMessagesIdle = new ArrayList<>(List.of("/help", "/quiz", "/mystats", "/leaderboard"));
    }


    /**
     * Выведение статистики пользователя
     * @param currentUser пользователь
     * @return статистика пользователя
     */
    public Response getUserStatistic(User currentUser) {
        StringBuilder responseMessage;
        List<Statistics> userStatistics = statisticsDao.findAllByUserId(currentUser.getId());

        if (!userStatistics.isEmpty()) {
            responseMessage = new StringBuilder("Ваша статистика:\n");
            int sumUser = 0;
            long sumTotal = 0L;
            for(Statistics stat : userStatistics) {
                Proglang proglang = stat.getProglang();
                int userQuestions = stat.getScore();
                long totalQuestions = proglangDao.countProgquizzesByProglangId(proglang.getId());

                sumUser += userQuestions;
                sumTotal += totalQuestions;
                responseMessage.append(proglang.getName()).append(": ").append(userQuestions)
                        .append("/").append(totalQuestions).append("\n");
            }
            responseMessage.append("Общая: ").append(sumUser).append("/").append(sumTotal).append("\n");
        } else {
            responseMessage = new StringBuilder("Статистика не найдена");
        }
        return  new Response(responseMessage.toString(), keyboardMessagesIdle);
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
            currentUser.setState(State.LEADERBOARD);
            userService.update(currentUser);
            return new Response("Выберите ЯП:", keyboardMessages);
        } else if (keyboardMessages.contains(message)) {
            StringBuilder responseMessage;
            long proglangId = proglangDao.getIdByName(message.toLowerCase());
            List<Statistics> stats = statisticsDao.findTopByProglangId(proglangId);
            if (stats.isEmpty()) {
                currentUser.setState(State.IDLE);
                userService.update(currentUser);
                return new Response("Нет статистики по выбранному ЯП", keyboardMessagesIdle);
            }
            long totalQuestions = proglangDao.countProgquizzesByProglangId(proglangId);

            responseMessage = new StringBuilder("Топ по языку " + message + "\n");
            for (int i = 0; i < stats.size(); i++) {
                String username = stats.get(i).getUser().getPlathformUsername();
                responseMessage.append((i + 1)).append(". ").append(username).append(" ")
                        .append(stats.get(i).getScore()).append("/").append(totalQuestions).append("\n");
            }
            currentUser.setState(State.IDLE);
            userService.update(currentUser);
            return new Response(responseMessage.toString(), keyboardMessagesIdle);
        }
        return new Response("Язык не найден", keyboardMessages);
    }
}
