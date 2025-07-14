package com.example.lmstudio.handlers;

public class GenerateTestsHandler extends AbstractLMStudioHandler {

	@Override
	protected String getPrompt(String selectedText) {
		return "Generate unit tests for the following code:\n" + selectedText;
	}

}
