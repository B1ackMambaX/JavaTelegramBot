package logic.handlers;

import database.dao.ProglangDao;
import database.dao.StatisticsDao;
import database.models.Proglang;
import database.models.Statistics;
import database.models.User;
import logic.Response;

import java.util.ArrayList;
import java.util.List;

public class StatisticHandler {
    private final StatisticsDao statisticsDao;
    private final ProglangDao proglangDao;
    private final List<String> keyboardMessages;

    public StatisticHandler() {
        this.statisticsDao = new StatisticsDao();
        this.proglangDao = new ProglangDao();
        this.keyboardMessages = new ArrayList<>();
        keyboardMessages.add("/help");
        keyboardMessages.add("/quiz");
        keyboardMessages.add("/mystats");
    }

    public Response getUserStatistic(User currentUser) {
        String responseMessage;
        List<Statistics> userStatistics = statisticsDao.findAllByUserId(currentUser.getId());

        if (!userStatistics.isEmpty()) {
            responseMessage = "Ваша статистика:\n";
            Integer sumUser = 0;
            Long sumTotal = 0L;
            for(Statistics stat : userStatistics) {
                Proglang proglang = stat.getProglang();
                Integer userQuestions = stat.getScore();
                Long totalQuestions = proglangDao.countProgquizzesByProglangId(proglang.getId());

                sumUser += userQuestions;
                sumTotal += totalQuestions;
                responseMessage += proglang.getName() + ": " + userQuestions + "/" + totalQuestions + "\n";
            }
            responseMessage += "Общая: " + sumUser + "/" + sumTotal + "\n";
        } else {
            responseMessage = "Статистика не найдена";
        }
        return  new Response(responseMessage, keyboardMessages);
    }
}
