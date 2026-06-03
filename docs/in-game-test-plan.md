# In-Game Test Plan

Run this checklist for every migrated Minecraft version before publishing the jar.

## Setup

1. Build the mod with `.\gradlew.bat clean build`.
2. Start a development client with `.\gradlew.bat runClient`.
3. Create a new creative test world with cheats enabled.
4. Run `/toek give` and confirm a Ticket of Eternal Keeping appears.
5. Open `.minecraft/config/ToEK.json` or `run/config/ToEK.json` and keep it available for edits.

## Command And Item Identity

1. Run `/toek give`.
2. Confirm the command succeeds for an operator.
3. Confirm the item name uses gold formatting.
4. Confirm the lore appears on multiple lines.
5. Confirm the item uses the configured base item, defaulting to paper.
6. Run `/toek give @p` and confirm the nearest player receives a ticket.
7. Run `/toek give @a` in a multiplayer or LAN test and confirm all players receive a ticket.
8. Run `/getticket @p` and confirm the legacy alias still accepts vanilla selectors.

## Inventory Keep Behavior

1. Put one ticket and several normal items in survival inventory.
2. Die with `/kill`.
3. Respawn.
4. Confirm normal inventory items remain.
5. Confirm exactly one ticket was consumed.
6. Repeat with two tickets and confirm only one is consumed.

## No-Ticket Death Behavior

1. Remove all tickets from inventory.
2. Put several normal items in survival inventory.
3. Die with `/kill`.
4. Confirm normal Minecraft item dropping behavior still happens.

## Curse Of Vanishing

1. Put one ticket in inventory.
2. Add an item with Curse of Vanishing.
3. Die with `/kill`.
4. Confirm other inventory items remain.
5. Confirm the cursed item is removed.
6. Repeat in creative mode and confirm the creative-mode behavior is unchanged.

## Config Migration And Formatting

1. Delete `ToEK.json`.
2. Restart the client/server.
3. Confirm the config file is recreated with default probabilities.
4. Change `name`, `lore`, and `CustomModelDataNumber`.
5. Run `/toek reload`.
6. Confirm the next generated ticket reflects those values without restarting.
7. Remove `genericChestProbability` and `genericMobProbability` from the config.
8. Run `/toek reload`.
9. Confirm they are restored with defaults.

## Loot Drops

1. Set `toek_debug_drops=true` in `gradle.properties` or run with `-Ptoek_debug_drops=true`.
2. Start the client.
3. Open an Ancient City chest or generate the loot table through a controlled test world.
4. Confirm the chest can contain the ticket at 100% debug probability.
5. Kill a zombie and confirm it can drop the ticket at 100% debug probability.
6. Set debug drops back to false before publishing.

## Multiplayer / Server

1. Run `.\gradlew.bat runServer`.
2. Join from a matching Fabric client.
3. Repeat the command, death, no-ticket, config, and loot checks.
4. Confirm players without client-side assets can still join when the mod is server-side.
