package botCore;

import java.io.Serializable;

public abstract class MessageListenerCompleteListener implements Serializable {
	private static final long serialVersionUID = -2657230668120775332L;
	MessageListenerLambda ml;
	String[] command;
	Command[] lambda;
	public MessageListenerCompleteListener(MessageListenerLambda MLA, String[] trigger, Command[] cmd) {
		this.ml=MLA;
		this.command=trigger;
		this.lambda=cmd;
	}
	abstract void addOption();
	public MessageListenerCompleteListener(MessageListenerLambda ML) {
		ml = ML;
	}
	
	public void run() {
		for(int i = 0;i<command.length;i++) {
			ml.addCommand(command[i],lambda[i]);
		}
	}
	
	public MessageListenerCompleteListener addMLL(MessageListenerLambda MLL) {
		this.ml=MLL;
		return this;
	}
}
