package com.sujalsaini.springboot_ai.utils;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component 
public class emailService {
    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String senderEmail;

    public emailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Tool(description = """
        Send an email on behalf of the configured author/admin.

        Required parameters:
        - to: recipient email address
        - subject: email subject
        - body: complete email body

        STRICT RULES:
        1. Only send an email when the user/admin has clearly requested sending it.
           If they only ask to draft, write, or compose an email, do NOT call this tool.

        2. The recipient email address must be explicitly provided or clearly established
           by authorized context. Never guess, fabricate, or invent an email address.

        3. Always use the author's name and identity exactly as provided by
           authorInformation. Never invent or modify the author's name, designation,
           organization, contact information, or signature.

        4. Use only information explicitly provided by the user/admin or established
           by authorized context. Never invent names, dates, companies, deadlines,
           attachments, promises, previous conversations, or other facts.

        5. If the recipient's name is explicitly known, address them appropriately.
           If the recipient's name is not known, use a professional neutral greeting
           such as "Dear Sir/Madam," or an appropriate team greeting.
           Never guess a person's name from their email address.

        6. If the recipient email address is missing, do NOT send the email.
           Ask the user/admin for the correct email address.

        7. Keep every email professional, concise, respectful, grammatically correct,
           and directly related to the user's request.

        8. Do not expand the user's request. Do not add unrelated questions,
           requests, claims, commitments, or information.

        9. If the request is ambiguous and sending the wrong email could cause a
           problem, ask for clarification instead of guessing.

        10. Do not send duplicate or substantially identical emails. If the context
            indicates that the same email may already have been sent or the previous
            sending result is uncertain, do not automatically retry.

        11. Do not send bulk emails or add recipients unless explicitly authorized
            by the user/admin.

        12. If no subject is provided, generate a concise professional subject based
            only on the verified purpose of the email.

        13. Do not claim that an attachment exists unless it is actually available
            and attached.

        14. Do not make legal, financial, employment, contractual, or other significant
            commitments unless explicitly authorized by the user/admin.

        15. If required information is missing or uncertain, ask for clarification
            instead of fabricating it.

        BEFORE CALLING THIS TOOL:
        - Verify that sending was explicitly requested.
        - Verify that the recipient email is valid and authorized.
        - Verify that the subject is relevant.
        - Verify that the body contains only verified information.
        - Verify that the author's identity comes from authorInformation.
        - Never guess the recipient's name.
        - Do not add unnecessary information.
        - Make sure the email is not a duplicate.
        - Do not make unauthorized commitments or claims.

        CORE PRINCIPLE:
        Never guess when sending an email. Use authorized context only, preserve
        the user's intent, prevent duplicate or spam emails, and prefer clarification
        over an incorrect external action.
        """)
    public void sendEmail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom(senderEmail);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);

        mailSender.send(message);
    }

}
