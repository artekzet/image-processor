package com.photoprocessor

import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

// red/green/blue 0-255
case class Pixel(r: Int, g: Int, b: Int) {
  assert(r >= 0 && r < 256)
  assert(g >= 0 && g < 256)
  assert(b >= 0 && b < 256)


  private def toInt: Int = {
    (r << 16) | (g << 8) | b
  }

  private infix def +(other: Pixel): Pixel = {
    Pixel(
      Pixel.clamp(r + other.r),
      Pixel.clamp(g + other.g),
      Pixel.clamp(b + other.b)
    )
  }

  def draw(w: Int, h: Int, path: String): Boolean = {
    val color: Int = toInt
    val image: BufferedImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB)
    val pixels = Array.fill(w * h)(color)
    image.setRGB(0, 0, w, h, pixels, 0, w)
    ImageIO.write(image, "JPG", new File(path))
  }

}

object Pixel {

  private def clamp(v: Int): Int = {
    if v <=0 then 0
    else if v >= 255 then 255
    else v
  }


  def main(args: Array[String]): Unit = {
    val red = Pixel(255, 0, 0)
    val green = Pixel(0, 255, 0)
    val yellow = red + green
    val black = Pixel (0, 0, 0)
    val white = Pixel(255, 255, 255)
    val pink = Transparency(0.5).combine(red, white)
    val gray = Pixel(128, 128, 128)
    val darkRed = Multiply.combine(red, gray)
    val lightRed = Screen.combine(red, gray)
    yellow.draw(40, 40, "src/main/resources/pixels/yellow.jpg")
    red.draw(40, 40, "src/main/resources/pixels/red.jpg")
    green.draw(40, 40, "src/main/resources/pixels/green.jpg")
    black.draw(40, 40, "src/main/resources/pixels/black.jpg")
    white.draw(40, 40, "src/main/resources/pixels/white.jpg")
    pink.draw(40, 40, "src/main/resources/pixels/pink.jpg")
    gray.draw(40, 40, "src/main/resources/pixels/gray.jpg")
    darkRed.draw(40, 40, "src/main/resources/pixels/darkRed.jpg")
    lightRed.draw(40, 40, "src/main/resources/pixels/lightRed.jpg")
  }
}
