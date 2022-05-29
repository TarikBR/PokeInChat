package PokeInChat;

import java.util.concurrent.TimeUnit;

import org.spongepowered.api.Game;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.data.type.HandTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.event.network.ClientConnectionEvent;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.Text.Builder;
import org.spongepowered.api.text.action.TextActions;
import org.spongepowered.api.text.channel.MessageChannel;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.serializer.TextSerializers;
import com.google.inject.Inject;
import com.pixelmonmod.pixelmon.Pixelmon;
import com.pixelmonmod.pixelmon.storage.PlayerPartyStorage;

@Plugin(
		   id = "pokeanuncio",
		   name = "PokeAnúncio",
		   version = "1.3.2",
		   description = "Anuncie compras/vendas",
		   authors = {"Tarik"}
		)

public class Comando {
	@Inject
	Game game;
	
	@Listener
	public void onInit(GameInitializationEvent e) {
		CommandSpec anuncio = CommandSpec.builder()
				.description(Text.of("Anuncie vendas/trocas. \n(@poke<slot> para mencionar um pokemon da sua party e @i@ com o item na mão para mencionar o item)"))
				.permission("pokeinchat.anuncio")
				.arguments(GenericArguments.remainingJoinedStrings(Text.of("message")))
				.executor((CommandSource src, CommandContext args) -> {
					  Player player = (Player)src;
						  if(!player.hasTag("pokeinchatdelaycommand")) {
							  String msg = args.getOne("message").get().toString();
							  Text mensagem;
							  if(msg.contains("@poke1") || msg.contains("@poke2") || msg.contains("@poke3") || msg.contains("@poke4") || msg.contains("@poke5") || msg.contains("@poke6") || msg.contains("@i@")) {
							      PlayerPartyStorage playerParty = Pixelmon.storageManager.getParty(player.getUniqueId());
							      for(int i = 0; i < 6; i++) {
							    	  msg = msg.replaceFirst("@poke" + (i+1), "@MudarAkiPoke" + i);
							      }
							      msg = msg.replaceFirst("@i@", "@MudarAkiItem");
							      mensagem = Text.of(TextColors.AQUA, TextSerializers.FORMATTING_CODE.deserialize(msg));
							      for(int i = 0; i < 6; i++) {
							    	  if(msg.contains("@MudarAkiPoke" + i)) {
							    		  if (!PokeData.isNull(playerParty.get(i))) {
							    			  Builder statBuilder = Text.builder();
							    			  mensagem = Text.of(new Object[]{mensagem.replace("@MudarAkiPoke" + i, statBuilder.append(new Text[]{TextSerializers.FORMATTING_CODE.deserialize("&8[&r&a" + playerParty.get(i).getSpecies().name + "&8]&r")}).onHover(TextActions.showText(PokeData.getHoverText(playerParty.get(i)))).build())});
							    		  } else {
							    			  player.sendMessage(TextSerializers.FORMATTING_CODE.deserialize("&bVocê não tem pokemon no slot &l1&b!"));
							    		  }
							    	  }
							      }
							      if (msg.contains("@MudarAkiItem")) {
			                        ItemStack hand = player.getItemInHand(HandTypes.MAIN_HAND).get();
			                        if (!hand.getType().equals(ItemTypes.NONE)) {
			                        	Builder item = Text.builder();
			                        	item.append(new Text[]{TextSerializers.FORMATTING_CODE.deserialize("&8[&a" + hand.getQuantity() + " " + hand.getTranslation().get() + "&8]&r")});
			                        	if(!hand.getType().getName().contains("shulker")) {
			                        		item.onHover(TextActions.showItem(hand.createSnapshot()));
			                        	}
			                            mensagem = Text.of(mensagem.replace("@MudarAkiItem", Text.of(new Object[] {item})));
			                        } else {
			                        	player.sendMessage(TextSerializers.FORMATTING_CODE.deserialize("&bVocê está sem item na mão!"));
			                        }
			                      }
							      Text inicio = Text.builder().append(new Text[]{TextSerializers.FORMATTING_CODE.deserialize("{chat}" + "{name}" + " &b»&r ")}).build();
							      Text name = Text.builder().append(new Text[]{TextSerializers.FORMATTING_CODE.deserialize("&f" + player.getName() + "&r")}).onClick(TextActions.suggestCommand("/tell " + player.getName() + " ")).onHover(TextActions.showText(Text.of(new Object[] {TextColors.YELLOW, "Clique para mandar um tell"}))).build();
							      Text chat = Text.builder().append(new Text[]{TextSerializers.FORMATTING_CODE.deserialize("&b&l[Anúncio]&r ")}).onClick(TextActions.suggestCommand("/anuncio ")).onHover(TextActions.showText(Text.of(new Object[] {TextColors.YELLOW, "Clique para anunciar algo"}))).build();
							      Text finalmessage = Text.builder().append(inicio.replace("{name}", name).replace("{chat}", chat)).append(new Text[]{mensagem}).build();
							      MessageChannel.TO_ALL.send(Text.of(finalmessage));
							      player.addTag("pokeinchatdelaycommand");
							      Task.builder().delay(1, TimeUnit.MINUTES).execute((Runnable)(new Runnable() {
							            public final void run() {
							            	player.removeTag("pokeinchatdelaycommand");
							               }
							         })).submit(this);
							      return CommandResult.success();
							  }
							  else {
								  Text inicio = Text.builder().append(new Text[]{TextSerializers.FORMATTING_CODE.deserialize("{chat}" + "{name}" + " &b»&r ")}).build();
							      Text name = Text.builder().append(new Text[]{TextSerializers.FORMATTING_CODE.deserialize("&f" + player.getName() + "&r")}).onClick(TextActions.suggestCommand("/tell " + player.getName() + " ")).onHover(TextActions.showText(Text.of(new Object[] {TextColors.YELLOW, "Clique para mandar um tell"}))).build();
							      Text chat = Text.builder().append(new Text[]{TextSerializers.FORMATTING_CODE.deserialize("&b&l[Anúncio]&r ")}).onClick(TextActions.suggestCommand("/anuncio ")).onHover(TextActions.showText(Text.of(new Object[] {TextColors.YELLOW, "Clique para anunciar algo"}))).build();
							      Text finalmessage = Text.builder().append(inicio.replace("{name}", name).replace("{chat}", chat)).append(new Text[]{Text.of(TextColors.AQUA, TextSerializers.FORMATTING_CODE.deserialize(msg))}).build();
							      MessageChannel.TO_ALL.send(Text.of(finalmessage));
							      player.addTag("pokeinchatdelaycommand");
							      Task.builder().delay(1, TimeUnit.MINUTES).execute((Runnable)(new Runnable() {
							            public final void run() {
							            	player.removeTag("pokeinchatdelaycommand");
							               }
							         })).submit(this);
							      return CommandResult.success();
							  	}
							  }
						  else {
							  player.sendMessage(TextSerializers.FORMATTING_CODE.deserialize("&4Aguarde para usar novamente o comando!"));
							  return CommandResult.success();
						  }
				})
				.build();
		
		CommandSpec info = CommandSpec.builder()
				.description(Text.of("Veja informações sobre seu pokemon."))
				.arguments(GenericArguments.onlyOne(GenericArguments.integer(Text.of("slot"))))
				.executor((CommandSource src, CommandContext args) -> {
					Player player = (Player) src;
					Object slotChar = args.getOne("slot").get();
			        Integer slot = Integer.parseInt(slotChar.toString()) - 1;
					if(slot >= 0 && slot < 6) {
						PlayerPartyStorage playerParty = Pixelmon.storageManager.getParty(player.getUniqueId());
						if(!PokeData.isNull(playerParty.get(slot))) {
							player.sendMessage(Text.of(PokeData.getHoverText(playerParty.get(slot))));
						}
						else {
							player.sendMessage(TextSerializers.FORMATTING_CODE.deserialize("&bVocê não tem pokemon no slot &l" + slot + "&b!"));
						}
						return CommandResult.success();
					}
					return CommandResult.success();
				})
				.build();
		
		game.getCommandManager().register(this, anuncio, "anuncio", "anunciarpoke");
		game.getCommandManager().register(this, info, "pokeinfo");
	}
	
	@Listener
	public void onPlayerJoin(ClientConnectionEvent.Join player) {
		if(player.getTargetEntity().hasTag("pokeinchatdelaycommand")) {
			player.getTargetEntity().removeTag("pokeinchatdelaycommand");
		}
	}
}