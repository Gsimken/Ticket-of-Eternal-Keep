# Ticket of Eternal Keeping Mod for Minecraft

## Description
This Minecraft mod introduces a new item to the game: the "Ticket of Eternal Keeping". This special item allows players to keep their inventory items upon death. The ticket is consumed in the process, making it a valuable and strategic resource.

## Locations and Probabilities
The "Ticket of Eternal Keeping" can be found in various structures within the game, each with different appearance probabilities (all configurable via the config file):

| Structure       | Default Probability | Configurable |
|-----------------|---------------------|--------------|
| Ancient City    | 10%                 | ✅ Yes       |
| Mineshaft       | 3%                  | ✅ Yes       |
| Stronghold      | 5%                  | ✅ Yes       |
| End Cities      | 5%                  | ✅ Yes       |
| Bastions        | 15%                 | ✅ Yes       |
| Other Chests    | 0.5%                | ✅ Yes       |

**Note:** All probabilities can be customized in the `ToEK.json` config file. See the [Config](#config) section for details.

## Config
The ToEK item is configurable, the file is located in the folder `.minecraft/config` (or `server_folder/config` for servers) and is named `ToEK.json`

```json
{
  "item": "minecraft:paper",
  "name": "&6Ticket of Eternal Keeping",
  "lore": [
    "&bThis ticket allows whoever carries it",
    "&bin the inventory to keep their items when they die.",
    "",
    "&4&lIt is consumed at death"
  ],
  "CustomModelDataNumber": 506,
  "lootTableProbabilities": {
    "minecraft:chests/ancient_city": 0.1,
    "minecraft:chests/abandoned_mineshaft": 0.03,
    "minecraft:chests/stronghold_library": 0.05,
    "minecraft:chests/stronghold_corridor": 0.05,
    "minecraft:chests/stronghold_crossing": 0.05,
    "minecraft:chests/end_city_treasure": 0.05,
    "minecraft:chests/bastion_bridge": 0.15,
    "minecraft:chests/bastion_hoglin_stable": 0.15,
    "minecraft:chests/bastion_other": 0.15,
    "minecraft:chests/bastion_treasure": 0.15
  },
  "genericChestProbability": 0.005
}
```

### Configurable Fields

**Item Configuration:**
- `item`: allows you to change the item that the game uses for the ticket; however, doing this removes the textures in the client, it is important to know that the chosen item can still be used for recipes and fulfill its functions, for example, choosing a block and using the right click can cause the object to be lost.

- `name`: change the name of the item. Accepts '&' for color codes (e.g., `&6` for gold, `&b` for aqua).

- `lore`: is the description of the item, it accepts as many lines as you like, just add a new one between the square brackets ('['). Also accepts '&' for color codes.

- `CustomModelDataNumber`: number used for custom texture pack, with this it is possible to force a texture pack from the server to the players so they can have a desired texture for the item.

**Loot Table Probabilities:**
- `lootTableProbabilities`: A map that allows you to configure the probability of finding the ticket in specific loot tables. Values range from `0.0` (never) to `1.0` (always/100%).
  - You can add or remove any loot table by its identifier (e.g., `"minecraft:chests/ancient_city"`)
  - To disable a loot table, set its probability to `0.0` or remove it from the map
  - The default probabilities are shown in the example above

- `genericChestProbability`: The probability for the ticket to appear in any chest that doesn't have a specific entry in `lootTableProbabilities`. Set to `0.005` (0.5%) by default. Set to `0.0` to disable tickets in generic chests.


## Installation
To install this mod, simply download the `.jar` file and place it in your Minecraft mods folder.
This mod is designed to work on the server side. It provides a convenient solution for server administrators who want to enhance the gameplay experience without requiring players to install additional mods.


In case you want users to have texture you can use the texture pack from the following [release](https://github.com/Gsimken/Ticket-of-Eternal-Keep/releases/tag/V1.1.0) on the server

## Commands
Toek has a command that allows an operator or whoever has the `toek.command.getticket` permission to generate a ticket at will, this ticket can be given to the player who invokes the command using `/getticket` or to another player using `/getticket playerName`.

## Future Work
The development team of the "Ticket of Eternal Keeping" mod is continuously working on improvements and expansions. The roadmap for future updates includes:

-  ~~**Textures for Clients with the Mod Installed**: The team is looking into allowing clients with the mod installed to see a unique texture for the "Ticket of Eternal Keeping".~~
- ~~**Item Configurability**: Plans are in place to make the item used for the "Ticket of Eternal Keeping" configurable, allowing server administrators or players to customize the specific item to be used.~~
- ~~**Customization of Name and Description**: Future versions aim to enable the customization of the item's name and description through configuration files.~~
- ~~**Adjustable Locations and Probabilities**: Features that allow the adjustment of locations and probabilities for finding the "Ticket of Eternal Keeping" in various game structures are intended to be implemented.~~
- **Configurable Crafting**: An option to enable or disable the crafting of the item within the game is planned.

Contributions are always welcome. If you have ideas or want to contribute to the code, feel free to fork the repository and submit your pull requests.

