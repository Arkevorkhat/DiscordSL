package botCore;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import sx.blah.discord.handle.impl.obj.Channel;
import sx.blah.discord.handle.impl.obj.Guild;
import sx.blah.discord.handle.obj.IUser;
import variableStores.ObjProperties;

public class ServerPref<E> extends ArrayList<E> implements Serializable{
	private static final long serialVersionUID = 1L;
	public long replChannelID;
	public long GuildID;
	public ObjProperties properties;
	public ArrayList<IUser> administratorList;
	public ServerPref(Guild guild, ObjProperties p){
		this.GuildID=guild.getLongID();
		this.properties=p;
	}
	public ServerPref(long GuildID, ObjProperties p) {
		this.GuildID=GuildID;
		this.properties=p;
	}
	public ServerPref(long GuildID, ObjProperties p, long replyChannel) {
		this.GuildID=GuildID;
		this.properties=p;
		this.replChannelID = replyChannel;
	}
	@SuppressWarnings("unchecked")
	public ArrayList<E> getSerialData(){
		ArrayList<E> AL =new ArrayList<E>();
		AL.addAll((List<E>)Arrays.asList(this.toArray()));
		return AL;
	}
	public Guild getGuild() {
		return (Guild) Engine.botClient.getGuildByID(this.GuildID);
	}
	public Channel getReplChannel() {
		return (Channel) Engine.botClient.getChannelByID(replChannelID);
	}
	public void setReplChannel(long channelID) {
		this.replChannelID=channelID;
	}
}
