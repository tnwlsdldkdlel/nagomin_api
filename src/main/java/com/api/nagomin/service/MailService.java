package com.api.nagomin.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.api.nagomin.dto.MailDto;
import com.api.nagomin.util.MailHandler;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MailService {
	private final JavaMailSender mailSender;

	@Value("${spring.mail.username}")
	private String from;

	public void mailSend(MailDto mailDto) {
		try {
			MailHandler mailHandler = new MailHandler(mailSender);
			mailHandler.setTo(mailDto.getAddress());
			mailHandler.setFrom(from);
			mailHandler.setSubject(mailDto.getTitle());
			String htmlContent = "<p>" + mailDto.getMessage();
			mailHandler.setText(htmlContent, true);
			mailHandler.send();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
