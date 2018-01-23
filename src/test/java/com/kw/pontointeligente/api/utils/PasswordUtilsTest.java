package com.kw.pontointeligente.api.utils;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordUtilsTest {

	private static final String SENHA = "1234567";
	private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

	@Test
	public void testNullPassword() throws Exception {
		assertNull(PasswordUtils.gerarBCrypt(null));
	}

	@Test
	public void testGenerateHashPassword() throws Exception {
		String hash = PasswordUtils.gerarBCrypt(SENHA);
		assertTrue(bCryptPasswordEncoder.matches(SENHA, hash));
	}

}
