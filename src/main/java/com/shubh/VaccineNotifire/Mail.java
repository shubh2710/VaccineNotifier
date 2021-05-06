package com.shubh.VaccineNotifire;

import java.util.List;

import javax.mail.internet.InternetAddress;



public class Mail {

	public InternetAddress getFromEmailAddr() {
		return fromEmailAddr;
	}

	public void setFromEmailAddr(InternetAddress fromEmailAddr) {
		this.fromEmailAddr = fromEmailAddr;
	}

	private InternetAddress fromEmailAddr;

	public List<InternetAddress> getToEmailAddr() {
		return toEmailAddr;
	}

	public void setToEmailAddr(List<InternetAddress> toEmailAddr) {
		this.toEmailAddr = toEmailAddr;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getAttachment() {
		return attachment;
	}

	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}

	private List<InternetAddress> toEmailAddr;
	private String subject;
	private String body;
	private String attachment;

	public InternetAddress[] getToEmailAddrAsArray() {
		return (InternetAddress[]) toEmailAddr.toArray(new InternetAddress[toEmailAddr.size()]);
	}

}
