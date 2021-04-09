
<div class="customResourceFields aboveInfo">
<dl class="customResourceFieldnative_mc_version">
<dt>Native Minecraft Version:</dt>
<dd>1.16</dd>
</dl>
<dl class="customResourceFieldmc_versions">
<dt>Tested Minecraft Versions:</dt>
<dd><ul class="plainList"><li>1.12, 1.13, 1.14, 1.15, 1.16</li></ul></dd>
</dl>
</div><br>
</dl>
</div>
<img src="https://i.imgur.com/UbPfFHT.png" alt="bg.png" class="bbCodeImage LbImage" style="">
<br>
<br>
<br>
<img src="https://i.imgur.com/zqWCGJp.png" alt="conditions.png" class="bbCodeImage LbImage" style="">
<br>
<br>
<ul>
<li><i>'hasitems (itemsadder item name) [amount]' <span style="color: #8000ff">e.g. 'hasitems itemsadder:ruby 4'</span></i><b> - Check if the player has all specified items in his inventory</b></li>
<li><i>'wearitems (itemsadder item name)'<span style="color: #8000ff"> e.g 'wearitems itemsadder:ruby_helmet'</span></i><b><b><b> - Check if the player wear specified armor</b></b></b><br>
</li>
<li><i>'hasiteminhand (itemsadder item name) [amount]' </i><b><b>- Check if the player is holding a specified item in his hand</b></b><br>
</li>
<li><i>'isblock (itemsadder block name) (x;y;z;world)' <span style="color: #8000ff">e.g. 'isblock itemsadder:ruby_ore 40;72;3;world'</span> </i><b><b><b>- Check if the block is correct</b></b></b></li>
</ul><b><br>
<img src="https://i.imgur.com/gBvlyBh.png" alt="events.png" class="bbCodeImage LbImage" style="">
</b><br>
<br>
<ul>
<li><i>'removeitems (itemsadder item name) [amount]'</i><b><b> - Removes items from player’s inventory</b></b><br>
</li>
<li><i>'giveitems (itemsadder item name) [amount]'</i><b><b> - Gives the player predefined items</b></b><br>
</li>
<li><i>'setblockat (itemsadder block name) (x;y;z;world)'</i> <b><b>- Changes the block at the given position</b></b></li>
<li><i>'playanimation (itemsadder animation name)' </i><b><b>- Play animated title</b></b></li>
</ul><b><br>
<img src="https://i.imgur.com/47WqR3y.png" alt="objectives.png" class="bbCodeImage LbImage" style="">
<br>
</b><br>
<ul>
<li>'craftitems (itemsadder item name) [amount]' <b>- To complete this objective the player must craft specified item</b></li>
<li>'pickupitems (itemsadder item name) [amount] [notify]' <span style="color: #5900b3">e.g. 'pickupitems itemsadder:ruby 3 notify' </span><b><span style="color: #404040">- To complete this objective you need to pickup the specified amount of items</span></b></li>
<li><span style="color: #000000">'blockbreak (itemsadder block name) [amount] [notify:number]' </span><span style="color: #5900b3">e.g. 'blockbreak itemsadder:ruby_ore 5 notify:1' </span><span style="color: #404040"><b>- To complete this objective player must break specified amount of blocks</b></span></li>
<li><span style="color: #404040">'blockplace (itemsadder block name) [amount] [notify:number]' <b>- To complete this objective player must place specified amount of blocks</b></span></li>
</ul><b><br>
<br>
<img src="https://i.imgur.com/eTVKRqj.png" alt="todo.png" class="bbCodeImage LbImage" style="">
<br>
<br>
</b><br>
<ul>
<li><b><br>
<ul>
<li><b>Create Enchant, Smelting objectives</b></li>
</ul><ul>
<li><b>Optimize setBlockAt event</b></li>
</ul><ul>
<li><b>Optimize isBlock condition</b></li>
</ul></b></li>
</ul>
