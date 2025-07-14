package japl.java.plugin.eclipse.lm.views;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.part.ViewPart;
import org.json.JSONException;
import org.json.JSONObject;

public class ChatView extends ViewPart {

	public static final String ID = "japl.java.plugin.eclipse.lm.views.ChatView";
	private Text conversation;
	private Text message;
	private Action explainCodeAction;
	private Action generateCommentsAction;
	private Action generateTestsAction;

	public ChatView() {
	}

	@Override
	public void createPartControl(Composite parent) {
		GridLayout layout = new GridLayout(2, false);
		parent.setLayout(layout);

		conversation = new Text(parent, SWT.BORDER | SWT.V_SCROLL | SWT.MULTI | SWT.READ_ONLY);
		GridData conversationData = new GridData(SWT.FILL, SWT.FILL, true, true);
		conversationData.horizontalSpan = 2;
		conversation.setLayoutData(conversationData);

		message = new Text(parent, SWT.BORDER | SWT.SINGLE);
		message.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

		Button sendButton = new Button(parent, SWT.PUSH);
		sendButton.setText("Send");
		sendButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				sendMessage();
			}
		});

		makeActions();
		contributeToActionBars();
	}

	private void contributeToActionBars() {
		IActionBars bars = getViewSite().getActionBars();
		fillLocalToolBar(bars.getToolBarManager());
	}

	private void fillLocalToolBar(IToolBarManager manager) {
		manager.add(explainCodeAction);
		manager.add(generateCommentsAction);
		manager.add(generateTestsAction);
	}

	private void makeActions() {
		explainCodeAction = new Action() {
			public void run() {
				sendMessage("Explain the following code:\n" + message.getText());
			}
		};
		explainCodeAction.setText("Explain Code");
		explainCodeAction.setToolTipText("Explain Code");

		generateCommentsAction = new Action() {
			public void run() {
				sendMessage("Generate comments for the following code:\n" + message.getText());
			}
		};
		generateCommentsAction.setText("Generate Comments");
		generateCommentsAction.setToolTipText("Generate Comments");

		generateTestsAction = new Action() {
			public void run() {
				sendMessage("Generate unit tests for the following code:\n" + message.getText());
			}
		};
		generateTestsAction.setText("Generate Tests");
		generateTestsAction.setToolTipText("Generate Tests");
	}

	public void sendMessage(String message) {
		if (message.isEmpty()) {
			return;
		}

		conversation.append("You: " + message + "\n");

		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create("http://localhost:12345/v1/chat/completions"))
				.header("Content-Type", "application/json")
				.POST(HttpRequest.BodyPublishers.ofString(
						"{\"messages\": [{\"role\": \"user\", \"content\": \"" + message + "\"}], \"max_tokens\": 100}"))
				.build();

		try {
			HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
			String responseBody = response.body();
			JSONObject jsonResponse = new JSONObject(responseBody);
			String messageContent = jsonResponse.getJSONArray("choices").getJSONObject(0).getJSONObject("message").getString("content");
			conversation.append("LM Studio: " + messageContent + "\n");
		} catch (IOException | InterruptedException | JSONException ex) {
			ex.printStackTrace();
			MessageDialog.openError(getSite().getShell(), "Error", "Error communicating with LM Studio: " + ex.getMessage());
			conversation.append("Error: " + ex.getMessage() + "\n");
		}
	}

	private void sendMessage() {
		sendMessage(message.getText());
		message.setText("");
	}

	@Override
	public void setFocus() {
		message.setFocus();
	}

}
