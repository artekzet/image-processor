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
}

object Image {
  def load(path: String): Image = {
    new Image(ImageIO.read(new File(path)))
  }

  def loadResource(path: String): Image = {
    load(s"src/main/resources/$path")
  }
}