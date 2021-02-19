package ru.chernov.notvk.formatter;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.chernov.notvk.domain.entity.User;
import ru.chernov.notvk.utils.DateUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneOffset;

/**
 * @author Pavel Chernov
 */
@Component
@Getter
public class UserInfoFormatter {

    private final String ONLINE_MESSAGE = "онлайн";
    private final String MINUTES_AGO_MESSAGE = "был(а) в сети %s минут назад";
    private final String HOURS_AGO_MESSAGE = "был(а) в сети %s часов назад";
    private final String DAYS_AGO_MESSAGE = "был(а) в сети %s дней назад";
    private final String LAST_DATE_MESSAGE = "был(а) в сети %s";

    @Value("${session.threshold.seconds}")
    private int secondsThreshold;

    @Value("${session.threshold.minutes}")
    private int minutesThreshold;

    @Value("${session.threshold.hours}")
    private int hoursThreshold;

    @Value("${session.threshold.days}")
    private int daysThreshold;

    public void formatLastOnlineString(User user) {
        var now = LocalDateTime.now();
        var lastOnline = user.getLastOnline();

        // сейчас онлайн
        int secondsAgo = (int)(now.toEpochSecond(ZoneOffset.UTC) - lastOnline.toEpochSecond(ZoneOffset.UTC));
        if (secondsAgo < secondsThreshold) {
            user.setLastOnlineString(ONLINE_MESSAGE);
            return;
        }

        // был в сети n минут назад
        int minutesAgo = secondsAgo / 60;
        if (minutesAgo < minutesThreshold) {
            user.setLastOnlineString(String.format(MINUTES_AGO_MESSAGE, minutesAgo));
            return;
        }

        // был в сети n часов назад
        int hoursAgo = minutesAgo / 60;
        if (hoursAgo < hoursThreshold) {
            user.setLastOnlineString(String.format(HOURS_AGO_MESSAGE, hoursAgo));
            return;
        }

        // был в сети n часов назад
        int daysAgo = hoursAgo / 24;
        if (daysAgo < daysThreshold) {
            user.setLastOnlineString(String.format(DAYS_AGO_MESSAGE, daysAgo));
            return;
        }

        // показ полной даты последнего онлайна
        user.setLastOnlineString(String.format(LAST_DATE_MESSAGE, DateUtils.formatDate(lastOnline)));
    }

    public void formatBirthdayString(User user) {
        if (user.getBirthday() != null) {
            user.setBirthdayString(DateUtils.formatDate(user.getBirthday()));
        }
    }

    public void formatAge(User user) {
        if (user.getBirthday() != null) {
            LocalDate now = LocalDate.now();
            Period period = Period.between(user.getBirthday(), now);
            user.setAge(period.getYears());
        }
    }
}
