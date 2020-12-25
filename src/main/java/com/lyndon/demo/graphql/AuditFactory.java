package com.lyndon.demo.graphql;

import graphql.kickstart.tools.TypeDefinitionFactory;
import graphql.language.*;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AuditFactory implements TypeDefinitionFactory {

	@NotNull
	@Override
	public List<Definition<?>> create(@NotNull List<Definition<?>> list) {
		List<Definition<?>> definitions = new ArrayList<>();
		Map<String, TypeDefinition<?>> definitionsByName = list.stream().filter(item -> item instanceof TypeDefinition)
				.map(item -> (TypeDefinition<?>) item).collect(Collectors.toMap(NamedNode::getName, item -> item));
		findAuditDirectives(list).stream().map(this::createDefinition).forEach(definition -> {
			if (!definitionsByName.containsKey(definition.getName())) {
				definitionsByName.put(definition.getName(), definition);
				definitions.add(definition);
			}
		});
		return definitions;
	}

	/**
	 * 寻找含有审计指令的输入类型
	 *
	 * @param list
	 *            类型定义列表
	 * @return 指令与输入类型封装类集合
	 */
	private List<DirectiveWithInputType> findAuditDirectives(List<Definition<?>> list) {
		return list.stream().filter(item -> item instanceof ObjectTypeDefinition)
				.map(item -> (ObjectTypeDefinition) item).flatMap(item -> item.getFieldDefinitions().stream())
				.flatMap(item -> item.getInputValueDefinitions().stream())
				.flatMap(item -> item.getDirectives().stream()
						.map(directive -> new DirectiveWithInputType(item, directive.getName(),
								directive.getArguments(), directive.getSourceLocation(), directive.getComments())))
				.filter(item -> "audit".equals(item.getName())).collect(Collectors.toList());
	}

	/**
	 * 创建输入类型定义
	 *
	 * @param directive
	 *            指令与类型封装对象
	 * @return 输入类型定义
	 */
	private InputObjectTypeDefinition createDefinition(DirectiveWithInputType directive) {
		return InputObjectTypeDefinition.newInputObjectDefinition().name(directive.getTypeName())
				.inputValueDefinition(new InputValueDefinition("content", new TypeName(directive.forTypeName())))
				.inputValueDefinition(new InputValueDefinition("operator", new TypeName("String")))
				.inputValueDefinition(new InputValueDefinition("description", new TypeName("String")))
				.inputValueDefinition(new InputValueDefinition("method", new TypeName("String")))
				.inputValueDefinition(new InputValueDefinition("args", new ListType(new TypeName("String"))))
				.inputValueDefinition(new InputValueDefinition("messages", new ListType(new TypeName("String"))))
				.inputValueDefinition(
						new InputValueDefinition("messagePathsFromResult", new ListType(new TypeName("String"))))
				.build();
	}

	private static class DirectiveWithInputType extends Directive {

		InputValueDefinition definition;

		DirectiveWithInputType(InputValueDefinition definition, String name, List<Argument> arguments,
				SourceLocation sourceLocation, List<Comment> comments) {
			super(name, arguments, sourceLocation, comments, IgnoredChars.EMPTY, Collections.emptyMap());
			this.definition = definition;
		}

		String forTypeName() {
			return ((StringValue) this.getArgument("for").getValue()).getValue();
		}

		String getTypeName() {
			Type<?> type = definition.getType();
			if (type instanceof NonNullType) {
				return ((TypeName) ((NonNullType) type).getType()).getName();
			}
			return ((TypeName) definition.getType()).getName();
		}
	}

}
