package com.example.lmstudio.handlers;

public class GenerateCommentsHandler extends AbstractLMStudioHandler {

	@Override
	protected String getPrompt(String selectedText) {
		return "Generate comments for the following code:\n" + selectedText;
	}

}
