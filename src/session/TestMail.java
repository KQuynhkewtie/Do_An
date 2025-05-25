package session;

import helper.Sendmail;

import javax.mail.MessagingException;

public class TestMail {
    public static void main(String[] args) throws MessagingException {
        Sendmail.sendOTP("kquynh0410@gmail.com", "123456");
    }
}
