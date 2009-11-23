/**
 * 
 */
package automenta.spacenet.space.jme.audio;

import java.io.IOException;
import java.nio.ByteBuffer;


@Deprecated public class DynamicGeneratedAudio /*extends DynamicAudioStream*/ {
	
//		private SampleGenerator generator;
//
//		public DynamicGeneratedAudio(SampleGenerator g) throws Exception {
//			super();
//			this.generator = g;
//		}
//
//		@Override public int getChannelCount() {
//			return 2;
//		}
//
//		final double shortMax = Short.MAX_VALUE/2.0;
//
//		/** synthesizes the next audio buffer */
//		@Override public int read(ByteBuffer b, int offset, int length)	throws IOException {
//
//			//System.out.println("read: " + offset + " " + length);
//
//			for (int i = offset; i < offset+length; ) {
//				double[] s = generator.nextSample();
//
//				int l = (int)(s[0] * shortMax);
//				int r = (int)(s[1] * shortMax);
//
////				System.out.println("read: " + l + " " + r);
////				if ((l!=0) && (r!=0)) {
////					System.out.println("read: " + l + " " + r);
////				}
//
//				byte l1 = (byte)((l >> 8) & 0xFF);
//				byte l2 =  (byte)(l & 0xFF);
//
//				byte r1 = (byte)((r >> 8) & 0xFF);
//				byte r2 = (byte)(r & 0xFF);
//
//				b.put(i++, l1);
//				b.put(i++, l2);
//				b.put(i++, r1);
//				b.put(i++, r2);
//			}
//
//			return length;
//		}
//
}