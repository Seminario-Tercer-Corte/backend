package com.seminario.api

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultMatcher
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext

@SpringBootTest
class GreetingTests {

	@Autowired
	private var mvc: MockMvc? = null

	@Autowired
	val webApplicationContext: WebApplicationContext? = null

	@BeforeEach
	fun setUp() {
		mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext!!).build()
	}

	@Test
	fun greeting() {
		mvc!!.perform(MockMvcRequestBuilders.get("/greeting")).andExpect(
				ResultMatcher.matchAll(
						MockMvcResultMatchers.status().isOk
				)
		)
	}

}
