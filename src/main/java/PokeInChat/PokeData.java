package PokeInChat;

import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.api.pokemon.PokemonSpec;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.Text.Builder;
import org.spongepowered.api.text.action.TextActions;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.format.TextStyles;
import org.spongepowered.api.text.serializer.TextSerializers;

public class PokeData {
   public static Boolean isNull(Pokemon slot) {
      return slot != null ? false : true;
   }
   public static PokemonSpec UNBREEDABLE;
   static Text isBreed(Pokemon pokemon){
       UNBREEDABLE = PokemonSpec.from ("unbreedable");
       if(UNBREEDABLE.matches(pokemon)) {
    	   return Text.of(new Object[] {TextColors.RED, "Unbreedable"});
       }
       else {
    	   return Text.of(new Object[] {TextColors.GREEN, "Breedable"});
       }
   }
   static Text aumenta(Pokemon pokemon) {
	   switch(pokemon.getNature().increasedStat.name().toString().toLowerCase()) {
		   case "attack":
			   return Text.of(new Object[] {TextColors.GREEN, "+Atk"});
		   case "specialattack":
			   return Text.of(new Object[] {TextColors.GREEN, "+SpAtk"});
		   case "defence":
			   return Text.of(new Object[] {TextColors.GREEN, "+Def"});
		   case "specialdefence":
			   return Text.of(new Object[] {TextColors.GREEN, "+SpDef"});
		   case "speed":
			   return Text.of(new Object[] {TextColors.GREEN, "+Speed"});
		   default:
			   return null;
	   }
   }
   static Text diminui(Pokemon pokemon) {
	   switch(pokemon.getNature().decreasedStat.name().toString().toLowerCase()) {
		   case "attack":
			   return Text.of(new Object[] {TextColors.RED, "-Atk"});
		   case "specialattack":
			   return Text.of(new Object[] {TextColors.RED, "-SpAtk"});
		   case "defence":
			   return Text.of(new Object[] {TextColors.RED, "-Def"});
		   case "specialdefence":
			   return Text.of(new Object[] {TextColors.RED, "-SpDef"});
		   case "speed":
			   return Text.of(new Object[] {TextColors.RED, "-Speed"});
		   default:
			   return null;
	   }
   }
   static Text form(Pokemon pokemon) {
	   switch(pokemon.getFormEnum().toString().toLowerCase()) {
		   	case "noform":
		   		return Text.of(new Object[] {TextColors.YELLOW, "N/A"});
		   case "galar_standard":
			   return Text.of(new Object[] {TextColors.YELLOW, "Galarian"});
		   case "singlestrike":
			   return Text.of(new Object[] {TextColors.YELLOW, "Simple Strike - Dark"});
		   case "rapidstrike":
			   return Text.of(new Object[] {TextColors.YELLOW, "Rapid Strike - Water"});
		   default:
			   String forma = pokemon.getFormEnum().toString().toLowerCase();
			   StringBuffer forma2 = new StringBuffer(forma);
			     
			    for(int i = 0; i < forma2.length(); i++){
			        Character letra = forma2.charAt(i);
			        if(i == 0){
			            letra = Character.toUpperCase(letra);
			            forma2.setCharAt(i, letra);
			        }
			        else if((i > 0) && (forma2.charAt(i - 1) == ' ')){
			            letra = Character.toUpperCase(letra);
			            forma2.setCharAt(i, letra);
			        }
			    }
			    forma = forma2.toString(); 
			   return Text.of(new Object[] {TextColors.YELLOW, forma});
	   }
   }
   @SuppressWarnings("deprecation")
   public static Text getHoverText(Pokemon pokemonData) {
      Builder statBuilder = Text.builder();
      statBuilder.append(new Text[]{TextSerializers.FORMATTING_CODE.deserialize("&8[&r&a" + pokemonData.getSpecies().name + "&8]&r")});
      Text statHover = Text.of(new Object[]{
    		  //pokemon + level
    		  TextColors.LIGHT_PURPLE, TextStyles.BOLD, pokemonData.isShiny() ? Text.of(new Object[]{TextColors.YELLOW, "★"}) : Text.EMPTY, pokemonData.getSpecies().name, pokemonData.getGender().toString() == "None" ? Text.EMPTY : Text.of(new Object[]{pokemonData.getGender().toString() == "Male" ? Text.of(new Object[]{TextColors.DARK_AQUA, " ♂"}) : Text.of(new Object[]{TextColors.DARK_PURPLE, " ♀"})}), TextStyles.RESET, TextColors.DARK_GRAY, " | ", TextColors.GRAY, "Level: ", TextColors.GRAY, pokemonData.getLevel(),
			  //nature
			  Text.NEW_LINE, TextColors.GRAY, "Nature: ", TextColors.YELLOW, pokemonData.getNature().toString(), TextColors.DARK_GRAY, " [", pokemonData.getNature().increasedStat.name() == "None" ? Text.of(new Object[]{TextColors.GRAY, "Neutro"}) : Text.of(new Object[]{aumenta(pokemonData), TextColors.DARK_GRAY, " | ", diminui(pokemonData)}), TextColors.DARK_GRAY, "]",
			  //habilidade
			  Text.NEW_LINE, TextColors.GRAY, "Ability: ", TextColors.YELLOW, pokemonData.getAbilitySlot() != 2 ? TextColors.YELLOW : TextColors.GOLD, pokemonData.getAbility().getName(),
			  //forma
			  Text.NEW_LINE, TextColors.GRAY, "Forma: ", form(pokemonData), 
			  //breed
			  Text.NEW_LINE, TextColors.GRAY, "Breed: ", isBreed(pokemonData), pokemonData.getCustomTexture().toString() == "" ? Text.EMPTY : (new Object[]{Text.NEW_LINE, TextColors.GRAY, "Textura: ", TextColors.YELLOW, pokemonData.getCustomTexture()}),
			  //ivs
			  Text.NEW_LINE, Text.NEW_LINE, TextColors.YELLOW, TextStyles.BOLD, "IVs: ", TextStyles.RESET, TextColors.GRAY, pokemonData.getIVs().getTotal(), TextColors.DARK_GRAY, "/", TextColors.GRAY, "186", TextColors.DARK_GRAY, " (", TextColors.GRAY, pokemonData.getIVs().getTotal() * 100 / 186, "%", TextColors.DARK_GRAY, ")", Text.NEW_LINE, TextColors.GREEN, pokemonData.getIVs().hp, TextColors.DARK_GRAY, " | ", TextColors.RED, pokemonData.getIVs().attack, TextColors.DARK_GRAY, " | ", TextColors.GOLD, pokemonData.getIVs().defence, TextColors.DARK_GRAY, " | ", TextColors.LIGHT_PURPLE, pokemonData.getIVs().specialAttack, TextColors.DARK_GRAY, " | ", TextColors.YELLOW, pokemonData.getIVs().specialDefence, TextColors.DARK_GRAY, " | ", TextColors.AQUA, pokemonData.getIVs().speed,
			  //evs
			  Text.NEW_LINE, Text.NEW_LINE, TextColors.YELLOW, TextStyles.BOLD, "EVs: ", TextStyles.RESET, TextColors.GRAY, pokemonData.getEVs().getTotal(), TextColors.DARK_GRAY, "/", TextColors.GRAY, "510", TextColors.DARK_GRAY, " (", TextColors.GRAY, pokemonData.getEVs().getTotal() * 100 / 186, "%", TextColors.DARK_GRAY, ")", Text.NEW_LINE, TextColors.GREEN, pokemonData.getEVs().hp, TextColors.DARK_GRAY, " | ", TextColors.RED, pokemonData.getEVs().attack, TextColors.DARK_GRAY, " | ", TextColors.GOLD, pokemonData.getEVs().defence, TextColors.DARK_GRAY, " | ", TextColors.LIGHT_PURPLE, pokemonData.getEVs().specialAttack, TextColors.DARK_GRAY, " | ", TextColors.YELLOW, pokemonData.getEVs().specialDefence, TextColors.DARK_GRAY, " | ", TextColors.AQUA, pokemonData.getEVs().speed,
			  //moves
			  Text.NEW_LINE, Text.NEW_LINE, TextColors.YELLOW, TextStyles.BOLD, "Moves:", TextStyles.RESET, Text.NEW_LINE, TextColors.GRAY, pokemonData.getMoveset().get(0) != null ? pokemonData.getMoveset().get(0).toString() : "N/A", TextColors.DARK_GRAY, " | ", TextColors.GRAY, pokemonData.getMoveset().get(1) != null ? pokemonData.getMoveset().get(1).toString() : "N/A", Text.NEW_LINE, TextColors.GRAY, pokemonData.getMoveset().get(2) != null ? pokemonData.getMoveset().get(2).toString() : "N/A", TextColors.DARK_GRAY, " | ", TextColors.GRAY, pokemonData.getMoveset().get(3) != null ? pokemonData.getMoveset().get(3).toString() : "N/A"});
      statBuilder.onHover(TextActions.showText(statHover));
      return Text.of(new Object[]{statBuilder.build()});
   }
}
