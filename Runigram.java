import java.awt.Color;

/** A library of image processing functions. */
public class Runigram {

	public static void main(String[] args) {
	    
		//// Hide / change / add to the testing code below, as needed.
		
		// Tests the reading and printing of an image:	
		 Color[][] tinypic = read("tinypic.ppm");
		 print(tinypic);

		// Creates an image which will be the result of various 
		// image processing operations:
		 Color[][] image;

		// Tests the horizontal flipping of an image:
		image = flippedHorizontally(tinypic);
		System.out.println();
		 print(image);

		Color[][] image2;

		image2 = scaled(tinypic, 3, 5);
		System.out.println();
		 print(image2);

		//// Write here whatever code you need in order to test your work.
		//// You can continue using the image array.
	}

	/** Returns a 2D array of Color values, representing the image data
	 * stored in the given PPM file. */
	public static Color[][] read(String fileName) {
		In in = new In(fileName);
		// Reads the file header, ignoring the first and the third lines.
		in.readString();
		int numCols = in.readInt();
		int numRows = in.readInt();
		in.readInt();
		// Creates the image array
		Color[][] image = new Color[numRows][numCols];
		// Reads the RGB values from the file into the image array. 
		// For each pixel (i,j), reads 3 values from the file,
		// creates from the 3 colors a new Color object, and 
		// makes pixel (i,j) refer to that object.
		
		for (int i = 0; i < numRows; i++) {
			for (int j = 0; j < numCols; j ++) {
				int red = in.readInt();
				int green = in.readInt();
				int blue = in.readInt();
				image[i][j] = new Color(red, green, blue);
			}
		}
		return image;
	}

    // Prints the RGB values of a given color.
	private static void print(Color c) {
	    System.out.print("(");
		System.out.printf("%3s,", c.getRed());   // Prints the red component
		System.out.printf("%3s,", c.getGreen()); // Prints the green component
        System.out.printf("%3s",  c.getBlue());  // Prints the blue component
        System.out.print(")  ");
	}

	// Prints the pixels of the given image.
	// Each pixel is printed as a triplet of (r,g,b) values.
	// This function is used for debugging purposes.
	// For example, to check that some image processing function works correctly,
	// we can apply the function and then use this function to print the resulting image.
	private static void print(Color[][] image) {
		if (image == null) {
			System.out.println("A null image array was recieved.");
			return;
		}
		for (int i = 0; i < image.length; i++) {
			for (int j = 0; j < image[i].length; j++) {
				print(image[i][j]);
				System.out.print(" ");
			}
			System.out.println();
		}
	}
	
	/**
	 * Returns an image which is the horizontally flipped version of the given image. 
	 */
	public static Color[][] flippedHorizontally(Color[][] image) {
		int numRows = image.length;
		int numCols = image[0].length;
		Color[][] flipped = new Color[numRows][numCols];
	
		for (int i = 0; i < numRows; i++) {
			for (int j = 0; j < numCols; j++) {
				flipped[i][j] = image[i][numCols - j - 1];  
			}
		}
		return flipped;
	}
	
	/**
	 * Returns an image which is the vertically flipped version of the given image. 
	 */
	public static Color[][] flippedVertically(Color[][] image) {
		int numRows = image.length;
		int numCols = image[0].length;
		Color[][] flipped = new Color[numRows][numCols];

		for (int i = 0; i < numRows; i++) {
			for (int j = 0; j < numCols; j ++) {
				flipped[i][j] = image[numRows - i - 1][j];
			}
		}
			return flipped;
	}
	
	// Computes the luminance of the RGB values of the given pixel, using the formula 
	// lum = 0.299 * r + 0.587 * g + 0.114 * b, and returns a Color object consisting
	// the three values r = lum, g = lum, b = lum.
	private static Color luminance(Color pixel) {
		double red = pixel.getRed() * 0.299;
		double green = pixel.getGreen() * 0.587;
		double blue = pixel.getBlue() * 0.114;
		int lum = (int) (red + green + blue);
		
		return new Color(lum, lum, lum);
	}
	
	/**
	 * Returns an image which is the grayscaled version of the given image.
	 */
	public static Color[][] grayScaled(Color[][] image) {
		int numRows = image.length;
		int numCols = image[0].length;
		Color [][] gray = new Color[numRows][numCols];

		for (int i = 0; i < numRows; i++) {
			for (int j = 0; j < numCols; j++) {
				gray[i][j] = luminance(image[i][j]);
			}
		}
		return gray;
	}	
	
	/**
	 * Returns an image which is the scaled version of the given image. 
	 * The image is scaled (resized) to have the given width and height.
	 */
	public static Color[][] scaled(Color[][] image, int width, int height) {
		double oldHeight = image.length;
		double oldWidth = image[0].length;
		double scaledHeight = oldHeight / (double) height;
		double scaledWidth = oldWidth / (double) width;
		Color[][] newImage = new Color[height][width];
		
		for(int i = 0; i < newImage.length; i++) {

			for(int j = 0; j < newImage[0].length; j++) {
				newImage[i][j] = image[(int) Math.floor(i * scaledHeight)][(int) Math.floor(j * scaledWidth)];
			}
		}
		return newImage;
	}
	
	/**
	 * Computes and returns a blended color which is a linear combination of the two given
	 * colors. Each r, g, b, value v in the returned color is calculated using the formula 
	 * v = alpha * v1 + (1 - alpha) * v2, where v1 and v2 are the corresponding r, g, b
	 * values in the two input color.
	 */
	public static Color blend(Color c1, Color c2, double alpha) {
		int red1 = c1.getRed();
		int green1 = c1.getGreen();
		int blue1 = c1.getBlue();
		
		int red2 = c2.getRed();
		int green2 = c2.getGreen();
		int blue2 = c2.getBlue();
		
		int NewRed = (int) Math.round(alpha * red1 + (1 - alpha) * red2);
		int NewGreen = (int) Math.round(alpha * green1 + (1 - alpha) * green2);
		int NewBlue = (int) Math.round(alpha * blue1 + (1 - alpha) * blue2);
		
		//The Value should be between 0 and 225
		NewRed = Math.min(255, Math.max(0, NewRed));
		NewGreen = Math.min(255, Math.max(0, NewGreen));
		NewBlue = Math.min(255, Math.max(0, NewBlue));
		
		return new Color(NewRed, NewGreen, NewBlue);

	}
	
	/**
	 * Cosntructs and returns an image which is the blending of the two given images.
	 * The blended image is the linear combination of (alpha) part of the first image
	 * and (1 - alpha) part the second image.
	 * The two images must have the same dimensions.
	 */
	public static Color[][] blend(Color[][] image1, Color[][] image2, double alpha) {
		int numRows = image1.length;
		int numCols = image1[0].length;
		Color[][] blended = new Color[numRows][numCols];
	
		for (int i = 0; i < numRows; i++) {
			for (int j = 0; j < numCols; j++) {
				Color c1 = image1[i][j];
				Color c2 = image2[i][j];
	
				blended[i][j] = blend(c1, c2, alpha);
			}
		}
	
		return blended;
	}

	/**
	 * Morphs the source image into the target image, gradually, in n steps.
	 * Animates the morphing process by displaying the morphed image in each step.
	 * Before starting the process, scales the target image to the dimensions
	 * of the source image.
	 */
	/**
 * Morphs the source image into the target image, gradually, in n steps.
 * Animates the morphing process by displaying the morphed image in each step.
 * Before starting the process, scales the target image to the dimensions
 * of the source image.
 */
public static void morph(Color[][] source, Color[][] target, int n) {
    int sHeight = source.length;
    int sWidth = source[0].length;
    Color[][] scaledTarget = scaled(target, sWidth, sHeight);
    setCanvas(source);

    for (int i = 0; i < n; i++) {
        double alpha = (double) i / (n - 1); 
        Color[][] blended = blend(source, scaledTarget, alpha); 
        display(blended); 
        StdDraw.pause(500); 
    }
}


	
	/** Creates a canvas for the given image. */
	public static void setCanvas(Color[][] image) {
		StdDraw.setTitle("Runigram 2023");
		int height = image.length;
		int width = image[0].length;
		StdDraw.setCanvasSize(width, height);
		StdDraw.setXscale(0, width);
		StdDraw.setYscale(0, height);
        // Enables drawing graphics in memory and showing it on the screen only when
		// the StdDraw.show function is called.
		StdDraw.enableDoubleBuffering();
	}

	/** Displays the given image on the current canvas. */
	public static void display(Color[][] image) {
		int height = image.length;
		int width = image[0].length;
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				// Sets the pen color to the pixel color
				StdDraw.setPenColor( image[i][j].getRed(),
					                 image[i][j].getGreen(),
					                 image[i][j].getBlue() );
				// Draws the pixel as a filled square of size 1
				StdDraw.filledSquare(j + 0.5, height - i - 0.5, 0.5);
			}
		}
		StdDraw.show();
	}
}


