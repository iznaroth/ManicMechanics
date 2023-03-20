# Manic Mechanics
*I promise I'll add a logo soon edition* | *ver. 0.3.34*

This is the repository for Manic Mechanics, a mod for Minecraft version 1.19.2 (port pending! I'm waiting for 1.20.) It is a conventional "tech-mod" with an automation focused gameplay loop bracketed over the traditional Minecraft experience of, well, mining and crafting. It is currently in a fairly early pre-alpha phase with a lot of *sketched*, broken or otherwise unfinished content. Everything is subject to change. 

## What's in it now?

Manic Mechanics is similar to other tech-mods in that it follows a rotating loop of planning, exploration, and implementing. With that as a basis, I'll cover how the current version, subtitled Content Package I, tackles each of these pursuits. *Please note - lots of the stuff here is undergoing active development. Things are missing, broken and can behave in unexpected ways.*

### Planning
*The process of figuring out what to do next, how much space it will take, and what things you'll need to accomplish the task.*
- MM currently uses [Patchouli](https://github.com/VazkiiMods/Patchouli), a free data-driven documentation solution that presents itself in-game as a readable book with iteratively-unlocking chapters. This is not necessarily a permanent solution, but it allows for the most direct delivery of otherwise obscure gameplay information.
- MM is compatible and intended to be used with [Just Enough Items](https://github.com/mezz/JustEnoughItems) to some degree, allowing for quick access to any recipe hierarchy. 
- A series of ingame advancements and "quest triggers" allow for the immersive delivery of story landmarks as the player progresses, quietly directing their action.
- A loose and currently unstable system of quasi-email the player can engage in to recieve advice, items and other resources from helpful NPCs. 

### Exploration
*Getting the stuff you need to proceed.*
- A rather large set of ores, some of which use specialized harvesting processes that can be very dangerous if done incorrectly.
- Subterranean caches stuffed with alien tech, still guarded by mostly-broken security.
- Strange dimensional rifts in high places that can be pilfered for powerful resources.
- Alchemical platforms and wandering Wizards that carry useful artifacts to seed powerful machineries. 
- Ancient relics of a bygone era, holding automation power that greatly outstrips your own. 
- Exotic resources that can be tapped for nearly-limitless output. 
- A mounting suspicion on your activity that may attract the authorities if you piss them off too-much. 

### Implementation
*Crafting, building and tweaking to suit the task.*
- A lot of production machines. Somewhere around fifteen of them. 
- Multiblock machines that pretty much just take up a lot more space.
- A few methods of power-production to facilitate operations. Some are more involved than others, with potential recycling processes. 
- A logistics network using tubes! Power, items, fluids! Tubes can even be nested into each-other as a bundle, saving on tons of space. They are probably the most broken thing in the mod right now, so beware.
- A second category of machinery - magic. Magical machines use a unique alternative energy-source and transfer things in different ways, using the Node Pointer logistics system to beam mana to one-another. 
- A system of interfacing and converting magical and mechanical resources to one-another, with lots of exchange rates and tricky ways to save on space, resources and time.
- Crystals with weird behavior! Mana can be drawn with and stored within these strange stones, encouraging weird and clever in-world automation to harvest it properly.
- A somewhat robust interdimensional economy, allowing players to buy and sell nearly anything at self-adjusting rates. Bundled with this is a host of consumer goods, useless-looking items that are really hard to make and worth tons of cash, often requiring the player to relocate and construct them on-site.
- Powerful tools with greater utility than their base-Minecraft counterparts, made from long and arduous processing chains.
- A Factorio-esque research system for decrypting exotic machines into their usable counterparts, by building research cartridges and feeding them to laboratories. 

All of this exists in the mod in *some* form. A lot of it is admittedly unfinished. Once complete, it will constitute the first major version of the mod (1.0.0). 

## How do I play it?
It's a little harder now than it would be otherwise. There aren't any production builds yet, so you can't drag-and-drop it like a traditional minecraft mod. I also do not have a maven set-up, so you can't declare it as a dependency. Currently, the only way to try out Manic Mechanics is to download this repo and set it up as a workspace. It's actually fairly self-explanatory, as much of it is already automated:
- Clone this repo into any folder.
- Open it in your IDE of choice (these instructions are written for IntelliJ, but it should work in Eclipse or any other Java IDE)
- Let Gradle use the build.gradle to download dependencies and set-up the workspace. On IntelliJ, you just have to click on the little elephant reload icon somewhere in the code panel. 
- Open the gradle menu, expand the 'forgegradle runs' folder, and run the script titled 'genXRuns' where X corresponds to your IDE of choice (mine would say genIntellijRuns)
- Use the runClient run configuration. If you run into any issues, snag the console output and let me know!

You need to be at JDK 17 minimum for this to work. 

This repo is not currently open to pull requests. Once I reach a 'content-complete' phase in development, I'll probably open it up. For now, feel free to fire any burning reccomendations or code-criticisms to my email, iznaroth@gmail.com. I'm always happy to hear them, no matter how brutal!

CurseForge link coming soon, hopefully!
