package nia.chapter10;

import io.netty.channel.CombinedChannelDuplexHandler;

/**
 * Created by zhujie on 16/6/3.
 */
// Parameterizes CombinedByteCharCodec by the decoder and encoder implementations
public class CombineByteCharCodec extends CombinedChannelDuplexHandler<ByteToCharDecoder, CharToByteEncoder> {
    public CombineByteCharCodec() {
        // Passes the delegate instances to the parent
        super(new ByteToCharDecoder(), new CharToByteEncoder());
    }
}
