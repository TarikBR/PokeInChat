package PokeInChat;

import java.util.concurrent.TimeUnit;
import org.spongepowered.api.Game;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandSpec;
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
import br.net.fabiozumbi12.UltimateChat.Sponge.UChat;

@Plugin(
		   id = "pokeanuncio",
		   name = "PokeAnúncio",
		   version = "1.1",
		   description = "Anuncie compras/vendas",
		   authors = {"Tarik"}
		)

public class Comando {
	@Inject
	Game game;
	
	@Listener
	public void onInit(GameInitializationEvent e) {
		@SuppressWarnings("deprecation")
		CommandSpec anuncio = CommandSpec.builder()
				.description(Text.of("Anuncie vendas/trocas. \n(@poke<slot> para mencionar um pokemon da sua party e @i@ com o item na mão para mencionar o item)"))
				.permission("pokeinchat.anuncio")
				.arguments(GenericArguments.remainingJoinedStrings(Text.of("message")))
				.executor((CommandSource src, CommandContext args) -> {
					  Player player = (Player)src;
					  if(!UChat.get().mutes.contains(player.getName())) {
						  if(!player.hasTag("pokeinchatdelaycommand")) {
							  String msg = args.<String>getOne("message").get();
							  Text mensagem = Text.of(TextColors.AQUA, TextSerializers.FORMATTING_CODE.deserialize(msg));
							  if(msg.contains("@poke1") || msg.contains("@poke2") || msg.contains("@poke3") || msg.contains("@poke4") || msg.contains("@poke5") || msg.contains("@poke6") || msg.contains("@i@")) {
							      PlayerPartyStorage playerParty = Pixelmon.storageManager.getParty(player.getUniqueId());
							      if (msg.contains("@poke1")) {
							         if (!PokeData.isNull(playerParty.get(0))) {
							            mensagem = Text.of(mensagem.replace("@poke1", PokeData.getHoverText(playerParty.get(0))));
							         } else {
							            player.sendMessage(TextSerializers.FORMATTING_CODE.deserialize("&bVocê não tem pokemon no slot &l1&b!"));
							         }
							      }
							      if (msg.contains("@poke2")) {
							          if (!PokeData.isNull(playerParty.get(1))) {
							             mensagem = Text.of(mensagem.replace("@poke2", PokeData.getHoverText(playerParty.get(1))));
							          } else {
							             player.sendMessage(TextSerializers.FORMATTING_CODE.deserialize("&bVocê não tem pokemon no slot &l2&b!"));
							          }
							      }
							      if (msg.contains("@poke3")) {
							          if (!PokeData.isNull(playerParty.get(2))) {
							             mensagem = Text.of(mensagem.replace("@poke3", PokeData.getHoverText(playerParty.get(2))));
							          } else {
							             player.sendMessage(TextSerializers.FORMATTING_CODE.deserialize("&bVocê não tem pokemon no slot &l3&b!"));
							          }
							      }
							      if (msg.contains("@poke4")) {
							          if (!PokeData.isNull(playerParty.get(3))) {
							             mensagem = Text.of(mensagem.replace("@poke4", PokeData.getHoverText(playerParty.get(3))));
							          } else {
							             player.sendMessage(TextSerializers.FORMATTING_CODE.deserialize("&bVocê não tem pokemon no slot &l4&b!"));
							          }
							      }
							      if (msg.contains("@poke5")) {
							          if (!PokeData.isNull(playerParty.get(4))) {
							             mensagem = Text.of(mensagem.replace("@poke5", PokeData.getHoverText(playerParty.get(4))));
							          } else {
							             player.sendMessage(TextSerializers.FORMATTING_CODE.deserialize("&bVocê não tem pokemon no slot &l5&b!"));
							          }
							      }
							      if (msg.contains("@poke6")) {
							          if (!PokeData.isNull(playerParty.get(5))) {
							             mensagem = Text.of(mensagem.replace("@poke6", PokeData.getHoverText(playerParty.get(5))));
							          } else {
							             player.sendMessage(TextSerializers.FORMATTING_CODE.deserialize("&bVocê não tem pokemon no slot &l6&b!"));
							          }
							      }
							      if (msg.contains("@i@")) {
			                        ItemStack hand = UChat.get().getVHelper().getItemInHand(player);
			                        if (!hand.getItem().equals(ItemTypes.NONE)) {
			                        	Builder item = Text.builder();
			                        	String itemName = hand.getType().getTranslation().get();
			                        	item.append(new Text[]{TextSerializers.FORMATTING_CODE.deserialize("&8[&a" + hand.getQuantity() + " " + itemName + "&8]&r")});
			                        	item.onHover(TextActions.showItem(hand.createSnapshot()));
			                            mensagem = Text.of(mensagem.replace("@i@", Text.of(new Object[] {item})));
			                        } else {
							             player.sendMessage(TextSerializers.FORMATTING_CODE.deserialize("&bVocê está sem item na mão!"));
							          }
			                      }
							      Text inicio = Text.builder().append(new Text[]{TextSerializers.FORMATTING_CODE.deserialize("{chat}" + "{name}" + " &e»&r ")}).build();
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
								  Text inicio = Text.builder().append(new Text[]{TextSerializers.FORMATTING_CODE.deserialize("{chat}" + "{name}" + " &e»&r ")}).build();
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
							  }
						  else {
							  player.sendMessage(TextSerializers.FORMATTING_CODE.deserialize("&4Aguarde para usar novamente o comando!"));
							  return CommandResult.success();
						  }
					  }
					  else {
						  player.sendMessage(TextSerializers.FORMATTING_CODE.deserialize("&4Aguarde para usar novamente o comando!"));
						  return CommandResult.success();
					  }
				})
				.build();
		game.getCommandManager().register(this, anuncio, "anuncio", "anunciarpoke");
	}
	
	@Listener
	public void onPlayerJoin(ClientConnectionEvent.Join player) {
		if(player.getTargetEntity().hasTag("pokeinchatdelaycommand")) {
			player.getTargetEntity().removeTag("pokeinchatdelaycommand");
		}
	}
}
