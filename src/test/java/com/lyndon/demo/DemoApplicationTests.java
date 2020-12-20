package com.lyndon.demo;

import com.lyndon.demo.service.ActorService;
import graphql.ExecutionResult;
import graphql.GraphQL;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;

import javax.transaction.Transactional;
import java.util.Map;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DemoApplicationTests {

	@Autowired
	private ActorService actorService;

	@Autowired
	private GraphQL graphQL;

	@Test
	@Transactional
	public void contextLoads() {
		System.out.println(actorService.findOne(1L));
	}

	@Test
	public void test() throws Exception {
		ExecutionResult result = graphQL.execute(IOUtils.toString(new ClassPathResource("./query.graphql").getInputStream()));
		Map map = (Map) result.getData();
		System.out.println(map.get("actorById"));
	}

	@Test
	public void add() throws Exception {
		ExecutionResult result = graphQL.execute(IOUtils.toString(new ClassPathResource("./modify.graphql").getInputStream()));
		Map map = (Map) result.getData();
		System.out.println(map.get("createActor"));
	}

	@Test
	public void page() throws Exception {
		ExecutionResult result = graphQL.execute(IOUtils.toString(new ClassPathResource("./query.graphql").getInputStream()));
		Map map = (Map) result.getData();
		System.out.println(map.get("actors"));
	}

}
