package com.tcloudsoft.utils.common;

import java.io.Serializable;

import lombok.Data;

@Data
public class HealthCheckSource implements Serializable {

	private static final long serialVersionUID = 8309704180225451639L;

	private Integer id;
	private String ip;
	private String userName;
	private String password;
}
