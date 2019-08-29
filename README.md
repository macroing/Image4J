Image4J v.0.0.1
===============
Image4J is an image processing library for Java.

Getting Started
---------------
To clone this repository and build the project, you can type the following in Git Bash. You need Apache Ant though.
```bash
git clone https://github.com/macroing/Image4J.git
cd Image4J
ant
```

Examples
--------
The following example loads two images from your hard drive, blends them together and saves the result to your hard drive.
```java
import org.macroing.image4j.Image;

public class BlendExample {
    public static void main(String[] args) {
        Image image_0 = Image.load("Image-0.png");
        Image image_1 = Image.load("Image-1.png");
        
        Image image = Image.blend(image_0, image_1, 0.5F);
        image.save("Image-Result.png");
    }
}
```

The following example loads an image from your hard drive, multiplies it with a convolution kernel and saves the result to your hard drive.
```java
import org.macroing.image4j.ConvolutionKernel33;
import org.macroing.image4j.Image;

public class ConvolutionKernelExample {
    public static void main(String[] args) {
        Image image = Image.load("Image.png");
        image.multiply(ConvolutionKernel33.SHARPEN);
        image.save("Image-Result.png");
    }
}
```

The following example loads an image from your hard drive, crops it and saves the result to your hard drive.
```java
import org.macroing.image4j.Color;
import org.macroing.image4j.Image;

public class CropExample {
    public static void main(String[] args) {
        Image image = Image.load("Image.png");
        image = image.crop(50, 50, image.getResolutionX() - 50, image.getResolutionY() - 50, Color.BLACK, false, false);
        image.save("Image-Result.png");
    }
}
```

The following example creates two images, one empty and one random. Then it fills the empty image with a circle, draws the random image to the empty image and saves the result to your hard drive.
```java
import org.macroing.image4j.Color;
import org.macroing.image4j.Image;

public class FillAndDrawExample {
    public static void main(String[] args) {
        Image image_0 = Image.random(50, 50);
        
        Image image = new Image(150, 150);
        image.fillCircle(75, 75, 50, Color.RED);
//      The two methods below are equivalent:
        image.drawImage(50, 50, image_0);
//      image.drawImage(50, 50, image_0, (colorA, colorB) -> Color.blend(colorA, colorB, colorB.a));
        image.save("Image-Result.png");
}
```

The following example loads an image from your hard drive, updates it with a function and saves the result to your hard drive.
```java
import org.macroing.image4j.Color;
import org.macroing.image4j.Image;

public class UpdateExample {
    public static void main(String[] args) {
        Image image = Image.load("Image.png");
        image.update(color -> Color.blend(color, Color.randomRed(), 0.5F));
        image.save("Image-Result.png");
    }
}
```

Dependencies
------------
 - [Java 8](http://www.java.com).

Note
----
This library hasn't been released yet. So, even though it says it's version 1.0.0 in all Java source code files, it shouldn't be treated as such. When this library reaches version 1.0.0, it will be tagged and available on the "releases" page.