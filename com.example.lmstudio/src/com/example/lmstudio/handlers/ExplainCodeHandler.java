package com.example.lmstudio.handlers;

public class ExplainCodeHandler extends AbstractLMStudioHandler {

	@Override
	protected String getPrompt(String selectedText) {
		return "Explain the following code:\n" + selectedText;
	}

}
