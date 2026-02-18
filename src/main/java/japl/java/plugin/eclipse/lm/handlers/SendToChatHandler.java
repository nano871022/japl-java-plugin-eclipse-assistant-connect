package japl.java.plugin.eclipse.lm.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;
import japl.java.plugin.eclipse.lm.views.ChatView;

public class SendToChatHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindow(event);
		IWorkbenchPage page = window.getActivePage();
		IEditorPart editor = page.getActiveEditor();

		if (editor != null) {
			ISelection selection = editor.getEditorSite().getSelectionProvider().getSelection();
			if (selection instanceof ITextSelection) {
				ITextSelection textSelection = (ITextSelection) selection;
				String selectedText = textSelection.getText();
				if (!selectedText.isEmpty()) {
					try {
						ChatView chatView = (ChatView) page.showView(ChatView.ID);
						chatView.sendMessage(selectedText);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}

		return null;
	}

}
