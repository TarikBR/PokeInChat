package PokeInChat;

import com.google.inject.Inject;
import com.pixelmonmod.pixelmon.Pixelmon;
import com.pixelmonmod.pixelmon.storage.PlayerPartyStorage;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.filter.Getter;
import org.spongepowered.api.event.message.MessageChannelEvent.Chat;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.serializer.TextSerializers;

public class PokeChat {
   @Inject
   PokeData pokeData = new PokeData();

   @Listener
   public void onChatMessage(Chat event, @Getter("getSource") Player player) {
	  String msg = event.toString();
	  Text mensagem = Text.of(TextColors.AQUA, TextSerializers.FORMATTING_CODE.deserialize(msg));
	  if(msg.contains("@poke1") || msg.contains("@poke2") || msg.contains("@poke3") || msg.contains("@poke4") || msg.contains("@poke5") || msg.contains("@poke6")) {
		  event.setCancelled(true);
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
	      //Text finalmessage = Text.builder("[").color(TextColors.DARK_GRAY).append(new Text[]{Text.builder(player.getName()).color(TextColors.LIGHT_PURPLE).build()}).append(new Text[]{Text.builder("] ").color(TextColors.DARK_GRAY).build()}).append(new Text[]{Text.builder(": ").color(TextColors.AQUA).build()}).append(new Text[]{mensagem}).build();
	      //MessageChannel.TO_ALL.send(Text.of(msg));
		player.getMessageChannel().send(mensagem);
	  }
   }
}