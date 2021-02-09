package ru.chernov.notvk.mail.generator;

import ru.chernov.notvk.mail.MailInfo;

/**
 * @author Pavel Chernov
 */
public interface MailGenerator {
    String generate(MailInfo mailInfo);

    String getSubject();
}
