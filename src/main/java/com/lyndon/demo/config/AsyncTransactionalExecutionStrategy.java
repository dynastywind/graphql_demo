package com.lyndon.demo.config;

import graphql.ExecutionResult;
import graphql.execution.AsyncExecutionStrategy;
import graphql.execution.ExecutionContext;
import graphql.execution.ExecutionStrategyParameters;
import graphql.execution.NonNullableFieldWasNullException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.concurrent.CompletableFuture;

@Service
public class AsyncTransactionalExecutionStrategy extends AsyncExecutionStrategy {

	@Override
	@Transactional
	public CompletableFuture<ExecutionResult> execute(ExecutionContext executionContext,
			ExecutionStrategyParameters parameters) throws NonNullableFieldWasNullException {
		return super.execute(executionContext, parameters);
	}

}
