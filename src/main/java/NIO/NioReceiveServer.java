package NIO;

import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.StandardSocketOptions;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Set;

public class NioReceiveServer {
    //接受文件路径
    private String path;

    private Charset charset = Charset.forName("UTF-8");

    public void receive() throws IOException {
        Selector selector = Selector.open();
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.socket().bind(new InetSocketAddress(8080));
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        while (selector.select() > 0) {
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            if (selectionKeys == null) {
                continue;
            }
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()) {
                // 8、获取单个的选择键，并处理
                SelectionKey selectionKey = iterator.next();
                if (selectionKey == null) {
                    continue;
                }
                if (selectionKey.isAcceptable()) {
                    SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
                    socketChannel.setOption(StandardSocketOptions.TCP_NODELAY, true);
                    socketChannel.configureBlocking(false);
                    socketChannel.register(selector, SelectionKey.OP_READ);
                } else if (selectionKey.isReadable()) {
                    handleData(selectionKey);
                }
                iterator.remove();
            }
        }
    }

    private void handleData(SelectionKey selectionKey) {
    }
}
