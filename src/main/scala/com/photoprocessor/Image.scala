package com.photoprocessor

import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

class Image private (private val buffImage: BufferedImage){
  val width = buffImage.getWidth()
  val height = buffImage.getHeight()

  def getColor(x: Int, y: Int): Pixel = {
    Pixel.fromHex(buffImage.getRGB(x, y))
  }

  def setColor(x: Int, y: Int, p: Pixel): Unit = {
    buffImage.setRGB(x, y, p.toInt)
  }

  def save(path: String): Unit = {
    ImageIO.write(buffImage, "JPG", new File(path))
  }

  def saveResource(path: String): Unit = {
    save(s"src/main/resources/$path")
  }

  def crop(startX: Int, startY: Int, w: Int, h: Int): Image = {
    assert(
      startX >= 0 && startX < width &&
        startY >= 0 && startY < height &&
        w > 0 && h > 0 && 
        startX + w < width && startY + h < height
    )
    
    val newPixels = Array.fill(w * h)(0)
    buffImage.getRGB(startX, startY, w, h, newPixels, 0, w)
    val newBuffImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB)
    newBuffImage.setRGB(0,0,w,h, newPixels, 0, w)
    new Image(newBuffImage)
  }
}

object Image {
  def load(path: String): Image = {
    new Image(ImageIO.read(new File(path)))
  }

  def loadResource(path: String): Image = {
    load(s"src/main/resources/$path")
  }
}