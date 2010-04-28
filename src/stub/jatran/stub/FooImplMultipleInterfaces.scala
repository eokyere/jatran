package jatran.stub

import java.awt.event.ItemEvent
import java.awt.event.ItemListener
import java.util.Iterator

import javax.imageio.ImageWriter
import javax.imageio.event.IIOWriteProgressListener

class FooImplMultipleInterfaces extends Iterable[String] with IIOWriteProgressListener with ItemListener {
  def iterator():Iterator[String] = {
    return null
  }

  def itemStateChanged(e:ItemEvent):Unit = {
  }

  def imageComplete(source:ImageWriter):Unit = {
  }
  
  def imageProgress(source:ImageWriter, percentageDone:Float):Unit = {
  }

  def imageStarted(source:ImageWriter, imageIndex:Int):Unit = {
  }

  def thumbnailComplete(source:ImageWriter):Unit = {
  }

  def thumbnailProgress(source:ImageWriter, percentageDone:Float):Unit = {
  }

  def thumbnailStarted(source:ImageWriter, imageIndex:Int, thumbnailIndex:Int):Unit = {
  }

  def writeAborted(source:ImageWriter):Unit = {
  }
}


