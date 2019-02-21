/*4.存储类
以下类是用来存储访问的channle，channelGroup的原型是set集合，保证channle的唯一，如需根据参数标注存储，可以使用currentHashMap来存储。*/

package com.vm.im.netty;

import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 * ClassName:Global
 * Function: TODO ADD FUNCTION.
 *
 * @author hxy
 */
public class Global {
    public static ChannelGroup group = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
}