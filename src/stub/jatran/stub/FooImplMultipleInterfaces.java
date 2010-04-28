package jatran.stub;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Iterator;

import javax.imageio.ImageWriter;
import javax.imageio.event.IIOWriteProgressListener;

public class FooImplMultipleInterfaces implements Iterable<String>, IIOWriteProgressListener, ItemListener {

	public Iterator<String> iterator() {
		// TODO Auto-generated method stub
		return null;
	}

	public void itemStateChanged(ItemEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void imageComplete(ImageWriter source) {
		// TODO Auto-generated method stub
		
	}

	public void imageProgress(ImageWriter source, float percentageDone) {
		// TODO Auto-generated method stub
		
	}

	public void imageStarted(ImageWriter source, int imageIndex) {
		// TODO Auto-generated method stub
		
	}

	public void thumbnailComplete(ImageWriter source) {
		// TODO Auto-generated method stub
		
	}

	public void thumbnailProgress(ImageWriter source, float percentageDone) {
		// TODO Auto-generated method stub
		
	}

	public void thumbnailStarted(ImageWriter source, int imageIndex,
			int thumbnailIndex) {
		// TODO Auto-generated method stub
		
	}

	public void writeAborted(ImageWriter source) {
		// TODO Auto-generated method stub
		
	}

}
