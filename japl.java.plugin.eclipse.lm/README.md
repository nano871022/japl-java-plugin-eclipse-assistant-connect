# LM Studio Eclipse Plugin

This plugin integrates LM Studio into the Eclipse IDE, providing AI-powered assistance for code-related tasks.

## Objective

The main objective of this plugin is to leverage the power of Large Language Models (LLMs) to assist developers with their daily tasks, such as:

*   **Code Explanation:** Understand complex code snippets by getting natural language explanations.
*   **Comment Generation:** Automatically generate comments for your code to improve readability and maintainability.
*   **Test Creation:** Generate unit tests for your code to ensure its quality and correctness.
*   **Code Completion:** Get intelligent code completion suggestions.

## How it Works

The plugin connects to a local LM Studio instance, which exposes an OpenAI-compatible API. When you invoke one of the plugin's commands, it sends the selected code snippet and a corresponding prompt to the LM Studio API. The LLM then processes the request and returns a response, which is displayed in the "LM Studio Chat" view.

The plugin is built using the Eclipse Plugin Development Environment (PDE) and standard Java libraries. It uses the `java.net.http.HttpClient` to communicate with the LM Studio API and the `org.json` library to parse the JSON responses.

## How to Modify or Add More Functionality

The plugin is designed to be extensible. To add new functionality, you can follow these steps:

1.  **Add a new command:** Define a new command in the `plugin.xml` file.
2.  **Add a new handler:** Create a new handler class that extends the `AbstractLMStudioHandler` class.
3.  **Implement the `getPrompt` method:** In your new handler class, implement the `getPrompt` method to provide the specific prompt for your new command.
4.  **Add a new menu item (optional):** Add a new menu item to the `plugin.xml` file to make your new command accessible from the UI.

## How to Install as a Plugin

To install the plugin, you can export it as a deployable feature and install it into your Eclipse IDE using the Eclipse update manager.

1.  **Export the plugin:** Right-click on the plugin project and select "Export..." -> "Deployable features".
2.  **Select the feature:** Select the `japl.java.plugin.eclipse.lm.feature` feature.
3.  **Specify the destination:** Choose a directory to export the feature to.
4.  **Install the feature:** In your Eclipse IDE, go to "Help" -> "Install New Software...".
5.  **Add the repository:** Click "Add..." and then "Local..." to select the directory where you exported the feature.
6.  **Install the plugin:** Select the "LM Studio" feature and follow the installation wizard.
